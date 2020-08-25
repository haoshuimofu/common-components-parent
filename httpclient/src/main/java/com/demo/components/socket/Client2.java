package com.demo.components.socket;

import java.io.*;
import java.net.Socket;

/**
 * @Author wude
 * @Create 2019-08-09 10:23
 */
public class Client2 {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket(Constants.host, Constants.port);
        client.setSoTimeout(10000);
        // 获取键盘输入
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        // 获取Socket的输出流，用来发送数据到服务端
        OutputStream out = client.getOutputStream();
        // 获取Socket的输入流，用来接收从服务端发送过来的数据
        InputStream is = client.getInputStream();
        boolean flag = true;
        while (flag) {
            System.out.print("键入信息: ");
            String msg = input.readLine();
            System.err.println("client write data to server....");
            // 发送数据到服务端
            out.write(msg.getBytes());
            System.err.println(msg);
            out.flush();

            if (!client.isOutputShutdown()) {
                client.shutdownOutput(); // 己放shutdownOutput, 对方 read() 才能 = -1，
            }
            if ("over".equals(msg)) {
                flag = false;
            } else {
                byte[] bytes = new byte[2048];
                int len;
                StringBuffer sb = new StringBuffer();
                while ((len = is.read(bytes)) != -1) {
                    sb.append(new String(bytes, 0, len));
                }
                System.err.println(sb.toString());
            }
        }
        input.close();
        System.out.println("客户端连接终止!");
        // 如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
        client.close(); //只关闭socket，其关联的输入输出流也会被关闭
    }
}