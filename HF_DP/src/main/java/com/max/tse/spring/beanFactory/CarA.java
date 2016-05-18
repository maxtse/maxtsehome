package com.max.tse.spring.beanFactory;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-11
 * Time: 下午5:12
 * To change this template use File | Settings | File Templates.
 */
public class CarA {

    private String desc;

    private CarB carB;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public CarB getCarB() {
        return carB;
    }

    public void setCarB(CarB carB) {
        this.carB = carB;
    }

    public CarA(){}

    public CarA(String desc) {
        this.desc = desc;
    }


}
