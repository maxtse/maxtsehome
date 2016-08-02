package com.max.tse.spring;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-10
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */
public class ChartBean extends JPanel {

    private int titlePosition = 100;

    private boolean inverse;

    public int getTitlePosition() {
        return titlePosition;
    }

    public void setTitlePosition(int titlePosition) {
        this.titlePosition = titlePosition;
    }

    public boolean isInverse() {
        return inverse;
    }

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }
}
