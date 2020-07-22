package com.demo.httpclient.chapter2;

import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLInitializationException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * httpclient的两个重要的参数maxPerRoute及MaxTotal - u013905744的专栏 - CSDN博客
 * https://blog.csdn.net/u013905744/article/details/94714696
 *
 * @Author wude
 * @Create 2019-08-30 10:13
 */
public class HttpFluentUtil {
    final static PoolingHttpClientConnectionManager CONNMGR;
    final static HttpClient CLIENT;
    final static Executor EXECUTOR;
    private final static int MaxPerRoute = 2;
    private final static int MaxTotal = 5;

    static {
        LayeredConnectionSocketFactory ssl = null;
        try {
            ssl = SSLConnectionSocketFactory.getSystemSocketFactory();
        } catch (final SSLInitializationException ex) {
            final SSLContext sslcontext;
            try {
                sslcontext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
                sslcontext.init(null, null, null);
                ssl = new SSLConnectionSocketFactory(sslcontext);
            } catch (final SecurityException | NoSuchAlgorithmException | KeyManagementException ignore) {

            }
        }

        final Registry<ConnectionSocketFactory> sfr = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", ssl != null ? ssl : SSLConnectionSocketFactory.getSocketFactory()).build();

        CONNMGR = new PoolingHttpClientConnectionManager(sfr);
        CONNMGR.setDefaultMaxPerRoute(MaxPerRoute);
        CONNMGR.setMaxTotal(MaxTotal);
        CLIENT = HttpClientBuilder.create().setConnectionManager(CONNMGR).build();
        EXECUTOR = Executor.newInstance(CLIENT);

    }


    public static String Get(String uri, int connectTimeout, int socketTimeout) throws IOException {
        return EXECUTOR.execute(Request.Get(uri).connectTimeout(connectTimeout).socketTimeout(socketTimeout))
                .returnContent().asString();
    }

    public static String Post(String uri, StringEntity stringEntity, int connectTimeout, int socketTimeout)
            throws IOException {
        return EXECUTOR.execute(Request.Post(uri).socketTimeout(socketTimeout)
                .addHeader("Content-Type", "application/json").body(stringEntity)).returnContent().asString();
    }

    /**
     * main方法，测试PoolingHttpConnectionManager {@link TestOpenApiController#test()}
     *
     * @param args
     */
    public static void main(String[] args) {
        String url = "http://localhost:8080/openapi/test"; // 服务端sleep 5秒再返回
        for (int i = 0; i < 5; i++) { // MaxPerRoute若设置为2，则5线程分3组返回（2、2、1），共15秒
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = HttpFluentUtil.Get(url, 2000, 35000);
                        System.out.println(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * MaxPerRoute=5，MaxTotal=4
     * socketTimeout连接后socket通信时间35s，略大于服务端线程修编时间30s，以免请求超时释放
     * 由此可见：MaxTotal表明服务端同时只能建立接受多少个连接
     *
     * 2019-08-30 11:08:09.572 [http-nio-8080-exec-7] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! /openapi/test
     * 2019-08-30 11:08:09.572 [http-nio-8080-exec-9] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! /openapi/test
     * 2019-08-30 11:08:09.572 [http-nio-8080-exec-8] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! /openapi/test
     * 2019-08-30 11:08:09.572 [http-nio-8080-exec-6] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! /openapi/test
     * 2019-08-30 11:08:39.573 [http-nio-8080-exec-9] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! sleep 30s 结束!
     * 2019-08-30 11:08:39.573 [http-nio-8080-exec-8] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! sleep 30s 结束!
     * 2019-08-30 11:08:39.573 [http-nio-8080-exec-6] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! sleep 30s 结束!
     * 2019-08-30 11:08:39.573 [http-nio-8080-exec-7] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! sleep 30s 结束!
     * 2019-08-30 11:08:39.589 [http-nio-8080-exec-10] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! /openapi/test
     * 2019-08-30 11:09:09.589 [http-nio-8080-exec-10] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! sleep 30s 结束!
     */

    /**
     * MaxPerRoute=2，MaxTotal=5
     * socketTimeout连接后socket通信时间35s，略大于服务端线程修编时间30s，以免请求超时释放
     * 可以看到接收2个请求，2个请求，1个请求，即说明maxPerRoute意思是某一个服务每次能并行接收的请求数量
     *
     * 2019-08-30 11:19:30.384 [http-nio-8080-exec-5] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! /openapi/test
     * 2019-08-30 11:19:30.384 [http-nio-8080-exec-9] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! /openapi/test
     * 2019-08-30 11:20:00.385 [http-nio-8080-exec-5] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! sleep 30s 结束!
     * 2019-08-30 11:20:00.385 [http-nio-8080-exec-9] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! sleep 30s 结束!
     * 2019-08-30 11:20:00.400 [http-nio-8080-exec-7] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! /openapi/test
     * 2019-08-30 11:20:00.400 [http-nio-8080-exec-8] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! /openapi/test
     * 2019-08-30 11:20:30.400 [http-nio-8080-exec-8] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! sleep 30s 结束!
     * 2019-08-30 11:20:30.400 [http-nio-8080-exec-7] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! sleep 30s 结束!
     * 2019-08-30 11:20:30.402 [http-nio-8080-exec-6] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! /openapi/test
     * 2019-08-30 11:21:00.403 [http-nio-8080-exec-6] INFO  c.d.c.e.controller.TestOpenApiController - ### 接收到了请求! sleep 30s 结束!
     */
}