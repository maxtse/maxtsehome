package com.max.tse.db.mybatis;

import com.alibaba.fastjson.JSON;
import com.max.tse.common.utils.DateFormatUtils;
import com.max.tse.db.mybatis.dao.OrderInfoDao;
import com.max.tse.db.mybatis.po.OrderInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-21
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/maxtse-*.xml"})
public class OrderInfoTest {

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoTest.class);

    private static final Random random = new Random();

    @Resource
    private OrderInfoDao orderInfoDao;

    @Test
    public void testInsert() throws Exception {
        OrderInfo orderInfo = generateOrderInfo();
        logger.info("orderInfo={}", JSON.toJSONString(orderInfo));
        orderInfoDao.insert(orderInfo);

    }

    private OrderInfo generateOrderInfo() {
        OrderInfo result = new OrderInfo();
        result.setOrderNo(generateOrderNo());
        result.setSource("test");
        result.setCreateTime(new Date());
        return result;
    }

    private String generateOrderNo() {
        String prefix = "abc";
        String timeString = DateFormatUtils.formatCurTimeLong();
        return prefix + timeString + generateRandomNumThree();
    }
    private int generateRandomNumThree() {
        return random.nextInt(900) + 100;
    }


}
