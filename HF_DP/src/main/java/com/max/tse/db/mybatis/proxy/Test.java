package com.max.tse.db.mybatis.proxy;

import com.max.tse.db.mybatis.dao.UserDao;
import com.max.tse.db.mybatis.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-13
 * Time: 下午2:33
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {
        UserDao userDao = (UserDao)Proxy.newProxyInstance(UserDao.class.getClassLoader(), new Class[] {UserDao.class}, new MyTestProxyInvocationHandler(new UserDao() {
            @Override
            public User queryById(@Param("id") int id) {
                return null;
            }

            @Override
            public List<User> queryAll(@Param("rowBounds") RowBounds rowBounds) {
                return null;
            }

            @Override
            public int addUser(@Param("user") User user) {
                return 100;
            }

            @Override
            public int deleteUser(@Param("id") int id) {
                return 0;
            }

            @Override
            public int updateUser(@Param("id") int id, @Param("password") String password) {
                return 0;
            }
        }));
        userDao.getClass();
        System.out.println(userDao.addUser(new User()));
    }
}
