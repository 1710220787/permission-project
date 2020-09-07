package com.yxun8.utils;

import com.yxun8.domain.Employees;

import javax.servlet.http.HttpServletRequest;

/**
 * 本地线程获得ip
 */
public class RequestUtil {
    private static ThreadLocal<HttpServletRequest> local = new ThreadLocal();

    public static HttpServletRequest getLocal() {
        return local.get();
    }

    public static void setLocal(HttpServletRequest request){
        local.set(request);
    }




}
