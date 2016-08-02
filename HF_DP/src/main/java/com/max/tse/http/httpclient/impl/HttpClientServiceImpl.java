package com.max.tse.http.httpclient.impl;

import com.max.tse.http.httpclient.HttpClientService;
import com.max.tse.http.httpclient.enums.HttpMethodEnum;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-14
 * Time: 上午11:11
 * To change this template use File | Settings | File Templates.
 * Note:http请求
 * 可以抽象出来这么几个东西
 * 一个是httpClient配置
 * 一个是httpMethod
 * 一个是请求参数
 * 一个是响应结果
 * 解析
 */
@Service("httpClientService")
public class HttpClientServiceImpl implements HttpClientService{

    @Override
    public HttpClient getHttpClient() {
        return null;
    }

    @Override
    public GetMethod getGetMethod() {
        return null;
    }

    @Override
    public HttpMethod getHttpMethod(HttpMethodEnum httpMethodEnum) {
        if (httpMethodEnum == null) {
            return getGetMethod();
        }

        switch (httpMethodEnum) {
            case Get:
                return getGetMethod();
            case Post:
                return getPostMethod();
            default:
                return getGetMethod();
        }

    }

    @Override
    public PostMethod getPostMethod() {
        return null;
    }

    @Override
    public String doGet(String url, Map<String, String> headParameters) {
        return null;
    }

    @Override
    public String doGet(String url, Map<String, String> parameters, Map<String, String> headParameters) {
        return null;
    }

    @Override
    public String doPost(String url, Map<String, String> parameters, Map<String, String> headParameters) {
        return null;
    }

    @Override
    public String uploadFile(String url, Map<String, String> parameters, Map<String, String> headParameters) {
        return null;
    }
}
