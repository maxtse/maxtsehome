package com.max.tse.guava.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-8-5
 * Time: 上午12:39
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static ImmutableList<String> C_TENPAY_LINE = ImmutableList.copyOf(new String[] {"3U","CA","CN","CZ","FM","HU","JD","KN","MF","MU","NS","SC","ZH"});//出票通 财付通支持航司
    public static ImmutableList<String> C_YEELIFE_LINE = ImmutableList.copyOf(new String[] {"8L","CN","GS","HU","JD","PN","UQ"});//出票通 易生支持航司
    public static ImmutableList<String> C_YEEPAY_LINE = ImmutableList.copyOf(new String[] {"3U","8L","BK","CA","DZ","EU","FM","FU","GJ","GS","GX","HO","HU","HX","JD","KN","KY","MF","MU","NS","PN","QW","SC","TV","ZH"});//出票通 易宝支持航司
    public static ImmutableList<String> C_CHINAPNR_LINE = ImmutableList.copyOf(new String[]{"3U", "BK", "CA", "CZ", "DZ", "FM", "GJ", "HO", "KN", "KY", "MF", "MU", "NS", "SC", "TV", "ZH"});//出票通 汇付支持航司
    public static Map<String, List<Integer>> airinePurchaseTypeMap = Maps.newHashMap();

    public static Date addSecond(Date myDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.SECOND, amount);
        return cal.getTime();
    }
    private static Map<String, List<Integer>> getAirlinePurchaseTypeMap() {
        if (MapUtils.isEmpty(airinePurchaseTypeMap)) {

            Map<String, List<Integer>> result = Maps.newHashMap();
            for (String airline : C_TENPAY_LINE) {
                List<Integer> airlinePurchaseList = result.get(airline);
                if (airlinePurchaseList == null) {
                    airlinePurchaseList = Lists.newArrayList();
                    result.put(airline, airlinePurchaseList);
                }
                airlinePurchaseList.add(2);
            }
            for (String airline : C_CHINAPNR_LINE) {
                List<Integer> airlinePurchaseList = result.get(airline);
                if (airlinePurchaseList == null) {
                    airlinePurchaseList = Lists.newArrayList();
                    result.put(airline, airlinePurchaseList);
                }
                airlinePurchaseList.add(4);
            }
            for (String airline : C_YEELIFE_LINE) {
                List<Integer> airlinePurchaseList = result.get(airline);
                if (airlinePurchaseList == null) {
                    airlinePurchaseList = Lists.newArrayList();
                    result.put(airline, airlinePurchaseList);
                }
                airlinePurchaseList.add(100);
            }
            for (String airline : C_YEEPAY_LINE) {
                List<Integer> airlinePurchaseList = result.get(airline);
                if (airlinePurchaseList == null) {
                    airlinePurchaseList = Lists.newArrayList();
                    result.put(airline, airlinePurchaseList);
                }
                airlinePurchaseList.add(9);
            }
            airinePurchaseTypeMap = ImmutableMap.copyOf(result);
        }
        return airinePurchaseTypeMap;
    }
    public static void main(String[] args) throws Exception{
        System.out.println(new Date().getTime());
        System.out.println(addSecond(new Date(), -10).getTime());
        System.out.println(DateUtil.formatDateTime(new Date(), DateUtil.LONG_DATE_FORMAT));
        System.out.println(DateUtil.formatDateTime(DateUtil.nextMonth(new Date(), -3), DateUtil.LONG_DATE_FORMAT));
        Map<String, Object> map = Maps.newHashMap();
        Map<String, Object> map1 = (Map) map.get("baseInfo");
        System.out.println(map1 == null);
        boolean result = CharMatcher.JAVA_LETTER_OR_DIGIT.or(CharMatcher.anyOf("-")).matchesAllOf(",");
        System.out.print(result);
        BigDecimal bigDecimal = new BigDecimal(5.25).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.print(bigDecimal);
        System.out.println(JSON.toJSONString(getAirlinePurchaseTypeMap()));


    }
}
