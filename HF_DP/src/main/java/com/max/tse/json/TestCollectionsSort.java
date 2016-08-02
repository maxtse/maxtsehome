package com.max.tse.json;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-7
 * Time: 下午5:21
 * To change this template use File | Settings | File Templates.
 */
public class TestCollectionsSort {
    public static void main(String[] args) {
        List<TicketInfo> ticketInfos = Lists.newArrayList();
        TicketInfo ticketInfo = new TicketInfo();
        ticketInfo.setValidateDate("2016-06-04 12:19:11");
        ticketInfos.add(ticketInfo);

        TicketInfo ticketInfo1 = new TicketInfo();
        ticketInfo1.setValidateDate("2016-06-04 12:19:12");
        ticketInfos.add(ticketInfo1);
        System.out.println(JSON.toJSONString(ticketInfos));
        Collections.sort(ticketInfos, new Comparator<TicketInfo>() {
            @Override
            public int compare(TicketInfo o1, TicketInfo o2) {
                return o2.getValidateDate().compareTo(o1.getValidateDate());
            }
        });
        System.out.println(JSON.toJSONString(ticketInfos));


    }

}
