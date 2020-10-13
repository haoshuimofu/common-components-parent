package com.demo.components.httpclient;

import com.alibaba.fastjson.JSON;
import com.demo.components.httpclient.exception.HttpException;
import com.demo.components.httpclient.exception.HttpStatusException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author wude
 * @date 2020/8/26 11:03
 */
public class HttpClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);


    public static String get(String url) throws Exception {
        return get(url, null, 0, 0);
    }

    public static String get(String url, Map<String, String> headers) throws Exception {
        return get(url, headers, 0, 0);
    }

    public static String get(String url, int connectTimeout, int socketTimeout) throws Exception {
        return get(url, null, connectTimeout, socketTimeout);
    }

    public static String get(String url, Map<String, String> headers, int connectTimeout, int socketTimeout) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().build();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectTimeout(connectTimeout > 0 ? connectTimeout : -1)
                .setSocketTimeout(socketTimeout > 0 ? socketTimeout : -1);
        httpGet.setConfig(requestConfigBuilder.build());
        HttpHelper.addHeaders(httpGet, headers);
        return HttpClientUtils.executeHttpRequest(httpClient, httpGet);
    }

    public static String post(String url, Map<String, String> headers, Object params,
                              int connectTimeout, int socketTimeout, String charset) throws Exception {
        return post(url, headers, params == null ? null : JSON.toJSONString(params), connectTimeout, socketTimeout, charset);
    }

    public static String post(String url, Map<String, String> headers, String params,
                              int connectTimeout, int socketTimeout, String charset) throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom().build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectTimeout(connectTimeout > 0 ? connectTimeout : -1)
                .setSocketTimeout(socketTimeout > 0 ? socketTimeout : -1);
        httpPost.setConfig(requestConfigBuilder.build());
        HttpHelper.addHeaders(httpPost, headers);
        if (params != null) {
            httpPost.setEntity((charset == null || charset.trim().isEmpty()) ?
                    new StringEntity(params) : new StringEntity(params, charset));
        }
        return HttpClientUtils.executeHttpRequest(httpclient, httpPost);
    }

    public static String postForm(String url, Map<String, String> headers, Map<String, Object> params,
                                  int connectTimeout, int socketTimeout, String charset) throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom().build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectTimeout(connectTimeout > 0 ? connectTimeout : -1)
                .setSocketTimeout(socketTimeout > 0 ? socketTimeout : -1);
        httpPost.setConfig(requestConfigBuilder.build());
        HttpHelper.addHeaders(httpPost, headers);
        if (params != null && params.size() > 0) {
            List<NameValuePair> nameValuePairs = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(),
                        Optional.ofNullable(entry.getValue()).orElse("").toString()));
            }
            if (StringUtils.isNotBlank(charset)) {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, charset));
            } else {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
        }
        return HttpClientUtils.executeHttpRequest(httpclient, httpPost);
    }

    public static String executeHttpRequest(CloseableHttpClient httpClient, HttpUriRequest httpRequest) throws Exception {
        CloseableHttpResponse response = null;
        boolean responseClose = false;
        try {
            response = httpClient.execute(httpRequest);
            if (response == null) {
                throw new HttpException("Http response is null");
            }
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() != HttpStatus.OK.getValue()) {
                LOGGER.error("### Http {} request error! httpStatus=[{}], reasonPhrase=[{}].",
                        httpRequest.getMethod(), statusLine.getStatusCode(), statusLine.getReasonPhrase());
                throw new HttpStatusException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            String responseStr = EntityUtils.toString(response.getEntity());
            responseClose = true;
            return responseStr;
        } finally {
            if (response != null && !responseClose) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 使用连接池, 连接交给ConnectionManager管理, 如果httpCleint.close会直接关闭连接, 重复使用时会导致报错
            // Connection pool shut down
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}