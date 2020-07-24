package com.rock.port.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于获取来源请求的一些信息
 * Created by caoqingyuan on 2017/11/24.
 */
public class HostUtil {
    private static final Logger logger= Logger.getLogger(HostUtil.class);

    public static void loadHostInfo(HttpServletRequest request){
        String ip=getIpAdress(request);
        String toUrl=getReqPurposeUrl(request);

        logger.info("request from ip ["+ip+"] to url ["+toUrl+"]");
    }

    /**
     * 获取请求主机ip
     * @param request
     * @return
     */
    public static String getIpAdress(HttpServletRequest request){
        //这种方式是获取经过各层代理之后的ip地址
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();//一般通过这种方式就可以直接获取到请求ip
        }
        return ip;
    }

    /**
     * 获取浏览器信息
     * @param request
     * @return
     */
    private static String getBrowserInfo(HttpServletRequest request){
        return request.getHeader("User-Agent");
    }

    /**
     * 获取请求的目的地址
     * @param request
     * @return
     */
    private static String getReqPurposeUrl(HttpServletRequest request){
        return request.getRequestURL().toString();
    }
}
