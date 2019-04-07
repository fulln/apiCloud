package com.fulln.me.api.common.threadconfig;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author fulln
 * @program api
 * @description 线程同步的map
 * @Date 2018-11-22 11:32
 * @Version 0.0.1
 **/
public class ThreadHashConfig {


    private ConcurrentHashMap<String ,Object> maps = new ConcurrentHashMap<>();
    private CountDownLatch countDownLatch;

    public ConcurrentHashMap<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(ConcurrentHashMap<String, Object> maps) {
        this.maps = maps;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(Integer size) {
        //线程计数器;
        this.countDownLatch =  new CountDownLatch(size);
    }
}
