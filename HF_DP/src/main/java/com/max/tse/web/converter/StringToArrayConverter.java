package com.max.tse.web.converter;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-7-15
 * Time: 下午8:15
 * To change this template use File | Settings | File Templates.
 */
public class StringToArrayConverter implements Converter<String, ArrayList> {
    @Override
    public ArrayList convert(String source) {
        if (StringUtils.isNotBlank(source)) {
            return (ArrayList<String>) Splitter.on(",").omitEmptyStrings().splitToList(source);
        }
        return Lists.newArrayList();
    }
}
