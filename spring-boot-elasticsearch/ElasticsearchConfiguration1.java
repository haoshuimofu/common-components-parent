//package com.demo.components.elasticsearch.config.replica;
//
//import org.apache.http.HttpHost;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.concurrent.FutureCallback;
//import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
//import org.apache.http.impl.nio.client.HttpAsyncClients;
//import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
//import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
//import org.apache.http.impl.nio.reactor.IOReactorConfig;
//import org.apache.http.nio.reactor.ConnectingIOReactor;
//import org.apache.http.nio.reactor.IOReactorException;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.beans.factory.InitializingBean;
//
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//
///**
// * Elasticsearch配置
// *
// * @Author wude
// * @Create 2019-07-20 15:24
// */
////@Configuration
////@EnableConfigurationProperties(value = {ElasticsearchProperties.class})
//public class ElasticsearchConfiguration1 implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {
//
//    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfiguration1.class);
//
//    private RestHighLevelClient client;
//    //    @Autowired
//    private ElasticsearchRestProperties properties;
//
//    public static void main(String[] args) throws ExecutionException, InterruptedException, IOReactorException {
//        //具体参数含义下文会讲 //apache提供了ioReactor的参数配置，这里我们配置IO 线程为1 
//        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(1).build();
//        //根据这个配置创建一个ioReactor 
//        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
//        //asyncHttpClient使用PoolingNHttpClientConnectionManager管理我们客户端连接 
//        PoolingNHttpClientConnectionManager conManager = new PoolingNHttpClientConnectionManager(ioReactor);
//        //设置总共的连接的最大数量 
//        conManager.setMaxTotal(100);
//        //设置每个路由的连接的最大数量 
//        conManager.setDefaultMaxPerRoute(100);
//        //创建一个Client 
//        CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setConnectionManager(conManager).build();
//        // Start the client 
//        httpclient.start();
//        // Execute request 
//        final HttpGet request1 = new HttpGet("http://www.apache.org/");
//        Future<HttpResponse> future = httpclient.execute(request1, null);
//        // and wait until a response is received 
//        HttpResponse response1 = future.get();
//        System.out.println(request1.getRequestLine() + "->" + response1.getStatusLine());
//        // One most likely would want to use a callback for operation result 
//        final HttpGet request2 = new HttpGet("http://www.baidu.com/");
//        httpclient.execute(request2, new FutureCallback<HttpResponse>() {
//            //Complete成功后会回调这个方法 
//            public void completed(final HttpResponse response2) {
//                System.out.println(request2.getRequestLine() + "->" + response2.getStatusLine());
//            }
//
//            public void failed(final Exception ex) {
//                System.out.println(request2.getRequestLine() + "->" + ex);
//            }
//
//            public void cancelled() {
//                System.out.println(request2.getRequestLine() + "cancelled");
//            }
//        });
//    }
//
//    @Override
//    public RestHighLevelClient getObject() throws Exception {
//        return client;
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return RestHighLevelClient.class;
//    }
//
//    @Override
//    public boolean isSingleton() {
//        return true;
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        buildClient();
//    }
//
//    private void buildClient() {
//        String schema = properties.getSchema();
//        String[] servers = properties.getServers().split(";");
//        HttpHost[] httpHosts = new HttpHost[servers.length];
//        for (int i = 0; i < servers.length; i++) {
//            String server = servers[i];
//            String[] hostAndPort = server.split(":");
//            httpHosts[i] = new HttpHost(hostAndPort[0], Integer.parseInt(hostAndPort[1]), schema);
//        }
//        /**
//         * @See https://www.cnblogs.com/number7/p/9514040.html
//         * 1. connectTimeOut：指建立连接的超时时间，比较容易理解
//         * 2. connectionRequestTimeOut：指从连接池获取到连接的超时时间，如果是非连接池的话，该参数暂时没有发现有什么用处
//         * 3. socketTimeOut：指客户端和服务进行数据交互的时间，是指两者之间如果两个数据包之间的时间大于该时间则认为超时，而不是整个交互的整体时间，
//         * 比如如果设置1秒超时，如果每隔0.8秒传输一次数据，传输10次，总共8秒，这样是不超时的。而如果任意两个数据包之间的时间超过了1秒，则超时。
//         */
//        RestClientBuilder builder = RestClient.builder(httpHosts);
//        builder.setRequestConfigCallback(requestConfigBuilder -> {
//            requestConfigBuilder.setConnectionRequestTimeout(3000);
//            return requestConfigBuilder;
//        });
//        builder.setHttpClientConfigCallback(httpClientBuilder -> {
//            IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
//                    .setConnectTimeout(5000)
//                    .setSoTimeout(3000)
//                    .build();
//            ConnectingIOReactor ioReactor;
//            try {
//                ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
//            } catch (IOReactorException e) {
//                throw new RuntimeException(e);
//            }
//            PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(ioReactor);
//            connectionManager.setMaxTotal(200);
//            httpClientBuilder.setConnectionManager(connectionManager);
//            return httpClientBuilder;
//        });
//        client = new RestHighLevelClient(RestClient.builder(httpHosts));
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        try {
//            if (client != null) {
//                client.close();
//            }
//        } catch (Exception e) {
//            logger.error("### Closing The {} Error!", RestHighLevelClient.class.getSimpleName());
//        }
//    }
//}
