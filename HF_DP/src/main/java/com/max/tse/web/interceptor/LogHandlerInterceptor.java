package com.max.tse.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.max.tse.web.annoation.LogInterceptor;
import com.max.tse.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-18
 * Time: 下午11:34
 * To change this template use File | Settings | File Templates.
 * 打日志用的拦截器
 */
public class LogHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LogHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            throw new IllegalArgumentException("LogHandlerInterceptor not right handlerMapping");
        }
        if (logOutput((HandlerMethod) handler)) {
            logger.info("IP ={}, uri= {}, url={}, param= {}", WebUtils.getRequestIp(request),
                    request.getRequestURI(), request.getRequestURL(), JSON.toJSONString(request.getParameterMap()));
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    private boolean logOutput(HandlerMethod handlerMethod) {
        Method method = handlerMethod.getMethod();
        LogInterceptor logInterceptor = method.getAnnotation(LogInterceptor.class);
        return logInterceptor != null;
    }
}
