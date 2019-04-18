package com.fulln.me.service.basic.impl;


import com.fulln.me.api.common.constant.ThreadPoolNameConfig;
import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.enums.ThreadCountsEnums;
import com.fulln.me.api.common.threadconfig.AbstractThreadRunFactory;
import com.fulln.me.api.common.threadconfig.AbstractThreadStartFactory;
import com.fulln.me.api.common.threadconfig.NamedThreadFactory;
import com.fulln.me.api.model.email.EmailEntity;
import com.fulln.me.dao.system.SysArticleInfoDao;
import com.fulln.me.service.basic.IMailService;
import com.fulln.me.service.basic.IThreadStartService;
import com.fulln.me.service.search.SearchService;
import com.fulln.me.thread.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.fulln.me.api.common.threadconfig.ThreadQueue.getTaskList;


/**
 * @program: api
 * @description: 线程池启动业务层实现
 * @author: fulln
 * @create: 2018-10-09 15:37
 * @Version： 0.0.1
 **/
@Service
@Slf4j
public class ThreadStartServiceImpl implements IThreadStartService {

    @Resource(name = "executorThreadPools")
    private ThreadPoolExecutor executor;

    @Resource(name = "dispatchThreadPools")
    private ThreadPoolExecutor[] dispatchThreadPools;

    @Resource(name = "safeThreadPools")
    private ThreadPoolExecutor safeThreadPools;

    @Resource
    private SearchService searchService;

    @Resource
    private SysArticleInfoDao sysArticleInfoDao;

    @Resource
    private IMailService mailService;

    @Override
    public GlobalResult dispatch(String text) {
        AbstractThreadStartFactory task = new DispatchTask(searchService, text);
        task.getThreadStatus(dispatchThreadPools[ThreadCountsEnums.SEARCH.ordinal()].submit(task));
        return task.getResult();
    }

    /**
     * 创建file
     *
     * @param path    地址
     * @param context 文章内容
     */
    @Override
    public void fileCreate(String path, String context) {
        File file = new File(path);
        AbstractThreadRunFactory task = new FileMakeTask(file, context);
        executor.execute(task);
    }

    /**
     * 读取file文件
     *
     * @param path 文件名(文件夹)
     */
    @Override
    public GlobalResult fileReader(File path) {
        AbstractThreadStartFactory task = new FileReadTask(path, sysArticleInfoDao);
        task.getThreadStatus(executor.submit(task));
        return task.getResult();
    }

    @Override
    public void sendEmail(EmailEntity emailEntity) {
        AbstractThreadRunFactory task = new EmailSendTask(mailService, emailEntity);
        executor.execute(task);
    }


    @Override
    public void destroy() {
        long timeout = 1L;
        TimeUnit unit = TimeUnit.MINUTES;
        int poolSize = 2 + dispatchThreadPools.length;
        getTaskList().clear();
        ThreadPoolExecutor shutdownThreadPool = new ThreadPoolExecutor(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new NamedThreadFactory(ThreadPoolNameConfig.SHUTDOWN_THREAD_POOL));
        shutdownThreadPool
                .execute(new ShutdownTask(ThreadPoolNameConfig.SINGLE_THREAD_POOL, executor, timeout, unit));
        shutdownThreadPool
                .execute(new ShutdownTask(ThreadPoolNameConfig.SAFE_THREAD_POOL, safeThreadPools, timeout, unit));
        Stream.of(ThreadCountsEnums.values()).forEach(application -> {
            String applicationName = application.name();
            int applicationOrdinal = application.ordinal();
            shutdownThreadPool.execute(new ShutdownTask(applicationName + ThreadPoolNameConfig.GROUP_THREAD_POOL,
                    dispatchThreadPools[applicationOrdinal], timeout, unit));

        });

        shutdownThreadPool.shutdown();
        try {
            boolean isTerminated = shutdownThreadPool.awaitTermination(timeout, unit);
            log.info("{} shutdown isTerminated={}", ThreadPoolNameConfig.SHUTDOWN_THREAD_POOL, isTerminated);
            if (!isTerminated) {
                shutdownThreadPool.shutdownNow();
                isTerminated = shutdownThreadPool.awaitTermination(timeout, unit);
                log.info("{} shutdownNow isTerminated={}", ThreadPoolNameConfig.SHUTDOWN_THREAD_POOL, isTerminated);
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

    }


}
