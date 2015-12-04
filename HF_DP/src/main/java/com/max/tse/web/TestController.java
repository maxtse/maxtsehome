package com.max.tse.web;

import com.max.tse.common.context.AppContext;
import com.max.tse.common.enums.LearnType;
import com.max.tse.po.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    public static final Logger logger = LoggerFactory.getLogger(TestController.class);



    @RequestMapping("/first")
    @ResponseBody
    public Result test(@RequestParam("username") String username) {
        AppContext.setLearnType(LearnType.test);
        logger.info("test param={}", new Object[]{username});
        AppContext.releaseAll();
        return Result.successResult().addData("username", username);
    }


}
