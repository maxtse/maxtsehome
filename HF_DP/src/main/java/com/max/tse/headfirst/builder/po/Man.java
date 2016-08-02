package com.max.tse.headfirst.builder.po;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-12
 * Time: 下午11:58
 * To change this template use File | Settings | File Templates.
 */
public class Man {

    private String firstName;

    private String lastName;

    private String cardNum;

    private int age;

    private Date birthDay;

    public Man() {}

    public Man(String firstName, String lastName, String cardNum, int age, Date birthDay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cardNum = cardNum;
        this.age = age;
        this.birthDay = birthDay;
    }

    private Man(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.birthDay = builder.birthDay;
        this.cardNum = builder.cardNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String firstName;

        private String lastName;

        private String cardNum;

        private int age;

        private Date birthDay;

        public Builder () {

        }

        public Builder addFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder addLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder addAge(int age) {
            this.age = age;
            return this;
        }

        public Builder addBirthDay(Date birthDay) {
            this.birthDay = birthDay;
            return this;
        }

        public Builder addCardNum(String cardNum) {
            this.cardNum = cardNum;
            return this;
        }

        public Man build() {
            return new Man(this);
        }
    }
}
