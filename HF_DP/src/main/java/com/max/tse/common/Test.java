package com.max.tse.common;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-9
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public class Test {
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
    }
}
