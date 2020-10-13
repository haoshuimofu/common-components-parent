package com.demo.components.httpclient;

import com.alibaba.fastjson.JSON;
import com.demo.components.httpclient.pooling.config.HttpClientPoolingProperties;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.util.Map;

/**
 * HTTP辅助工具类
 *
 * @author wude
 * @date 2020/8/27 11:18
 */
public class HttpHelper {

    /**
     * 为HTTP Method实例设置Header
     *
     * @param httpRequestBase
     * @param headers
     */
    public static void addHeaders(HttpRequestBase httpRequestBase, Map<String, String> headers) {
        if (httpRequestBase != null && headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                httpRequestBase.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
    }

    /**
     * 初始化PoolingHttpClientConnectionManager实例
     *
     * @param properties
     * @return
     */
    public static PoolingHttpClientConnectionManager connectionManager(HttpClientPoolingProperties properties) {
        // SSL context for secure connections can be created either based on
        // system or application specific properties.
        SSLContext sslcontext = SSLContexts.createSystemDefault();

        // Create a registry of custom connection socket factories for supported
        // protocol schemes.
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();

        // Create a connection manager with custom configuration.
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connManager.setDefaultMaxPerRoute(properties.getMaxPerRoute());
        connManager.setMaxTotal(properties.getMatTotal());
        System.err.println("###初始化连接管理器: " + JSON.toJSONString(properties, true));
        return connManager;
    }

    /**
     * Http连接池获取HttpClient实例
     *
     * @param connectionManager
     * @return
     */
    public static CloseableHttpClient get(PoolingHttpClientConnectionManager connectionManager) {
        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();
    }

    public static RequestConfig requestConfig(HttpClientPoolingProperties properties) {
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectionRequestTimeout(properties.getConnectionRequestTimeout())
                .setConnectTimeout(properties.getConnectTimeout());
        if (properties.getSocketTimeout() > 0) {
            requestConfigBuilder.setSocketTimeout(properties.getSocketTimeout());
        }
        return requestConfigBuilder.build();
    }

    /**
     * 构建Http请求设置
     *
     * @param properties          连接池配置
     * @param customSocketTimeout 自定义socketTimeout
     * @return
     */
    public static RequestConfig requestConfig(HttpClientPoolingProperties properties, int customSocketTimeout) {
        int timeout = customSocketTimeout > 0 ? customSocketTimeout : properties.getSocketTimeout();
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectionRequestTimeout(properties.getConnectionRequestTimeout())
                .setConnectTimeout(properties.getConnectTimeout());
        if (timeout > 0) {
            requestConfigBuilder.setSocketTimeout(timeout);
        }
        return requestConfigBuilder.build();
    }

}