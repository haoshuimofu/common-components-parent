package com.demo.components.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author ddmc
 * @Create 2019-08-09 10:24
 */
public class Server2 {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(Constants.port);
        Socket client;
        boolean accept = true;
        while (accept) {
            // 等待客户端的连接，如果没有获取连接阻塞
            client = server.accept();
            String connect = client.getLocalAddress().getHostName() + ":" + client.getPort();
            System.out.println("客户端连接成功: " + connect);
            // 为每个客户端连接开启一个线程
            new Thread(new ServerThread2(String.valueOf(System.currentTimeMillis()), client)).start();
        }
        server.close();
    }

    static class ServerThread2 implements Runnable {

        private String timeMillis;
        private Socket client;

        public ServerThread2(String timeMillis, Socket client) {
            this.timeMillis = timeMillis;
            this.client = client;
        }

        @Override
        public void run() {

            StringBuffer sb = new StringBuffer();
            byte[] bytes = new byte[2048];
            int len;
            try {
                InputStream is = client.getInputStream();
                OutputStream os = client.getOutputStream();

                while ((len = is.read(bytes)) != -1) { // 对方shutdownOutput之后 -1 才成立
                    sb.append(new String(bytes, 0, len));
                }
                System.err.println(sb.toString());
                os.write(("You said: " + sb.toString()).getBytes());
                os.flush();
//                client.shutdownOutput();
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public String getTimeMillis() {
            return timeMillis;
        }

        public void setTimeMillis(String timeMillis) {
            this.timeMillis = timeMillis;
        }

        public Socket getClient() {
            return client;
        }

        public void setClient(Socket client) {
            this.client = client;
        }
    }

}