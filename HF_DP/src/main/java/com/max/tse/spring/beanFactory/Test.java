package com.max.tse.spring.beanFactory;

import com.max.tse.myself.singleton.innerclass.Singleton;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-30
 * Time: 下午9:17
 * To change this template use File | Settings | File Templates.
 * Note:beanFactory
 */
public class Test {

    //ListableBeanFactory listableBeanFactory = new ListableBeanFactory(); 封装了访问容器中bean基本信息
    //的若干方法，

    HierarchicalBeanFactory hierarchicalBeanFactory = new ClassPathXmlApplicationContext();//父子容器
    //子容器可以访问父容器的，但是父容器不可以访问子容器的 如WebApplicationContext 和Application

    //ConfigurableBeanFactory configurableBeanFactory ; 比较重要 提供了设置类加载器，属性编辑器 容器初始化后置处理器等方法

    //AutowireCapableBeanFactory autowireCapableBeanFactory; 定义了各种自动装配的方法

    SingletonBeanRegistry singletonBeanRegistry = new SingletonBeanRegistry() {//定义了在运行期间想容器中注册单例bean的方法
        @Override
        public void registerSingleton(String beanName, Object singletonObject) {

        }

        @Override
        public Object getSingleton(String beanName) {
            return null;
        }

        @Override
        public boolean containsSingleton(String beanName) {
            return false;
        }

        @Override
        public String[] getSingletonNames() {
            return new String[0];
        }

        @Override
        public int getSingletonCount() {
            return 0;
        }
    };


    BeanDefinitionRegistry beanDefinitionRegistry = new BeanDefinitionRegistry() {//spring配置文件中的bean标签都通过BeanDefinition来定义
        @Override
        public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {

        }

        @Override
        public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {

        }

        @Override
        public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
            return null;
        }

        @Override
        public boolean containsBeanDefinition(String beanName) {
            return false;
        }

        @Override
        public String[] getBeanDefinitionNames() {
            return new String[0];
        }

        @Override
        public int getBeanDefinitionCount() {
            return 0;
        }

        @Override
        public boolean isBeanNameInUse(String beanName) {
            return false;
        }

        @Override
        public void registerAlias(String name, String alias) {

        }

        @Override
        public void removeAlias(String alias) {

        }

        @Override
        public boolean isAlias(String beanName) {
            return false;
        }

        @Override
        public String[] getAliases(String name) {
            return new String[0];
        }
    };
}
