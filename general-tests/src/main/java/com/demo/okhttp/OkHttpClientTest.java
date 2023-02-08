package com.demo.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wude
 * @date 2020/8/7 14:05
 */
public class OkHttpClientTest {

    public static void main(String[] args) throws Exception {
        // get from a url
        getAURL();
    }

    public static void getAURL() throws Exception {
        String url = "http://www.baidu.com";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.err.println(response.body().string());
        }
    }

}