package com.max.tse.web.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-18
 * Time: 上午9:48
 * To change this template use File | Settings | File Templates.
 */
public class MyExceptionHandlerResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandlerResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("unCaught exception", ex);

        return new ModelAndView("error");
    }
}
