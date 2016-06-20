package com.max.tse.json;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-7
 * Time: 下午5:22
 * To change this template use File | Settings | File Templates.
 */
public class TicketInfo implements Serializable{
    private static final long serialVersionUID = -1777119028240996180L;
    private String ticketNo;
    private int ticketStatus;
    private int channelType;
    private String validateDate;
    private int validateResult;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public int getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(int ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public String getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(String validateDate) {
        this.validateDate = validateDate;
    }

    public int getValidateResult() {
        return validateResult;
    }

    public void setValidateResult(int validateResult) {
        this.validateResult = validateResult;
    }
}
