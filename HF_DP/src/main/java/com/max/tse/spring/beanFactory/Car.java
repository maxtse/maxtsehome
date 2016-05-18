package com.max.tse.spring.beanFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-9
 * Time: 下午4:31
 * To change this template use File | Settings | File Templates.
 */
public class Car implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean{

    private String brand;
    private String color;
    private int maxSpeed;

    private BeanFactory beanFactory;
    private String beanName;

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("调用BeanFactoryAware的setBeanFactory()");
        this.beanFactory = beanFactory;

    }

    @Override
    public void setBeanName(String name) {
        System.out.println("调用BeanNameAware的setBeanName()");
        this.beanName = name;

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("调用DisposableBean的destroy()");

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("调用InitializingBean的afterPropertiesSet()");

    }

    private void myInit() {
        System.out.println("调用Init-method所指定的myInit(),将maxSpeed设置为240");
        this.maxSpeed = 240;
    }

    private void myDestroy() {
        System.out.println("调用destroy-method所指定的myDestroy()");

    }
}
