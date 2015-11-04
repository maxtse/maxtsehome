package test;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-11-3
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class TestString {
    public static void main(String[] args) {
        String testString = "ordercenter_pangolin.field.orderNo.path=condition.biz_order_no";
        System.out.println(StringUtils.substringBefore(testString, "."));
    }
}
