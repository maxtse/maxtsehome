package com.max.tse.jvm;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-27
 * Time: 下午7:08
 * To change this template use File | Settings | File Templates.
 * Note:常量池
 */
public class ConstantPool {

    public static void main(String[] args) {
        testBasicData();
    }

    /**
     * 对于基本类型包装类型
     * 只有-128～127才在常量池中
     * IntegerCache
     * */
    public static void testBasicData() {

        Integer a = 127;
        System.out.println(a == 127);//拆箱
        Integer b = 127;
        System.out.println("127-----a==b: result is " + (a == b));


        a = 128;
        b = 128;
        System.out.println("128-----a==b: result is " + (a == b));

        a = -128;
        b = -128;
        System.out.println("-128-----a==b: result is " + (a == b));

        a = -129;
        b = -129;
        System.out.println("-129-----a==b: result is" + (a == b));

        System.out.println(b == -129);
        float f = 5f;
        System.out.println(f);
    }
}
