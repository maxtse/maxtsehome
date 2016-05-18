package com.max.tse.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-27
 * Time: 下午5:24
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void testSpring() {
        String filePath = "spring/maxtse-core.xml";
        ClassPathResource classPathResource = new ClassPathResource(filePath);

        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(classPathResource);
        xmlBeanFactory.getBean("springBean");
    }

    public static void main(String[] args) {
        testSpring();
    }
}
