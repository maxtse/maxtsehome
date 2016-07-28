package com.max.tse.db.mybatis.service;

import com.max.tse.db.mybatis.dao.UserDao;
import com.max.tse.db.mybatis.po.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.Statement;

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

    @Resource
    SqlSessionTemplate sqlSessionTemplate;

    public User queryById(int id) {
        long start = System.currentTimeMillis();
        try {
            return userDao.queryById(id);

        } finally {
            logger.info("queryById time={}", System.currentTimeMillis() - start);
        }
    }
    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = Exception.class,
            propagation = Propagation.REQUIRED)
    public User queryTwice(int id) {

        userDao.updateUser(id, "121x11");
        long start = System.currentTimeMillis();
        try {
            logger.info("user time1={}", queryById(id));
        } finally {
            logger.info("time1={}", System.currentTimeMillis() - start);
        }

        updateUser(id, "neswo");

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
        changeDb();
        userDao.updateUser(1, "133221");
        long start2 = System.currentTimeMillis();
        try {
            logger.info("user time3={}", userDao.queryById(id));

            return userDao.queryById(id);

        } finally {
            logger.info("time3={}", System.currentTimeMillis() - start2);
        }

    }
    public void updateUser(int id, String password) {
        userDao.updateUser(id, password);

    }

    public void changeDb() {
        Connection connection = sqlSessionTemplate.getConnection();
        try {

            Statement statement = connection.createStatement();
            statement.execute("use learn_01");
        } catch (Exception e) {
            logger.error("changeDb error", e);
        }
    }

}
