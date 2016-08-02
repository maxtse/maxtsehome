package com.max.tse.spring.beanFactory;

import com.max.tse.spring.SpringBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-6
 * Time: 下午12:34
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationContextTest {

    private static final String PATH = "classpath:spring/maxtse-core.xml";

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(PATH);

        SpringBean springBean = applicationContext.getBean("springBean", SpringBean.class);
        System.out.print(springBean.toString());
    }
}
