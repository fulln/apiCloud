package me.fulln.base.common.threadconfig;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @program: TaskIn
 * @description: 线程队列
 * @author: fulln
 * @create: 2018-09-03 10:52
 * @Version： 0.0.1
 **/
public class ThreadQueue {

    private static ConcurrentLinkedQueue<AbstractThreadStartFactory> taskList = new ConcurrentLinkedQueue<>();

    public static ConcurrentLinkedQueue<AbstractThreadStartFactory> getTaskList() {
        return taskList;
    }
    public static void setTask(AbstractThreadStartFactory task) {
        taskList.add(task);
    }


    public static void main(String[] args) {



    }
}
