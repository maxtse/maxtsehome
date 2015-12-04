package com.max.tse.common.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-11-10
 * Time: 上午11:26
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {
        String string = "乘机人%s 改签申请原因【%s】 往返-去程：航段【%s】 起飞日期【%s】 ";
        List<String> paramList = Lists.newArrayList();
        paramList.add("【DONG/NAN】 【DAVID/WU】 【LI/WENTING】");
        paramList.add("我要改变行程计划、我要改航班");
        paramList.add("北京-首尔");
        paramList.add("2015-1010");
        String[] params = new String[4];
        params[0] = paramList.get(0);
        params[1] = paramList.get(1);
        params[2] = paramList.get(2);
        params[3] = paramList.get(3);
        System.out.println(String.format(string, params));
        Set<String> testSet = Sets.newHashSet();
        testSet.add("1");
        testSet.add("3");
        testSet.add("2");
        testSet.add("4");
        List<String> testList = Lists.newArrayList(testSet);
        System.out.println(JSON.toJSONString(testList));
        Ordering<String> ordering = Ordering.natural();
        List<String> orderList = ordering.sortedCopy(testList);
        System.out.println(JSON.toJSONString(testList));
        String upgradeFee = "a100";
        String useFee = "100";

        boolean checkFee = CharMatcher.JAVA_DIGIT.matchesAllOf(upgradeFee) && CharMatcher.JAVA_DIGIT.matchesAllOf(useFee);
        System.out.println("checkFee=" + checkFee);
        String SPLITTER = ".";
        String key = "ACTION_UPDATE.1.1_1";
        String action = StringUtils.trim(StringUtils.substringBefore(key, SPLITTER));//ACTION_UPDATE
        key = StringUtils.substringAfter(key, SPLITTER);//1.1_1
        String bizType = StringUtils.trim(StringUtils.substringBefore(key, SPLITTER));//1
        key = StringUtils.substringAfter(key, SPLITTER);//1_1
        String productType = StringUtils.trim(StringUtils.substringBefore(key, SPLITTER));
        System.out.println("action=" + action + "bizType=" + bizType + "productType=" + productType);

        System.out.print("fsd" + Long.MAX_VALUE);


    }
}
