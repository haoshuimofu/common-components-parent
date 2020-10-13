package com.demo.components.httpclient;

import com.alibaba.fastjson.JSON;
import com.demo.components.httpclient.exception.HttpException;
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

    /**
     * GET请求
     *
     * @param url            请求URL
     * @param headers        Headers
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  数据读取阻塞超时时间
     * @return
     * @throws Exception
     */
    public static String get(String url, Map<String, String> headers, int connectTimeout, int socketTimeout) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().build();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout < 0 ? -1 : connectTimeout)
                .setSocketTimeout(socketTimeout < 0 ? -1 : socketTimeout)
                .build();
        httpGet.setConfig(requestConfig);
        HttpHelper.addHeaders(httpGet, headers);
        return HttpClientUtils.executeHttpRequest(httpClient, httpGet);
    }

    /**
     * POST 提交表单
     *
     * @param url            请求URL
     * @param headers        Header
     * @param params         提交参数KV, V只接收String, 请先做好数据格式和类型转换
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  数据读取超时时间
     * @param charset        字符集
     * @return ResponseString
     * @throws Exception
     */
    public static String postForm(String url, Map<String, String> headers, Map<String, String> params,
                                  int connectTimeout, int socketTimeout, String charset) throws Exception {
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
        return HttpClientUtils.executeHttpRequest(httpclient, httpPost);
    }

    /**
     * POST请求
     *
     * @param url            请求URL
     * @param headers        Header
     * @param params         请求参数, 实际请求时会转换成String
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  数据读取超时时间
     * @param charset        字符集
     * @return Response String
     * @throws Exception
     */
    public static String post(String url, Map<String, String> headers, Object params,
                              int connectTimeout, int socketTimeout, String charset) throws Exception {
        return post(url, headers, params == null ? null : JSON.toJSONString(params), connectTimeout, socketTimeout, charset);
    }

    public static String post(String url, Map<String, String> headers, String params,
                              int connectTimeout, int socketTimeout, String charset) throws Exception {
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
        return HttpClientUtils.executeHttpRequest(httpclient, httpPost);
    }

    /**
     * 执行Http请求
     *
     * @param httpClient
     * @param httpRequest
     * @return
     * @throws Exception
     */
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
            // 使用连接池, 连接交给ConnectionManager管理, 如果httpCleint.close会直接关闭连接, 重复使用时会导致报错
            // Connection pool shut down
//            try {
//                httpClient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return null;
    }

}