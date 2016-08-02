package com.max.tse.spring.reflect;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-29
 * Time: 下午3:58
 * To change this template use File | Settings | File Templates.
 */
public class Car {

    private String brand;

    private String color;

    private int maxSpeed;

    public Car() {}

    public Car(String brand, String color, int maxSpeed) {
        this.brand = brand;
        this.color = color;
        this.maxSpeed = maxSpeed;
    }

    public void introduce() {
        System.out.println("brand:" + brand + ", color:" + color + ",maxSpeed:" + maxSpeed);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
