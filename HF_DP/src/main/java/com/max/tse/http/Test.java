package com.max.tse.http;

import com.google.common.collect.Maps;
import com.max.tse.http.httpclient.FileUploadService;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-13
 * Time: 下午5:56
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    private static final String TEST_URL = "http://10.86.54.179:8068/fuwuorder/open/refund/uploadProveImg?orderNo=cnb160411214010402&source=source";

    private static final String SOURCE = "fuwu.max.tse";


    public static void main(String[] args) {
        FileUploadService fileUploadService = new FileUploadService();

        Map<String, String> parameters  = Maps.newHashMap();

        parameters.put("orderNo", "cnb160411214010402");
        parameters.put("source", SOURCE);

        fileUploadService.upload(TEST_URL, new String[] {"03.jpg"}, "/home/xyb/Pictures/", parameters);
    }
}
