package com.max.tse.thread.self;

import java.io.Serializable;
import java.util.List;

/**
* Created with IntelliJ IDEA.
* User: yuebin.xie
* Date: 16-5-23
* Time: 下午8:31
* To change this template use File | Settings | File Templates.
*/
class TestJson implements Serializable {
    String orderNo;

    List<String> strings;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }
}
