package com.demo.httpclient.chapter5;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 从V4.2之后提供org.apache.httpcomponents.fluent-hc.xxx.jar，支持fluent风格快速构建httpclient
 *
 * @Author wude
 * @Create 2019-08-30 13:49
 */
public class HttpFluentTest {

    public static void main(String[] args) throws IOException {

        String server = "http://www.baidu.com/";
        // Execute a GET with timeout settings and return response content as String.
        Request.Get(server)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .execute().returnContent().asString();

        // Execute a POST with the 'expect-continue' handshake, using HTTP/1.1,
        // containing a request body as String and return response content as byte array.
//        Request.Post("http://somehost/do-stuff")
//                .useExpectContinue()
//                .version(HttpVersion.HTTP_1_1)
//                .bodyString("Important stuff", ContentType.DEFAULT_TEXT)
//                .execute().returnContent().asBytes();

        // Execute a POST with a custom header through the proxy containing a request body
        // as an HTML form and save the result to the file
//        Request.Post("http://somehost/some-form")
//                .addHeader("X-Custom-header", "stuff")
//                .viaProxy(new HttpHost("myproxy", 8080))
//                .bodyForm(Form.form().add("username", "vip").add("password", "secret").build())
//                .execute().saveContent(new File("result.dump"));

        // todo 测试
        executorTest();

        test();

    }

    public static void executorTest() throws IOException {
        Executor executor = Executor.newInstance()
                .auth(new HttpHost("somehost"), "username", "password")
                .auth(new HttpHost("myproxy", 8080), "username", "password")
                .authPreemptive(new HttpHost("myproxy", 8080));
        executor = Executor.newInstance();

        String responseString = executor.execute(Request.Get("http://www.baidu.com/"))
                .returnContent().asString();
        System.out.println(responseString);
    }

    public static void test() throws IOException {
        String result = Request.Get("http://www.baidu.com/")
                .execute().handleResponse(new ResponseHandler<String>() {

                    public String handleResponse(final HttpResponse response) throws IOException {
                        StatusLine statusLine = response.getStatusLine();
                        HttpEntity entity = response.getEntity();
                        if (statusLine.getStatusCode() >= 300) {
                            throw new HttpResponseException(
                                    statusLine.getStatusCode(),
                                    statusLine.getReasonPhrase());
                        }
                        if (entity == null) {
                            throw new ClientProtocolException("Response contains no content");
                        }
                        return EntityUtils.toString(entity);
                    }
                });
        System.err.println("请求百度首页字符串内容: " + result);
    }
}