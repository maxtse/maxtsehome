package com.max.tse.common;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-9
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    private static String buildUploadSuccessResult() {
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("url", "http://www.baidu.com");
        dataMap.put("fileName", "i_am_test.jpg");

        return JSON.toJSONString(success(dataMap));
    }

    public static Map<String, Object> success(Object data) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("ret", true);
        map.put("errmsg", "success");
        map.put("data", data);
        return map;
    }

    public static void testIllness() {
        String originString = "1、病退凭证内的主要内容（病人姓名、时间、病症等信息）+" +
                "3、县级（含）以上医疗单位出具的有主治医生签字的正规诊断证明+" +
                "2、病历+" +
                "4、医院电脑打印的200元（含）以上医药费收费单+" +
                "5、病退旅客需提供证件原件";
        List<String> splitStringList = Lists.newArrayList(Splitter.on("+").splitToList(originString));
        System.out.println(JSON.toJSONString(splitStringList));
        Collections.sort(splitStringList);
        System.out.println(JSON.toJSONString(splitStringList));

    }

    public static void main(String[] args) {
        String testProductType = "2_901";
        System.out.println(NumberUtils.toInt(testProductType.substring(testProductType.lastIndexOf("_") + 1, testProductType.length())));

        InetSocketAddress inetSocketAddress= new InetSocketAddress("127.0.0.1", 6380);
        System.out.println(inetSocketAddress.isUnresolved());

        System.out.println(new BigDecimal("30.0"));
        String test = "{\"vendorInfo\":{\"companyName\":\"低价特惠\",\"worktime\":\"周一至周日 00:00-23:59\",\"phone\":\"10101234\",\"phone1\":null,\"logoUrl\":\"\",\"dom\n" +
                "ain\":null,\"companyFullName\":\"去哪儿国际机票自营\",\"address\":\"北京市石景山区石景山路乙18号院3号楼18层2005\"},\"checkInOrder\":{\"id\":\"5008144196950ch\n" +
                "eckin2\",\"flightNum\":\"HX401\",\"depDate\":\"2016-07-31\",\"depTime\":\"19:10\",\"depAirportCode\":\"HKG\",\"arrDate\":\"2016-07-31\",\"arrTime\":\"22:10\",\"arrAirpor\n" +
                "tCode\":\"BKK\",\"passengerName\":\"HOU/YU\",\"passengerCardNum\":\"SF8UJNBNHS#\",\"status\":1,\"checkInDocUrl\":\"\",\"price\":30.000000,\"updateTime\":null,\"servi\n" +
                "ceDeadline\":1469920200000,\"handleTime\":\"2016-05-26 20:20\"},\"checkInSeatCode\":0,\"originalOrderNo\":\"5008144196950\"}";
        System.out.println(StringUtils.replace(test, "null,", "\"null\","));

        System.out.println(buildUploadSuccessResult());

        testIllness();

        String toSplit = StringUtils.replaceEach("dfds", new String[] {"[", "]"}, new String[] {"", ""});
        System.out.println(toSplit);

        System.out.println(StringUtils.replace("{\"ret\":true,\"errmsg\":\"fds\"}", "\"", "&quot;"));

    }
}
