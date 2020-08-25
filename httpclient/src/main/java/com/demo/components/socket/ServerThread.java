package com.demo.components.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @Author ddmc
 * @Create 2019-08-09 10:22
 */
public class ServerThread implements Runnable {
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
                if ("over".equalsIgnoreCase(msg)) {
                    flag = false;
                    buf.close();
                    out.close();
                    client.shutdownInput();
                    client.shutdownOutput();
                    client.close();
                }
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