package com.max.tse.spring.resource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-30
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 * Note:spring的resource包封装了各种不同的读取资源文件的source
 * classpath classpath*的区别：classpath只会在第一个加载类的包下寻找
 * 而classpath*则会在所有的地方寻找，包括jar包
 */
public class FileSourceExample {

    public static void main(String[] args) {
        try {
            String filePath = "/home/q/www/spring/resource/configfile1.txt";
            Resource resource1 = new FileSystemResource(filePath);
            Resource resource2 = new ClassPathResource("configfile1.txt");

            InputStream ins1 = resource1.getInputStream();
            InputStream ins2 = resource2.getInputStream();

            System.out.println("res1." + resource1.getFilename());
            System.out.println("res2." + resource2.getFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
