package me.fulln.base.common.threadconfig;


import me.fulln.base.common.enums.ThreadCountsEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static me.fulln.base.common.constant.ThreadPoolNameConfig.*;


/**
 * @program: TaskIn
 * @description: 线程池配置类
 * @author: fulln
 * @create: 2018-08-31 17:27
 * @Version： 0.0.1
 **/
@Slf4j
@Configuration
public class ThreadPoolConfig {

    /**
     * 单个线程池
     *
     * @return
     */
    @Bean
    public ThreadPoolExecutor executorThreadPools() {
        return new ThreadPoolExecutor(5, 8, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                new NamedThreadFactory(SINGLE_THREAD_POOL));
    }


    /**
     * 组线程池
     *
     * @return
     */
    @Bean
    public ThreadPoolExecutor[] dispatchThreadPools() {
        return Stream.of(ThreadCountsEnums.values())
                .map(application -> {
                            int poolSize = application.ordinal() + 1;
                            return new ThreadPoolExecutor(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                                    new NamedThreadFactory(application.name() + GROUP_THREAD_POOL));
                        }
                )
                .toArray(ThreadPoolExecutor[]::new);
    }

    /**
     * 安全防御型
     *
     * @return
     */
    @Bean
    public ThreadPoolExecutor safeThreadPools() {
        return new ThreadPoolExecutor(5, 8, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                new NamedThreadFactory(SAFE_THREAD_POOL));
    }

}