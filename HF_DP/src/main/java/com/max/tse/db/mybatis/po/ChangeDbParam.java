package com.max.tse.db.mybatis.po;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-21
 * Time: 上午10:06
 * To change this template use File | Settings | File Templates.
 */
public class ChangeDbParam {

    public ChangeDbParam(String orderNo) {
        this.orderNo = orderNo;
    }

    public ChangeDbParam() {}

    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
