package com.max.tse.common.utils;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
    private static HttpClient fast_client = new HttpClient(new MultiThreadedHttpConnectionManager());
    private static HttpClient fast_client_delay = new HttpClient(new MultiThreadedHttpConnectionManager());
    public static final String BROWSER_COMPATIBILITY = "compatibility";

    static {
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        client.getHttpConnectionManager().getParams().setSoTimeout(60000);
        client.getParams().setConnectionManagerTimeout(3000l);
        client.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(10);
        client.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler() {

            public boolean retryMethod(HttpMethod method, IOException exception, int executionCount) {
                return executionCount < 3;
            }
        });

        fast_client.getHttpConnectionManager().getParams().setConnectionTimeout(500);
        fast_client.getHttpConnectionManager().getParams().setSoTimeout(2000);
        fast_client.getParams().setConnectionManagerTimeout(1000l);
        fast_client.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(10);

        fast_client_delay.getHttpConnectionManager().getParams().setConnectionTimeout(500);
        fast_client_delay.getHttpConnectionManager().getParams().setSoTimeout(2000);
        fast_client_delay.getParams().setConnectionManagerTimeout(1000l);
        fast_client_delay.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(5);
    }

    /**
     * 通过HttpClient发送 http GET 请求 发送时将当前 request中的所有请求头headers也原封不动的发送过去
     * 判断响应流是否是GZIP格式,如果是则设置响应头Content-Encoding说明内容编码为gzip压缩格式,以便浏览器解压gzip 将响应流输出到当前response中
     */
    /*
     * public static void getUrlAndOutToResponse(String url, HttpServletRequest request, HttpServletResponse response) {
     * GetMethod method = new GetMethod(url); method.getParams().setParameter("http.protocol.cookie-policy",
     * BROWSER_COMPATIBILITY); method.setFollowRedirects(true); Enumeration<String> names = request.getHeaderNames();
     * while (names.hasMoreElements()) { String name = names.nextElement(); String head = request.getHeader(name);
     * method.setRequestHeader(name, head); } try { AppContext.releaseDbCon(); setRequestHeadersForTrace(method); int
     * code = client.executeMethod(method); logger.warn("returncode {}", code); if (code == 200) { InputStream in =
     * method.getResponseBodyAsStream(); //判断是否是GZIP格式,如果是则转为GZIPInputStream byte[] header = new byte[2]; int result =
     * in.read(header); int ss = (header[0] & 0xff) | ((header[1] & 0xff) << 8); if (result != -1 && ss ==
     * GZIPInputStream.GZIP_MAGIC) {// 判断是否是GZIP格式 logger.warn("判断是否是GZIP: {}", true);
     * response.setHeader("Content-Encoding", "gzip");//设置响应头Content-Encoding说明内容编码为gzip压缩格式,以便浏览器解压gzip }
     * response.getOutputStream().write(header, 0, result); byte[] buffer = new byte[1024]; int length = 0; while
     * ((length = in.read(buffer)) != -1) { response.getOutputStream().write(buffer, 0, length); } in.close();
     * response.getOutputStream().flush(); response.getOutputStream().close(); response.flushBuffer(); } else {
     * logger.warn("getUrlAndOutResponse url:{} returncode {}", url, code); } } catch (Exception e) {
     * logger.error("getUrlAndOutResponse fail: url=" + url, e); logError(method); } finally {
     * method.releaseConnection(); } }
     */
    public static String get(String url) {
        GetMethod method = new GetMethod(url);
        method.setFollowRedirects(true);
        try {
            // AppContext.releaseDbCon();

            // setRequestHeadersForTrace(method);
            int code = client.executeMethod(method);
            if (code == 200) {
                long contentLength = method.getResponseContentLength();
                if (contentLength == -1)
                    return method.getResponseBodyAsString(Integer.MAX_VALUE);
                else
                    return method.getResponseBodyAsString();
            } else {
                logger.warn("get url:{} return {}", url, code);
            }
        } catch (Exception e) {
            logger.error("push fail: url=" + url, e);
        } finally {
            method.releaseConnection();
        }
        return null;
    }

    /*
     * public static String fastGet(String url) { GetMethod method = new GetMethod(url);
     * method.setFollowRedirects(true); try { AppContext.releaseDbCon(); setRequestHeadersForTrace(method); int code =
     * fast_client.executeMethod(method); if (code == 200) { long contentLength = method.getResponseContentLength(); if
     * (contentLength == -1) return method.getResponseBodyAsString(Integer.MAX_VALUE); else return
     * method.getResponseBodyAsString(); } else { logger.warn("get url:{} return {}", url, code); } } catch (Exception
     * e) { logger.error("push fail: url=" + url, e); } finally { method.releaseConnection(); } return null; }
     */

    public enum ClientType {
        FAST, NORMAL, DELAY// 延误险专用
    }

    /*
     * public static String postJson(String url, String body ,ClientType clientType){ PostMethod method = new
     * PostMethod(url); try{ method.setRequestEntity(new StringRequestEntity(body, "application/json", "UTF-8"));
     * setRequestHeadersForTrace(method); AppContext.releaseDbCon(); int code = 0; switch(clientType){ case FAST: code =
     * fast_client.executeMethod(method); break; case DELAY: code = fast_client_delay.executeMethod(method); break; case
     * NORMAL: code = client.executeMethod(method); break; default: logger.error("error ClientType:" +
     * clientType.name()); return null; } if (code == 200) { long contentLength = method.getResponseContentLength(); if
     * (contentLength == -1) return method.getResponseBodyAsString(Integer.MAX_VALUE); else return
     * method.getResponseBodyAsString(); } }catch(Exception e){ logger.error("push fail: url=" + url + ", body=" + body
     * + ", ClientType=" + clientType.name(), e); }finally{ method.releaseConnection(); } return null; }
     */

    public static String post(String url, Map<String, String> params, String body, ClientType clientType) {
        PostMethod method = new PostMethod(url);
        try {
            method.setRequestEntity(new StringRequestEntity(body, "application/x-www-form-urlencoded", "UTF-8"));
            // setRequestHeadersForTrace(method);
            if (params != null) {
                for (String name : params.keySet()) {
                    method.addParameter(name, params.get(name));
                }
            }
            // AppContext.releaseDbCon();

            int code = 0;

            switch (clientType) {
                case FAST:
                    code = fast_client.executeMethod(method);
                    break;
                case DELAY:
                    code = fast_client_delay.executeMethod(method);
                    break;
                case NORMAL:
                    code = client.executeMethod(method);
                    break;
                default:
                    logger.error("error ClientType:" + clientType.name());
                    return null;
            }

            if (code == 200) {
                long contentLength = method.getResponseContentLength();
                if (contentLength == -1)
                    return method.getResponseBodyAsString(Integer.MAX_VALUE);
                else
                    return method.getResponseBodyAsString();
            }
        } catch (Exception e) {
            logger.error("push fail: url=" + url + ", body=" + body + ", params=" + params + ", ClientType="
                    + clientType.name(), e);
        } finally {
            method.releaseConnection();
        }
        return null;
    }

    public static String get(String url, javax.servlet.http.Cookie[] cookies) {
        GetMethod method = new GetMethod(url);
        method.setFollowRedirects(true);
        try {
            method.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            StringBuffer tmpcookies = new StringBuffer();
            for (javax.servlet.http.Cookie c : cookies) {
                //Cookie nc = ApacheCookieUtils.apacheCookieFromServletCookie(c);
                //tmpcookies.append(nc.toString()).append(";");
            }
            logger.info("url={},cookie={}", url, tmpcookies.toString());
            method.setRequestHeader("Cookie", tmpcookies.toString());
            int code = client.executeMethod(method);
            if (code == 200) {
                long contentLength = method.getResponseContentLength();
                if (contentLength == -1)
                    return method.getResponseBodyAsString(Integer.MAX_VALUE);
                else
                    return method.getResponseBodyAsString();
            } else {
                logger.warn("get url:{} return {}", url, code);
            }
        } catch (Exception e) {
            logger.error("push fail: url=" + url, e);
        } finally {
            method.releaseConnection();
        }
        return null;
    }
    /*
     * public static String post(String url, String body) { return post(url, null, body, ClientType.NORMAL); } public
     * static String getFromInputStream(String url) { GetMethod method = new GetMethod(url);
     * method.setFollowRedirects(true); try { AppContext.releaseDbCon(); setRequestHeadersForTrace(method); int code =
     * client.executeMethod(method); if (code == 200) { InputStream in = method.getResponseBodyAsStream();
     * InputStreamReader isr = new InputStreamReader(in, "UTF-8"); BufferedReader buffRead = new BufferedReader(isr);
     * StringBuffer inputLine = new StringBuffer(); String temp = null; while ((temp = buffRead.readLine()) != null) {
     * inputLine.append(temp); } return inputLine.toString(); } else { logger.warn("get url:{} return {}", url, code); }
     * } catch (Exception e) { logger.error("push fail: url=" + url, e); } finally { method.releaseConnection(); }
     * return null; } public static String postForm(String url, String body) { PostMethod method = new PostMethod(url);
     * // method.setFollowRedirects(true); try { method.setRequestEntity(new StringRequestEntity(body,
     * "application/x-www-form-urlencoded", "UTF-8")); AppContext.releaseDbCon(); setRequestHeadersForTrace(method); int
     * code = client.executeMethod(method); logger.info("voucher response code:"+code); if (code == 200) { InputStream
     * in = method.getResponseBodyAsStream(); InputStreamReader isr = new InputStreamReader(in, "UTF-8"); BufferedReader
     * buffRead = new BufferedReader(isr); StringBuffer inputLine = new StringBuffer(); String temp = null; while ((temp
     * = buffRead.readLine()) != null) { inputLine.append(temp); } return inputLine.toString(); } } catch (Exception e)
     * { logger.error("push fail: url=" + url + ", body=" + body, e); } finally { method.releaseConnection(); } return
     * null; } public static String getFast(String url) { GetMethod method = new GetMethod(url);
     * method.setFollowRedirects(true); try { AppContext.releaseDbCon(); setRequestHeadersForTrace(method); int code =
     * fast_client.executeMethod(method); if (code == 200) { long contentLength = method.getResponseContentLength(); if
     * (contentLength == -1) return method.getResponseBodyAsString(Integer.MAX_VALUE); else return
     * method.getResponseBodyAsString(); } else { logger.warn("get url:{} return {}", url, code); } } catch (Exception
     * e) { logger.error("push fail: url=" + url, e); } finally { method.releaseConnection(); } return null; }
     */

    /**
     *
     * 根据AppContext中的变量，设置GET或者POST的request header (虽然RFC中request header的key不区分大小写,但我们采用约定好的key)
     *
     * @param httpMethod
     */
    /*
     * public static void setRequestHeadersForTrace(HttpMethod httpMethod) { String bookTrace =
     * AppContext.getBookTrace(); if (StringUtils.isNotBlank(bookTrace)) { httpMethod.setRequestHeader("bookTrace",
     * bookTrace); try { logger.info("set {} requestHeader bookTrace:{}", httpMethod.getURI(), bookTrace); } catch
     * (URIException e) { logger.error("getURI ERROR", e); } } }
     */
    /*
     * private static void logError(HttpMethod method) { try {
     * logger.error("logError real URI={}\nRequestHeaders={}\nResponseHeaders={}", method.getURI(),
     * method.getRequestHeaders(), method.getResponseHeaders()); } catch (Exception e) { logger.error("getURI error",
     * e); } } public static void main(String args[]) { String a = getFast(
     * "http://fuwu.qunar.com/orderdetail/order/ajaxGetOrderLogs?orderNo=td3140121182702156&orderId=191921857&domain=dev3.tts.qunar.com"
     * ); java.lang.reflect.Type stringStringMap = new TypeToken<Map<String, Object>>(){}.getType(); Map<String,Object>
     * map1 = (Map<String,Object>) JSON.parse(a); List<String> b = (List<String>)map1.get("data");
     * System.out.println("**************" + b.size()); }
     */

    /**
     * 获取QunarGlobal cookie
     *
     * @param request
     * @return
     */
    public static String getGlobalCookie(HttpServletRequest request) {
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if ("QunarGlobal".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String getIPFromRequest(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        return request.getHeader("X-Real-IP");
    }
}
