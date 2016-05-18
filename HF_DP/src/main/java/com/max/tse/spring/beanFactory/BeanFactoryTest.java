package com.max.tse.spring.beanFactory;

import com.max.tse.spring.SpringBean;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-5
 * Time: 下午8:56
 * To change this template use File | Settings | File Templates.
 */
public class BeanFactoryTest {
    public static void main(String[] args) throws Exception{
        ResourcePatternResolver resourcePatternResolver= new PathMatchingResourcePatternResolver();
        Resource resource = resourcePatternResolver.getResource("classpath:spring/maxtse-core.xml");
        System.out.println(resource.getDescription());

        BeanFactory beanFactory = new XmlBeanFactory(resource);
        System.out.println("init bean factory");

        //SpringBean springBean = beanFactory.getBean("springBean", SpringBean.class);
        CarA carA = beanFactory.getBean("carA", CarA.class);

        System.out.println("spring bean is ready for use!");


    }
}
