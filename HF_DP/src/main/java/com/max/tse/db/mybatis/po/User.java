package com.max.tse.db.mybatis.po;

import com.max.tse.db.mybatis.enums.AgeType;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-9
 * Time: 下午1:13
 * To change this template use File | Settings | File Templates.
 * Note:与数据库中的对应起来
 */
@Alias("user")
public class User implements Serializable{
    private static final long serialVersionUID = 1998162016687621121L;
    private long id;

    private String username;

    private String password;

    private AgeType ageType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AgeType getAgeType() {
        return ageType;
    }

    public void setAgeType(AgeType ageType) {
        this.ageType = ageType;
    }
}
