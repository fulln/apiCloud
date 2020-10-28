package me.fulln.base.common.threadconfig;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @program: TaskIn
 * @description: 线程启动工厂类 无future 的类型判断
 * @author: fulln
 * @create: 2018-09-03 10:58
 * @Version： 0.0.1
 **/
@Slf4j
public abstract class AbstractThreadRunFactory implements Runnable {

    /**
     * 获取线程的执行状态  (当线程为callable的时候才有用处)
     * @param future
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public  void getThreadStatus() {


    }

}
