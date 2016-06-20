package com.max.tse.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-19
 * Time: 上午12:36
 * To change this template use File | Settings | File Templates.
 */
public class WebUtils {

    private WebUtils() {};

    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
