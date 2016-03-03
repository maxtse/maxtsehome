package com.max.tse.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-12-29
 * Time: 下午4:22
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static final String testString = "[\"zhangsan\", \"lisi\"]";


    public static void testJsonArray() {
        List<String> jsonList = JSON.parseArray(testString, String.class);
        System.out.println(jsonList.contains("zhangsan"));
    }

    public static void testJsonObjectArray() {
        JSONArray jsonArray = JSONObject.parseArray(testString);
        System.out.println(jsonArray.contains("zhangsan"));
    }

    public static void main(String[] args) {

        String jsonString = "{\"fromMainOrder\":{\"bizType\":2,\"bizOrderNo\":\"cj1151228105650251f102f\"}}";
        JSONObject jsonObject = JSON.parseObject(jsonString);
        System.out.println(jsonObject.getJSONObject("fromMainOrder").getString("bizType"));
        String callInfoString = "{\n" +
                "ret: true,\n" +
                "data: [\n" +
                "{\n" +
                "id: 0,\n" +
                "name: \"泰国航空\",\n" +
                "code: \"TG\",\n" +
                "hotline: \"01085150088\",\n" +
                "decriptionHotline: \"enc3234724802976\"\n" +
                "}\n" +
                "]\n" +
                "}";
        AirlineResult airlineResult = JSON.parseObject(callInfoString, AirlineResult.class);
        System.out.println(JSON.toJSONString(airlineResult));

        String testMatch = "1_1.*|2_1.*|2_2.*|2_3.* ";
        Pattern internationalPattern = Pattern.compile(testMatch);
         if (internationalPattern.matcher("2_2").find()) {
            System.out.println("fsddf");
        }
        System.out.println("****************");
        testJsonArray();
        System.out.println("****************");
        testJsonObjectArray();

    }

}
