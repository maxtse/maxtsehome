package com.max.tse.json;

import java.util.List;

/**
 * Copyright (c) 2015 Qunar.com. All Rights Reserved.
 * Created with IntelliJ IDEA.
 * ClassName: AirlineResult
 * User: xiaoshuang.cui
 * Date: 2015-11-06
 * Time: 16:21
 */
public class AirlineResult {
    private boolean ret;

    private String errmsg;

    private List<AirlineInfo> data;

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<AirlineInfo> getData() {
        return data;
    }

    public void setData(List<AirlineInfo> data) {
        this.data = data;
    }
}
