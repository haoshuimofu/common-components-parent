package com.demo.components.httpclient;

import com.demo.components.httpclient.exception.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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

    public static String get(String url, int connectionTimeout, int socketTimeout) throws Exception {
        return get(url, null, connectionTimeout, socketTimeout);
    }

    public static String get(String url, Map<String, String> headers, int connectTimeout, int socketTimeout) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().build();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout < 0 ? -1 : connectTimeout)
                .setSocketTimeout(socketTimeout < 0 ? -1 : socketTimeout)
                .build();
        httpGet.setConfig(requestConfig);
        HttpHelper.addHeaders(httpGet, headers);
        CloseableHttpResponse response = null;
        boolean responseClose = false;
        try {
            response = httpClient.execute(httpGet);
            if (response == null) {
                throw new HttpException("Http response is null");
            }
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() != HttpStatus.OK.getValue()) {
                LOGGER.error("### http get request error! httpStatus=[{}], reasonPhrase=[{}].",
                        statusLine.getStatusCode(), statusLine.getReasonPhrase());
                throw new HttpException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.getValue()) {
                String responseStr = EntityUtils.toString(response.getEntity());
                responseClose = true;
                return responseStr;
            }
        } finally {
            if (response != null && !responseClose) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String post(String url, Map<String, String> headers, Map<String, String> params, int connectTimeout, int socketTimeout, String charset) throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom().build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout < 0 ? -1 : connectTimeout)
                .setSocketTimeout(socketTimeout < 0 ? -1 : socketTimeout)
                .build();
        httpPost.setConfig(requestConfig);
        HttpHelper.addHeaders(httpPost, headers);
        if (params != null && params.size() > 0) {
            List<NameValuePair> nameValuePairs = new ArrayList<>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            if (StringUtils.isNotBlank(charset)) {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, charset));
            } else {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
        }
        CloseableHttpResponse response = null;
        boolean responseClose = false;
        try {
            response = httpclient.execute(httpPost);
            if (response == null) {
                throw new HttpException("Http response is null");
            }
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() != HttpStatus.OK.getValue()) {
                LOGGER.error("### http post request error! httpStatus=[{}], reasonPhrase=[{}].",
                        statusLine.getStatusCode(), statusLine.getReasonPhrase());
                throw new HttpException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.getValue()) {
                String responseStr = EntityUtils.toString(response.getEntity());
                responseClose = true;
                return responseStr;
            }
        } finally {
            if (response != null && !responseClose) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String post(String url, Map<String, String> headers, String params, int connectTimeout, int socketTimeout, String charset) throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom().build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout < 0 ? -1 : connectTimeout)
                .setSocketTimeout(socketTimeout < 0 ? -1 : socketTimeout)
                .build();
        httpPost.setConfig(requestConfig);
        HttpHelper.addHeaders(httpPost, headers);
        if (params != null && params.length() > 0) {
            httpPost.setEntity((charset == null || charset.trim().isEmpty()) ?
                    new StringEntity(params) : new StringEntity(params, charset));
        }
        CloseableHttpResponse response = null;
        boolean responseClose = false;
        try {
            response = httpclient.execute(httpPost);
            if (response == null) {
                throw new HttpException("Http response is null");
            }
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() != HttpStatus.OK.getValue()) {
                LOGGER.error("### http post request error! httpStatus=[{}], reasonPhrase=[{}].",
                        statusLine.getStatusCode(), statusLine.getReasonPhrase());
                throw new HttpException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.getValue()) {
                String responseStr = EntityUtils.toString(response.getEntity());
                responseClose = true;
                return responseStr;
            }
        } finally {
            if (response != null && !responseClose) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}