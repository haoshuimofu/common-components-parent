package com.demo.components.httpclient.pooling.config;

import com.alibaba.fastjson.JSON;
import com.demo.components.httpclient.HttpClientUtils;
import com.demo.components.httpclient.HttpHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wude
 * @date 2020/8/26 11:03
 */
public class HttpClientPoolingUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientPoolingUtils.class);

    private PoolingHttpClientConnectionManager connectionManager;
    private HttpClientPoolingProperties properties;

    public HttpClientPoolingUtils(PoolingHttpClientConnectionManager connectionManager,
                                  HttpClientPoolingProperties properties) {
        this.connectionManager = connectionManager;
        this.properties = properties;
    }

    public String post(String url, Map<String, String> headers, String params,
                       int socketTimeout, String charset) throws Exception {
        CloseableHttpClient httpclient = getHttpClient();
        RequestConfig requestConfig = requestConfig(socketTimeout);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        HttpHelper.addHeaders(httpPost, headers);
        if (params != null && params.length() > 0) {
            httpPost.setEntity((charset == null || charset.trim().isEmpty()) ?
                    new StringEntity(params) : new StringEntity(params, charset));
        }
        return HttpClientUtils.executeHttpRequest(httpclient, httpPost);
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
    public String postForm(String url, Map<String, String> headers, Map<String, String> params,
                           int connectTimeout, int socketTimeout, String charset) throws Exception {
        CloseableHttpClient httpclient = getHttpClient();
        RequestConfig requestConfig = requestConfig(socketTimeout);
        HttpPost httpPost = new HttpPost(url);
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
    public String post(String url, Map<String, String> headers, Object params,
                       int connectTimeout, int socketTimeout, String charset) throws Exception {
        return post(url, headers, params == null ? null : JSON.toJSONString(params), connectTimeout, socketTimeout, charset);
    }

    public String get(String url) throws Exception {
        return get(url, null, 0);
    }

    public String get(String url, Map<String, String> headers) throws Exception {
        return get(url, headers, 0);
    }

    public String get(String url, int socketTimeout) throws Exception {
        return get(url, null, socketTimeout);
    }

    /**
     * GET请求
     *
     * @param url           请求URL
     * @param headers       Headers
     * @param socketTimeout 数据读取阻塞超时时间
     * @return
     * @throws Exception
     */
    public String get(String url, Map<String, String> headers, int socketTimeout) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        RequestConfig requestConfig = requestConfig(socketTimeout);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        HttpHelper.addHeaders(httpGet, headers);
        return HttpClientUtils.executeHttpRequest(httpClient, httpGet);
    }

    public CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();
    }

    public RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(properties.getConnectionRequestTimeout())
                .setConnectTimeout(properties.getConnectTimeout())
                .setSocketTimeout(properties.getSocketTimeout())
                .build();

    }

    public RequestConfig requestConfig(int socketTimeout) {
        if (socketTimeout <= 0) {
            socketTimeout = properties.getSocketTimeout();
        }
        return RequestConfig.custom()
                .setConnectionRequestTimeout(properties.getConnectionRequestTimeout())
                .setConnectTimeout(properties.getConnectTimeout())
                .setSocketTimeout(socketTimeout)
                .build();

    }
}