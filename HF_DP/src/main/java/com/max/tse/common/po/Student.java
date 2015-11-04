package com.max.tse.common.po;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-11-3
 * Time: 下午2:55
 * To change this template use File | Settings | File Templates.
 */
public class Student {

    private long id;

    private String name;

    private Date createDate;

    private String no;

    private String nickName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
