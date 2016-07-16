package com.max.tse.db.mybatis.service;

import com.max.tse.db.mybatis.dao.UserDao;
import com.max.tse.db.mybatis.po.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-7-16
 * Time: 上午11:33
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserDao userDao;

    public User queryById(int id) {
        long start = System.currentTimeMillis();
        try {
            return userDao.queryById(id);

        } finally {
            logger.info("queryById time={}", System.currentTimeMillis() - start);
        }
    }
    @Transactional
    public User queryTwice(int id) {

        userDao.updateUser(id, "mynewpassword1231");
        long start = System.currentTimeMillis();
        try {
            logger.info("user time1={}", queryById(id));
        } finally {
            logger.info("time1={}", System.currentTimeMillis() - start);
        }

        long start1 = System.currentTimeMillis();
        try {
            logger.info("user time2={}", userDao.queryById(id));

        } finally {
            logger.info("time2={}", System.currentTimeMillis() - start1);
        }
        /*Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("id",id);
        paramMap.put("password", "12425");
        sqlSessionTemplate.update("com.max.tse.db.mybatis.dao.UserDao.updateUser", paramMap);*/
        userDao.updateUser(id, "mynewpassword111");
        long start2 = System.currentTimeMillis();
        try {
            logger.info("user time3={}", userDao.queryById(id));

            return userDao.queryById(id);

        } finally {
            logger.info("time3={}", System.currentTimeMillis() - start2);
        }

    }
}
