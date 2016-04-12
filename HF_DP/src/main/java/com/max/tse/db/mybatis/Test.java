package com.max.tse.db.mybatis;

import com.alibaba.fastjson.JSON;
import com.max.tse.db.mybatis.dao.UserDao;
import com.max.tse.db.mybatis.enums.AgeType;
import com.max.tse.db.mybatis.po.User;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-9
 * Time: 下午6:34
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public void testMybatis() {
        try {
            SqlSessionFactory sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder().
                    build(Resources.getResourceAsReader("mybatis/mybatis-config.xml"));
            SqlSession sqlSession = sqlSessionFactoryBuilder.openSession();
            UserDao userDao = sqlSession.getMapper(UserDao.class);


            User user = buildTestUser();
            //sqlSession.insert("userDao.addUser", user);//mapperStatement id=namespace+id ,参数
            userDao.addUser(user);
            sqlSession.commit();
            /*List<User> allUser = userDao.queryAll(new RowBounds(1, 2));
            System.out.println(JSON.toJSONString(allUser));*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public User buildTestUser () {
        User user = new User();
        user.setUsername("max_test");
        user.setPassword("maxpassword");
        user.setAgeType(AgeType.ADULT);

        return user;
    }
    public static void main(String[] args) {
        System.out.println(Executor.class.getInterfaces());
        Test test = new Test();
        /*try {

            Resources.getResourceAsReader("mybatis/mybatis-config.xml");
        } catch (Exception e) {
            e.printStackTrace();

        }*/
        for (int i =0; i < 100000; i++) {
            test.testMybatis();

        }


    }
}
