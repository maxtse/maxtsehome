package com.max.tse.http.httpclient;

import com.max.tse.http.httpclient.enums.HttpMethodEnum;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-14
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
public interface HttpClientService {



    public HttpClient getHttpClient();

    public GetMethod getGetMethod();

    public HttpMethod getHttpMethod(HttpMethodEnum methodEnum);

    public PostMethod getPostMethod();

    public String doGet(String url,Map<String, String> headParameters);

    public String doGet(String url, Map<String, String> parameters, Map<String, String> headParameters);

    public String doPost(String url, Map<String, String> parameters, Map<String, String> headParameters);

    public String uploadFile(String url, Map<String, String> parameters, Map<String, String> headParameters);

}
