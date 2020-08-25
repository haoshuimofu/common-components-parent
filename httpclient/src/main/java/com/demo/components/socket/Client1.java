package com.demo.components.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * @Author wude
 * @Create 2019-08-09 10:23
 */
public class Client1 {

    public static void main(String[] args) throws IOException {
        //客户端请求与本机在20006端口建立TCP连接
        Socket client = new Socket(Constants.host, Constants.port);
        client.setSoTimeout(10000);
        // 获取键盘输入
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        // 获取Socket的输出流，用来发送数据到服务端
        PrintStream out = new PrintStream(client.getOutputStream());
        // 获取Socket的输入流，用来接收从服务端发送过来的数据
        BufferedReader buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean flag = true;
        while(flag){
            System.out.print("键入信息: ");
            String msg = input.readLine();
            // 发送数据到服务端
            out.println(msg);
            if("over".equals(msg)){
                flag = false;
//                client.shutdownOutput();
            }else{
                try{
                    // 从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
                    String replay = buf.readLine();
                    System.out.println("服务端回复: " + replay);
                    if (replay.contains("返回信息")) {
                        System.out.println("再次发送消息ok通知服务端关闭连接!");
                        out.println("ok");
                        out.flush();
                    } else if (replay.contains("我知道了")){
                        flag = false;
                    }
                }catch(SocketTimeoutException e){
                    System.out.println("Time out, No response");
                }
            }
        }
        input.close();
        System.out.println("客户端连接终止!");
        // 如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
        client.close(); //只关闭socket，其关联的输入输出流也会被关闭
    }
}