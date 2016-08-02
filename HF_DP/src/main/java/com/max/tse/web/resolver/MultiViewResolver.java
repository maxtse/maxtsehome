package com.max.tse.web.resolver;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-20
 * Time: 上午8:40
 * To change this template use File | Settings | File Templates.
 * Note:自定义的多视图解析器
 * 根据后缀名来处理
 * 默认是jsp
 */
public class MultiViewResolver implements ViewResolver,Ordered {

    private Map<String, ViewResolver> viewResolverMap;
    private int order;


    public Map<String, ViewResolver> getViewResolverMap() {
        return viewResolverMap;
    }

    public void setViewResolverMap(Map<String, ViewResolver> viewResolverMap) {
        this.viewResolverMap = viewResolverMap;
    }


    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {

        int n = viewName.lastIndexOf(".");

        String suffix = "";

        if (n == -1) {
            suffix = "jsp";
        } else {
            suffix = StringUtils.substringAfterLast(viewName, ".");
        }

        ViewResolver viewResolver = viewResolverMap.get(suffix);
        if (viewResolver != null) {
            return viewResolver.resolveViewName(StringUtils.substringBeforeLast(viewName, "."), locale);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
