package com.max.tse.spring;

import java.beans.PropertyEditorSupport;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-10
 * Time: 下午5:28
 * To change this template use File | Settings | File Templates.
 */
public class TitlePositionEditor extends PropertyEditorSupport{

    private String[] options = {"Left", "Center", "Right"};

    @Override
    public String[] getTags() {
        return options;
    }

    @Override
    public String getJavaInitializationString() {
        return "" + getValue();
    }

    @Override
    public String getAsText() {
        int value = (Integer) getValue();
        return options[value];
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        for (int i = 0; i < options.length; i++) {
            if (options[i].equalsIgnoreCase(text)) {
                setValue(i);
                return;
            }
        }
    }
}
