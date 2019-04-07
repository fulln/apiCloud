package com.fulln.me.api.common.threadconfig;


import com.fulln.me.api.common.entity.GlobalResult;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @program: TaskIn
 * @description: 线程启动工厂类 有返回值的类型判断
 * @author: fulln
 * @create: 2018-09-03 10:58
 * @Version： 0.0.1
 **/
@Slf4j
public abstract class AbstractThreadStartFactory implements Callable {

    /**
     * 线程运行标志
     */
    private Boolean status;

    /**
     * 通用返回的结果
     */
    private GlobalResult result;

    /**
     * 获取线程的执行状态  (当线程为callable的时候才有用处)
     * @param future
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public  void getThreadStatus(Future future) {
        try {
            setStatus(true);
            setResult((GlobalResult)future.get());
        } catch (InterruptedException | ExecutionException e) {
            setStatus(false);
            log.error("运行的程序异常", e);
        }

    }


    public void setStatus(Boolean b) {
        this.status = b;
    }


    public Boolean getStatus() {
        return status;
    }


    public GlobalResult getResult() {
        return result;
    }

    public void setResult(GlobalResult result) {
        this.result = result;
    }

}
