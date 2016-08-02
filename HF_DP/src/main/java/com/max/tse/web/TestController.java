package com.max.tse.web;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.max.tse.common.context.AppContext;
import com.max.tse.common.enums.LearnType;
import com.max.tse.db.mybatis.dao.UserDao;
import com.max.tse.db.mybatis.po.User;
import com.max.tse.db.mybatis.service.UserService;
import com.max.tse.guava.eventBus.EventBusFactory;
import com.max.tse.guava.eventBus.EventBusRequest;
import com.max.tse.http.httpclient.FileUploadService;
import com.max.tse.po.Result;
import com.max.tse.redis.client.SimpleRedisTestService;
import com.max.tse.reflect.tairTree.pojo.AgeType;
import com.max.tse.spring.aop.ToWeave;
import com.max.tse.spring.aop.annoation.ParamLogMonitor;
import com.max.tse.spring.aop.annoation.ResultLogMonitor;
import com.max.tse.spring.template.VelocityTemplateService;
import com.max.tse.web.annoation.LogInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

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

    @Resource
    private FileUploadService fileUploadService;

    @Resource
    private VelocityTemplateService velocityTemplateService;

    @Resource
    private ToWeave toWeave;

    @Resource
    private UserDao userDao;

    @Resource
    private UserService userService;

    public static final Logger logger = LoggerFactory.getLogger(TestController.class);


    private static final String TEST_URL = "http://localhost:8080/max/tse/test/upload?orderNo=cnb160411214010402&source=source";

    private static final String SOURCE = "fuwu.max.tse";




    @RequestMapping("/first")
    @ResponseBody
    public Result test(@RequestParam("username") String username) {
        AppContext.setLearnType(LearnType.test);
        logger.info("test param={}", new Object[]{username});
        toWeave.testAround(username, "TestControllerpassword11232");
        toWeave.testBefore(username, "TestControllerpassword_before");
        toWeave.testAfterReturning(username, "TestControllerpasswordreturn");
        toWeave.testAfterReturning1(username, 15);
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

    @RequestMapping("/redis/string")
    @ResponseBody
    public Result testRedisString() {
        try {
            return simpleRedisTestService.testString();
        } catch (Exception e) {
            logger.error("testRedisString error", e);
            return Result.failResult().addErrmsg(e.getMessage());
        }
    }


    @RequestMapping("/redis/second")
    @ResponseBody
    public Result testRedisSecond(@RequestParam("key") String key) {
        try {
            boolean redisResult = simpleRedisTestService.del(key);
            return Result.successResult().addData("redisDel", redisResult).addData("redisValue", simpleRedisTestService.get(key));
        } catch (Exception e) {
            logger.error("redis second error", e);
            return Result.failResult().addErrmsg(e.getMessage());
        }
    }

    @RequestMapping("/upload")
    @ResponseBody
    public Result testUpload(@RequestParam("orderNo") String orderNo, @RequestParam("source") String source, HttpServletRequest request) {
        logger.info("orderNo={}, source={}", orderNo, source);
        try {
            logger.info("fileItem={}", JSON.toJSONString(fileUploadService.getFileItems(request)));
        } catch (Exception e) {
            logger.error("testUpload error", e);
            return Result.failResult().addErrmsg(e.getMessage());
        }
        return Result.successResult();


    }

    @RequestMapping("/uploadpre")
    @ResponseBody
    public Result testUploadPre() {

        Map<String, String> parameters  = Maps.newHashMap();

        parameters.put("orderNo", "cnb160411214010402");
        parameters.put("source", SOURCE);

        fileUploadService.upload(TEST_URL, new String[]{"03.jpg"}, "/home/xyb/Pictures/", parameters);
        return Result.successResult();

    }
    @LogInterceptor
    @RequestMapping("/myJsp")
    public ModelAndView myJsp(@RequestParam("username") String username) {
        ModelAndView result = new ModelAndView("myJsp");
        result.addObject("data", "{username:" + username + "}");
        return result;
    }

    @LogInterceptor
    @RequestMapping("/path/{username}")
    public ModelAndView testPath2(@PathVariable("username") String username) {
        ModelAndView result = new ModelAndView("myJsp");
        result.addObject("data", "username:" + username + "}");
        return result;
    }

    @LogInterceptor
    @RequestMapping("/MyVm")
    public ModelAndView myVm(@RequestParam("username") String username) {
        ModelAndView result = new ModelAndView("MyVm.vm");
        result.addObject("username", username);
        return result;
    }

    @LogInterceptor
    @RequestMapping("/template/vm")
    public ModelAndView testTemplateVm(@ModelAttribute("user") User user) {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("user", user);
        String templateString = velocityTemplateService.transform("userVm", paramMap);
        logger.info("templateString={}", templateString);

        ModelAndView modelAndView = new ModelAndView("myJsp");
        modelAndView.addObject("data", templateString);
        return modelAndView;

    }

    @LogInterceptor
    @RequestMapping("/mybatis")
    @ResponseBody
    public Object testMybatis(@ModelAttribute("user") User user) {
        logger.info("mybatis user={}", JSON.toJSONString(user));
        userDao.addUser(user);
        return Result.successResult();
    }

    @LogInterceptor
    @RequestMapping("/testCache")
    @ResponseBody
    public Object testMybatisCache() {
        User user = userService.queryTwice(100465);
        return Result.successResult().addData("user", user);
    }

    @ModelAttribute("user")
    public User getUser() {
        User user = new User();
        user.setAgeType(com.max.tse.db.mybatis.enums.AgeType.ADULT);
        user.setUsername("max tse");
        user.setPassword("maxtse2133");
        user.setId(100670);
        return user;
    }

    @ExceptionHandler
    public ModelAndView handleException(RuntimeException re, HttpServletRequest request) {
        logger.error(request.getRequestURI() + "error", re);
        return new ModelAndView("error");
    }

}
