package me.fulln.base.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author fulln
 * @program api
 * @description 服务器性能监控
 * @Date 2018-11-21 17:51
 * @Version 0.0.1
 **/
@Slf4j
public final class LinuxSystemUtil {
    /**
     * get memory by used info
     * 内存
     *
     * @return int[] result
     * result.length==4;int[0]=MemTotal;int[1]=MemFree;int[2]=SwapTotal;int[3]=SwapFree;
     * @throws IOException
     * @throws InterruptedException
     */
    public static int[] getMemInfo() throws IOException, InterruptedException {
        File file = new File("/proc/meminfo");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(file)));
        int[] result = new int[6];
        String str = null;
        StringTokenizer token = null;
        while ((str = br.readLine()) != null) {
            token = new StringTokenizer(str);
            if (!token.hasMoreTokens())
                continue;

            str = token.nextToken();
            if (!token.hasMoreTokens())
                continue;


            if ("MemTotal:".equalsIgnoreCase(str))
                result[0] = Integer.parseInt(token.nextToken());
            else if ("MemAvailable:".equalsIgnoreCase(str))
                result[1] = Integer.parseInt(token.nextToken());
            else if ("SwapTotal:".equalsIgnoreCase(str))
                result[2] = Integer.parseInt(token.nextToken());
            else if ("SwapFree:".equalsIgnoreCase(str))
                result[3] = Integer.parseInt(token.nextToken());
            else if ("MemFree:".equalsIgnoreCase(str))
                result[4] = Integer.parseInt(token.nextToken());
        }

        return result;
    }

    /**
     * get memory by used info
     *
     * @return float efficiency
     * @throws IOException
     * @throws InterruptedException
     */
    public static float getCpuInfo() throws IOException, InterruptedException {
        File file = new File("/proc/stat");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(file)));
        StringTokenizer token = new StringTokenizer(br.readLine());
        token.nextToken();
        int user1 = Integer.parseInt(token.nextToken());
        int nice1 = Integer.parseInt(token.nextToken());
        int sys1 = Integer.parseInt(token.nextToken());
        int idle1 = Integer.parseInt(token.nextToken());

        Thread.sleep(1000);

        br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file)));
        token = new StringTokenizer(br.readLine());
        token.nextToken();
        int user2 = Integer.parseInt(token.nextToken());
        int nice2 = Integer.parseInt(token.nextToken());
        int sys2 = Integer.parseInt(token.nextToken());
        int idle2 = Integer.parseInt(token.nextToken());

        return (float) ((user2 + sys2 + nice2) - (user1 + sys1 + nice1)) / (float) ((user2 + nice2 + sys2 + idle2) - (user1 + nice1 + sys1 + idle1));
    }

    public static String getHostIp() {
        String command = "ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|grep -v 172.*|awk '{print $2}'|tr -d 'addr:'";
        List<String> lis = runTask(command);
        return Arrays.toString(lis.toArray(new String[]{}));
    }

    public static List<String> runTask(String task) {
        List<String> strArr = new ArrayList<>();
        List<String> taskArr = new ArrayList<>();
        taskArr.add("sh");
        taskArr.add("-c");
        taskArr.add(task);
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(taskArr);
        pb.redirectErrorStream(true);
        Process process = null;
        try {
            process = pb.start();
            String str;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                while ((str = in.readLine()) != null) {
                    strArr.add(str);
                }
            } catch (Exception e) {
                log.error("命令执行异常", e);
            }
        } catch (IOException e) {
            log.error("命令执行异常", e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return strArr;
    }


    public static Desk getDeskUsage() throws IOException {
        List<String> lis = runTask("df -hl");
        Desk desk = new Desk();
        String[] strArray = lis.get(1).split(" ");
        for (String aStrArray : strArray) {
            if (!StringUtils.isEmpty(aStrArray)) {
                if (aStrArray.endsWith("G")) {
                    if (StringUtils.isEmpty(desk.getTotal())) {
                        desk.setTotal(aStrArray);
                    } else if (StringUtils.isEmpty(desk.getUsed())) {
                        desk.setUsed(aStrArray);
                    }
                }
                if (aStrArray.endsWith("%")) {
                    desk.setUseRate((Float.parseFloat(aStrArray.replace("%", "")) / 100));
                }
            }
        }
        return desk;
    }

    public static class Desk {
        private String total;
        private String used;
        private float useRate;

        @Override
        public String toString() {
            return "总磁盘空间：" + total + "，已使用：" + used + "，使用率达：" + useRate;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public float getUseRate() {
            return useRate;
        }

        public void setUseRate(float useRate) {
            this.useRate = useRate;
        }

    }

    public static void main(String[] args) {
        System.out.println(getThisProjectName());

    }

    public static String getThisProjectName() {
        String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        String[] s = path.replace("/target/classes", "").split("[\\\\/]");
        return s[s.length - 1];
    }
}



