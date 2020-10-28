package com.fulln.me.thread;


import me.fulln.base.common.threadconfig.AbstractThreadRunFactory;
import me.fulln.base.common.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;


/**
 * @program: api
 * @description: 本地生成文件线程
 * @author: fulln
 * @create: 2018-11-13 17:43
 * @Version： 0.0.1
 **/
@Slf4j
public class FileMakeTask extends AbstractThreadRunFactory {

    /**
     * 需要执行的方法和参数
     */
    private File path;
    private String context;

    public FileMakeTask(File path, String context) {
        this.path = path;
        this.context = context;
    }

    @Override
    public void run() {
        FileUtil.writeToFile(path, new StringBuffer(context));
    }

}
