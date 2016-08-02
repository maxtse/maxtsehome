package com.max.tse.jvm;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-12
 * Time: 下午7:26
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    private enum OrderStatus {

        PAY_OK(1,""),
        TICKET_LOCK(2, "");
        OrderStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        private final int code;

        private final String desc;

        public static OrderStatus formCode(int code) {
            for (OrderStatus orderStatus : OrderStatus.values()) {
                if (orderStatus.code == code) {
                    return orderStatus;
                }
            }
            return null;
        }
    }

    public static void main (String[] args) {
        testSwitch();
    }

    /**
     * 测试switch
     * 遇到break才会停止
     * 否则不匹配 照样执行
     * fall through
     * shit
     * */
    public static void  testSwitch() {
        int status = 1;

        OrderStatus orderStatus = OrderStatus.formCode(status);
        switch (orderStatus) {
            case PAY_OK:
                System.out.println("pay_ok");
            case TICKET_LOCK:
                System.out.println("ticket_ok");
                break;
            default:
                System.out.println("default");
        }
    }
}
