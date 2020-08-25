package com.demo.components.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
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

    static class ServerThread implements Runnable {
        private String name;
        private Socket client = null;

        public ServerThread(String name, Socket client) {
            this.name = name;
            this.client = client;
        }

        @Override
        public void run() {
            System.out.println(name + ": 服务端线程处理客户端连接!");
            try {
                //获取Socket的输出流，用来向客户端发送数据
                PrintStream out = new PrintStream(client.getOutputStream());
                //获取Socket的输入流，用来接收从客户端发送过来的数据
                BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
                boolean flag = true;
                while (flag) {
                    String msg = buf.readLine();
                    System.err.println("接收到来自客户端的消息: " + msg);
//                if ("over".equalsIgnoreCase(msg)) {
//                    flag = false;
//                    client.shutdownInput();
//                }
                    if ("ok".equalsIgnoreCase(msg)) {
                        out.println("我知道了");
                        System.out.println("回复我知道了");
                        out.flush();
                        flag = false;
                    } else {
                        out.println(String.format("%s: %s", name, msg));
                        System.out.println("消息原路返回");
                        out.flush();
                    }
                }
                System.out.println("服务端连接终止!");
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}