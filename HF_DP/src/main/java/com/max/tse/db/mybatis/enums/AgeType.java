package com.max.tse.db.mybatis.enums;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-9
 * Time: 下午7:05
 * To change this template use File | Settings | File Templates.
 */
public enum AgeType {
    ADULT(1, "成人"),
    CHILD(0, "儿童");

    AgeType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public final int code;

    public final String desc;

    public static AgeType fromCode(int code) {
        for (AgeType ageType : AgeType.values()) {
            if (ageType.code == code) {
                return ageType;
            }
        }
        return null;
    }

}
