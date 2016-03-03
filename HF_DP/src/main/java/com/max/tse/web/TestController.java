package com.max.tse.web;

import com.max.tse.common.context.AppContext;
import com.max.tse.common.enums.LearnType;
import com.max.tse.guava.eventBus.EventBusFactory;
import com.max.tse.guava.eventBus.EventBusRequest;
import com.max.tse.po.Result;
import com.max.tse.redis.client.SimpleRedisTestService;
import com.max.tse.reflect.tairTree.pojo.AgeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-11-23
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
@RequestMapping("/test/")
@Controller
public class TestController {

    @Resource
    private SimpleRedisTestService simpleRedisTestService;

    public static final Logger logger = LoggerFactory.getLogger(TestController.class);



    @RequestMapping("/first")
    @ResponseBody
    public Result test(@RequestParam("username") String username) {
        AppContext.setLearnType(LearnType.test);
        logger.info("test param={}", new Object[]{username});
        AppContext.releaseAll();
        return Result.successResult().addData("username", username);
    }


    @RequestMapping("/eventBus")
    @ResponseBody
    public Result testEventBus(@RequestParam("username") String username,
                               @RequestParam("password") String password) {
        AppContext.setLearnType(LearnType.guava);
        logger.info("testEventBus username={}, password={}", username, password);
        EventBusRequest eventBusRequest = new EventBusRequest();
        eventBusRequest.setUsername(username);
        eventBusRequest.setPassword(password);
        EventBusFactory.firstEventBus.post(eventBusRequest);
        AppContext.releaseAll();
        return Result.successResult().addData("eventBus", "success");
    }

    @RequestMapping("/redis/first")
    @ResponseBody
    public Result testRedisFirst(@RequestParam("key") String key, @RequestParam("value") String value) {
        try {
            simpleRedisTestService.set(key, value);
            String redisResult = simpleRedisTestService.get(key);
            return Result.successResult().addData("redisResult", redisResult);
        } catch (Exception e) {
            logger.error("redis first error", e);
            return Result.failResult().addErrmsg(e.getMessage());
        }
    }

    @RequestMapping("/redis/second")
    @ResponseBody
    public Result testRedisSecond(@RequestParam("key") String key) {
        try {
            String redisResult = simpleRedisTestService.del(key);
            return Result.successResult().addData("redisDel", redisResult).addData("redisValue", simpleRedisTestService.get(key));
        } catch (Exception e) {
            logger.error("redis second error", e);
            return Result.failResult().addErrmsg(e.getMessage());
        }
    }


}
