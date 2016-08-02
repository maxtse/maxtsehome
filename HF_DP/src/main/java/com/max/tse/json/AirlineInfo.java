package com.max.tse.json;

/**
 * Copyright (c) 2015 Qunar.com. All Rights Reserved.
 * Created with IntelliJ IDEA.
 * ClassName: AirlineInfo
 * User: xiaoshuang.cui
 * Date: 2015-11-06
 * Time: 15:28
 */
public class AirlineInfo {
    /**
     * 航空公司名称
     */
    private String name;

    /**
     * 航空公司二字码
     */
    private String code;

    /**
     * 票号前缀
     */
    private String ticketPrefix;

    /**
     * 航司电话
     */
    private String hotline;

    /**
     * 航司电话加密后
     */
    private String decriptionHotline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTicketPrefix() {
        return ticketPrefix;
    }

    public void setTicketPrefix(String ticketPrefix) {
        this.ticketPrefix = ticketPrefix;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getDecriptionHotline() {
        return decriptionHotline;
    }

    public void setDecriptionHotline(String decriptionHotline) {
        this.decriptionHotline = decriptionHotline;
    }

    @Override
    public String toString() {
        return "AirlineInfo{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", ticketPrefix='" + ticketPrefix + '\'' +
                ", hotline='" + hotline + '\'' +
                ", decriptionHotline='" + decriptionHotline + '\'' +
                '}';
    }
}
