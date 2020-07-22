package com.demo.httpclient.chapter1;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wude
 * @Create 2019-08-29 15:20
 */
public class HttpClientTest {

    public static void main(String[] args) throws URISyntaxException {
        // 一般测试
//        httpClientCommonTest();
        // 构建httpGet URI
        httpClientRequestTest();

    }

    /**
     * HttpClient一般测试
     */
    public static void httpClientCommonTest() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://www.baidu.com/");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
            System.out.println("### HttpStatus = " + response.getStatusLine().getStatusCode());
            System.out.println("### ContentType = " + response.getEntity().getContentType());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("### HttpClient执行异常!");
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void httpClientRequestTest() throws URISyntaxException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // uri
        // HttpGet httpget = new HttpGet("https://www.baidu.com/s?wd=叮咚买菜");
        // 使用URIBuilder方式
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("www.baidu.com")
                .setPath("/s")
                .setParameter("wd", "叮咚买菜")
                .build();
        HttpGet httpget = new HttpGet(uri);
        System.out.println(httpget.getURI());
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
            System.out.println("### HttpStatus = " + response.getStatusLine().getStatusCode());
            System.out.println("### ContentType = " + response.getEntity().getContentType());
            System.err.println(EntityUtils.toString(response.getEntity()));

            // 使用BufferedHttpEntity可以将entity缓存起来，便于多次消费内容
            HttpEntity entity = new BufferedHttpEntity(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("### HttpClient执行异常!");
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void httpEntityiInRequest() {

        // 文件Entity
        File file = new File("somefile.txt");
        FileEntity entity = new FileEntity(file,
                ContentType.create("text/plain", "UTF-8"));
        HttpPost httppost = new HttpPost("http://localhost/action.do");
        httppost.setEntity(entity);

        // 表单Entity
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("param1", "value1"));
        formParams.add(new BasicNameValuePair("param2", "value2"));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        HttpPost formHttppost = new HttpPost("http://localhost/handler.do");
        formHttppost.setEntity(entity);

        // StringEntity
        StringEntity stringEntity = new StringEntity("important message",
                ContentType.create("text/plain", "UTF-8"));

    }

    public void keepAlive() {
        ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(
                    HttpResponse response,
                    HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1) {
                    // Keep connections alive 5 seconds if a keep-alive value
                    // has not be explicitly set by the server
                    keepAlive = 5000;
                }
                return keepAlive;
            }

        };
        CloseableHttpClient httpclient = HttpClients.custom()
                .setKeepAliveStrategy(keepAliveStrat)
                .build();
    }
}