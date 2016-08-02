package com.max.tse.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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

        System.out.println("fsd" + Long.MAX_VALUE);
        System.out.println(NumberUtils.toDouble("34.00000"));
        System.out.println(NumberUtils.toInt("536.000000"));
        System.out.println(parseFlightPrice("4a5"));
        String interJson = "{\"ret\":false,\"errmsg\":\"系统错误\",\"errcode\":-1,\"data\":{}}";
        testInterJson(interJson);

        int count = 0;
        List<String> testList1 = ImmutableList.of("1", "2");
        for (String string1 : testList1) {
            System.out.println(++count);
        }

        String brString = "sfaf<br>sdfd<br>";
        brString = brString.toString().replaceAll("<br>", "，");//替换
        if (brString.lastIndexOf("，") == brString.length() - 1) {//末尾的逗号舍去
            brString = brString.substring(0, brString.length() - 1);
        }
        System.out.println("br=== " + brString);

        String json = "{\"m\n" +
                "essageId\":\"160720.185657.10.86.54.165.8268.2545\",\"subject\":\"flightqm.pnrsys.pnrinfos.betac\",\"attrs\":{\"qmq_createTIme\":1469012217419,\"qmq_traceId\":\"f_pnr_pnrsys_160720.185657.10.86.54.165.8268.124203_1\",\"qm\n" +
                "q_prefix\":\"flightqm.pnrsys.pnrinfos.betac\",\"qmq_spanId\":\"1.1\",\"pnrInfosJson\":\"{\\\"flightInfos\\\":\\\"[{\\\\\\\"actionCode\\\\\\\":\\\\\\\"HK\\\\\\\",\\\\\\\"arrivalCity\\\\\\\":\\\\\\\"KMG\\\\\\\",\\\\\\\"arrivalTime\\\\\\\":\\\\\\\"13:00:00\\\\\\\",\\\\\\\"cab\n" +
                "in\\\\\\\":\\\\\\\"Y\\\\\\\",\\\\\\\"departureCity\\\\\\\":\\\\\\\"PEK\\\\\\\",\\\\\\\"departureDate\\\\\\\":\\\\\\\"2016-08-30\\\\\\\",\\\\\\\"departureTime\\\\\\\":\\\\\\\"08:10:00\\\\\\\",\\\\\\\"flightNum\\\\\\\":\\\\\\\"3U8838\\\\\\\",\\\\\\\"id\\\\\\\":7990,\\\\\\\"isShare\\\\\\\":false,\\\\\\\n" +
                "\"mainFlightNum\\\\\\\":\\\\\\\"3U8838\\\\\\\",\\\\\\\"pnrId\\\\\\\":7833,\\\\\\\"realFlightNum\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"share\\\\\\\":false}]\\\",\\\"newPnrInfos\\\":\\\"{\\\\\\\"actionCode\\\\\\\":\\\\\\\"HK\\\\\\\",\\\\\\\"available\\\\\\\":false,\\\\\\\"bound\\\\\\\":false,\\\\\\\"\n" +
                "canceled\\\\\\\":false,\\\\\\\"canceledButHasError\\\\\\\":false,\\\\\\\"contactTel\\\\\\\":\\\\\\\"15890790679\\\\\\\",\\\\\\\"createTime\\\\\\\":1469012217000,\\\\\\\"creator\\\\\\\":\\\\\\\"sys\\\\\\\",\\\\\\\"exeOfficeId\\\\\\\":\\\\\\\"SZX403\\\\\\\",\\\\\\\"exeType\\\\\\\":\\\n" +
                "\\\\\"IBE\\\\\\\",\\\\\\\"id\\\\\\\":7833,\\\\\\\"invalid\\\\\\\":false,\\\\\\\"invalidButHasError\\\\\\\":false,\\\\\\\"maxTimeToLive\\\\\\\":1000,\\\\\\\"new\\\\\\\":false,\\\\\\\"numOfPassenger\\\\\\\":1,\\\\\\\"numOfSegment\\\\\\\":1,\\\\\\\"officeId\\\\\\\":\\\\\\\"SZX403\\\\\\\n" +
                "\",\\\\\\\"pnr\\\\\\\":\\\\\\\"A9150S\\\\\\\",\\\\\\\"qunardid\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"riskLevel\\\\\\\":0,\\\\\\\"source\\\\\\\":\\\\\\\"f.tts.smart.pnr\\\\\\\",\\\\\\\"status\\\\\\\":\\\\\\\"d\\\\\\\",\\\\\\\"statusCode\\\\\\\":4,\\\\\\\"supplierTel\\\\\\\":\\\\\\\"010-89677788\\\\\\\",\\\\\\\"\n" +
                "timeToLive\\\\\\\":1469012817000,\\\\\\\"tryCancelCount\\\\\\\":0,\\\\\\\"type\\\\\\\":0,\\\\\\\"updateTime\\\\\\\":1469012217000,\\\\\\\"used\\\\\\\":true}\\\",\\\"oldPnrInfos\\\":\\\"{\\\\\\\"actionCode\\\\\\\":\\\\\\\"HK\\\\\\\",\\\\\\\"available\\\\\\\":true,\\\\\\\"bound\\\n" +
                "\\\\\":true,\\\\\\\"canceled\\\\\\\":false,\\\\\\\"canceledButHasError\\\\\\\":false,\\\\\\\"contactTel\\\\\\\":\\\\\\\"15890790679\\\\\\\",\\\\\\\"createTime\\\\\\\":1469012217000,\\\\\\\"creator\\\\\\\":\\\\\\\"sys\\\\\\\",\\\\\\\"exeOfficeId\\\\\\\":\\\\\\\"SZX403\\\\\\\",\\\\\\\"\n" +
                "exeType\\\\\\\":\\\\\\\"IBE\\\\\\\",\\\\\\\"id\\\\\\\":7833,\\\\\\\"invalid\\\\\\\":false,\\\\\\\"invalidButHasError\\\\\\\":false,\\\\\\\"maxTimeToLive\\\\\\\":1000,\\\\\\\"new\\\\\\\":false,\\\\\\\"numOfPassenger\\\\\\\":1,\\\\\\\"numOfSegment\\\\\\\":1,\\\\\\\"officeId\\\\\\\":\n" +
                "\\\\\\\"SZX403\\\\\\\",\\\\\\\"pnr\\\\\\\":\\\\\\\"A9150S\\\\\\\",\\\\\\\"qunardid\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"riskLevel\\\\\\\":0,\\\\\\\"source\\\\\\\":\\\\\\\"f.tts.smart.pnr\\\\\\\",\\\\\\\"status\\\\\\\":\\\\\\\"b\\\\\\\",\\\\\\\"statusCode\\\\\\\":1,\\\\\\\"supplierTel\\\\\\\":\\\\\\\"010-8967\n" +
                "7788\\\\\\\",\\\\\\\"timeToLive\\\\\\\":1469012817000,\\\\\\\"tryCancelCount\\\\\\\":0,\\\\\\\"type\\\\\\\":0,\\\\\\\"updateTime\\\\\\\":1469012217000,\\\\\\\"used\\\\\\\":false}\\\",\\\"orderNo\\\":\\\"cta160720185631123\\\",\\\"passengerInfos\\\":\\\"[{\\\\\\\"birthd\n" +
                "ay\\\\\\\":\\\\\\\"1980-01-10 00:00:00.0\\\\\\\",\\\\\\\"cardNum\\\\\\\":\\\\\\\"E1509049\\\\\\\",\\\\\\\"cardType\\\\\\\":\\\\\\\"PP\\\\\\\",\\\\\\\"id\\\\\\\":9288,\\\\\\\"name\\\\\\\":\\\\\\\"王兰兰\\\\\\\",\\\\\\\"pnrId\\\\\\\":7833,\\\\\\\"type\\\\\\\":\\\\\\\"ADULT\\\\\\\"}]\\\"}\",\"qmq_regist\n" +
                "ry\":\"zookeeper://zk.beta.corp.qunar.com:2181?backup=zk1.beta.corp.qunar.com:2181,zk2.beta.corp.qunar.com:2181,zk3.beta.corp.qunar.com:2181&group=/qmq/broker/group/new\",\"qmq_expireTime\":1469015817419,\"qmq_r\n" +
                "eliabilityLevel\":\"High\",\"qmq_times\":1,\"qmq_brokerGroupName\":\"new\",\"qmq_consumerGroupName\":\"fuwu_ticket_platform\"}}";


    }

    private static int parseFlightPrice(String priceString) {
        if (StringUtils.isBlank(priceString)) {
            return 0;
        }
        try {
            BigDecimal bigDecimal = new BigDecimal(NumberUtils.toDouble(priceString));
            return bigDecimal == null ? 0 : bigDecimal.intValue();
        } catch (Exception e) {
            return 0;
        }

    }

    public static void testInterJson(String json) {
        View view= JSONObject.parseObject(json, View.class);
        System.out.print(view!=null&&view.hasData());
    }

    private static final class View{
        Boolean ret;
        String errmsg;
        Integer errcode;
        Map<String,Integer> data;

        public boolean hasData(){
            return ret!=null&&ret&&data!=null&&!data.isEmpty();
        }

        public Boolean getRet() {
            return ret;
        }

        public void setRet(Boolean ret) {
            this.ret = ret;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        public Integer getErrcode() {
            return errcode;
        }

        public void setErrcode(Integer errcode) {
            this.errcode = errcode;
        }

        public Map<String, Integer> getData() {
            return data;
        }

        public void setData(Map<String, Integer> data) {
            this.data = data;
        }

        public String toString(){
            return JSONObject.toJSONString(this);
        }
    }

}
