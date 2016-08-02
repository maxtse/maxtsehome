package com.max.tse.po;

import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-11-23
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */
public class Result implements Serializable{

    private static final long serialVersionUID = 8046783347829423343L;

    private boolean ret;

    private String errmsg;

    private Map<String, Object> data;

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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public static Result emptyResult() {
        Result result = new Result();
        return result;
    }
    public static Result successResult() {
        Result result = new Result();
        result.setRet(true);
        return result;
    }
    public static Result failResult() {
        Result result = new Result();
        result.setRet(false);
        return result;
    }

    public Result addData(String key, Object data) {
        if (this.data == null) {
            this.data = Maps.<String, Object>newHashMap();
        }
        this.data.put(key, data);
        return this;
    }

    public Result addErrmsg(String errmsg) {
        this.errmsg = errmsg;
        return this;
    }
}
