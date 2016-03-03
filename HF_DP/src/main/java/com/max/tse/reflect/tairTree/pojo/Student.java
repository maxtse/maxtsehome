package com.max.tse.reflect.tairTree.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-12
 * Time: 下午6:40
 * To change this template use File | Settings | File Templates.
 */
public class Student {

    private String studentName;

    private AgeType ageType;

    private String studentNum;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public AgeType getAgeType() {
        return ageType;
    }

    public void setAgeType(AgeType ageType) {
        this.ageType = ageType;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }
}
