package com.max.tse.db.mybatis.dao;

import com.max.tse.db.mybatis.DaoAnnotation;
import com.max.tse.db.mybatis.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-9
 * Time: 下午1:14
 * To change this template use File | Settings | File Templates.
 */
@DaoAnnotation
public interface UserDao {

    public User queryById(@Param("id") int id);

    public List<User> queryAll(@Param("rowBounds")RowBounds rowBounds);
    public int addUser(@Param("user") User user);
    public int deleteUser(@Param("id") int id);

    public int updateUser(@Param("id") int id, @Param("password") String password);
}
