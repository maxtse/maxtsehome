package com.max.tse.guava.eventBus;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-12-4
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public class EventBusRequest {

    private String username;

    private String password;

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

    @Override
    public String toString() {
        return "EventBusRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
