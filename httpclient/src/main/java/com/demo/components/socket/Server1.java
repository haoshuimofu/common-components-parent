package com.demo.components.socket;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author ddmc
 * @Create 2019-08-09 10:24
 */
public class Server1 {

    public static void main(String[] args) throws Exception {
        // 服务端在20006端口监听客户端请求的TCP连接
        ServerSocket server = new ServerSocket(Constants.port);
        Socket client;
        boolean accept = true;
        while (accept) {
            // 等待客户端的连接，如果没有获取连接阻塞
            client = server.accept();
            String connect = client.getLocalAddress().getHostName() + ":" + client.getPort();
            System.out.println("客户端连接成功: " + connect);
            //为每个客户端连接开启一个线程
            new Thread(new ServerThread(String.valueOf(System.currentTimeMillis()), client)).start();
        }
        server.close();
    }

}