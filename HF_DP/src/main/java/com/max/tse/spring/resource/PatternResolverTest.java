package com.max.tse.spring.resource;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;


/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-30
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public class PatternResolverTest {
    public static void main(String[] args) throws Throwable{
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        org.springframework.core.io.Resource resource[] = resourcePatternResolver.getResources("classpath*:spring/*-core.xml");

        for (Resource resource1 : resource) {
            System.out.println(resource1.getDescription());
        }
    }
}
