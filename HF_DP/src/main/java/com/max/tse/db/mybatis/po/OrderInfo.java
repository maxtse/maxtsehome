package com.max.tse.db.mybatis.po;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-20
 * Time: 下午4:46
 * To change this template use File | Settings | File Templates.
 */
public class OrderInfo {

    private Long id;

    private String orderNo;

    private String source;

    private Date createTime;

    private Date lastUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
