package test.netty.io;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author dewu.de
 * @date 2023-03-29 5:30 下午
 */
public class ClientStartup {
    static String serverName = "localhost";
    static int oioPort = 10001;
    static int nioPort = 10002;

    public static void main(String[] args) throws Exception {
//        oioClient();
        nioClient();
    }


    public static void oioClient() {
        try {
            Socket client = new Socket(serverName, oioPort);
            System.out.println("OIO Server address: " + client.getRemoteSocketAddress());

            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF("Hi OIO Server, I am a client from: " + client.getLocalSocketAddress());

            DataInputStream dis = new DataInputStream(client.getInputStream());
            System.out.println("Received OIO Server reply: " + dis.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void nioClient() {
        try {
            // 打开一个SocketChannel
            SocketChannel socketChannel = SocketChannel.open();
            // 设置为非阻塞模式
            socketChannel.configureBlocking(false);
            // 连接到远程服务器
            socketChannel.connect(new InetSocketAddress(serverName, nioPort));

            // 等待连接完成
            while (!socketChannel.finishConnect()) {
                Thread.sleep(100);
            }
            System.out.println("Connected to server ok!");

            // 发送数据
            String message = "Hi NIO Server, I am a client";
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }
            // 关闭SocketChannel
            socketChannel.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
