package me.fulln.base.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @program: api
 * @description: ip获取的util类
 * @author: fulln
 * @create: 2018-10-18 14:42
 * @Version： 0.0.1
 **/
@Slf4j
public class RequestIpUtil {

    /**
     * 获取客户端请求id
     * @param request 请求信息
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        if(request==null){
            return null;
        }

        String ip = request.getHeader("X-Forwarded-For");
        if (getAddr(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (getAddr(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (getAddr(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (getAddr(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (getAddr(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)){
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            }
            catch (UnknownHostException unknownhostexception) {
                log.error("找不到对应ip",unknownhostexception);
            }
        }
        return ip;
    }

    private static Boolean getAddr(String ip){
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }


}
