package com.fulln.me.api.common.utils;


import com.fulln.me.api.common.enums.pythonEnums;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUthor: fulln
 * @Description: 运行python文件
 * @Date : Created in  17:53  2018/9/2.
 */
@Slf4j
public class PythonRun {

    public static List<String> usePython(String[] args) {

        List<String> li = new ArrayList<>();
        if (args == null) {
            return li;
        }

        try {
            // 执行py文件
            Process proc = Runtime.getRuntime().exec(args);
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if("magnet".equals(line.substring(0, 6))){
                    li.add(line);
                }
            }

            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            log.error("执行python文件出现异常");
            e.printStackTrace();
        }
        return li;
    }

    public static void main(String[] args) {
        List<String> li = usePython(pythonEnums.SEARCH_TORRENT.getAll("ipz"));
        li.forEach(System.out::println);
    }
}
