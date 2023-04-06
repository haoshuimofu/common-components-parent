package test.netty.io;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

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
//        nioClient();
        nettyNioClient();
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

    public static void nettyNioClient() {
        final ByteBuf buf = Unpooled.copiedBuffer("Hi, I am netty nio-client!\r\n", CharsetUtil.UTF_8);
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {

                        @Override
                        protected void initChannel(io.netty.channel.socket.SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            System.out.println("Client send: " + buf.toString(CharsetUtil.UTF_8));
                                            ctx.writeAndFlush(buf.duplicate());
//                                                    .addListener(ChannelFutureListener.CLOSE);
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            ByteBuf in = (ByteBuf) msg;
                                            // 将消息记录到控制台
                                            System.out.println("Client received server reply: " + in.toString(CharsetUtil.UTF_8));
                                        }

                                        @Override
                                        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                                                    .addListener(ChannelFutureListener.CLOSE);
                                        }

                                    });
                        }
                    });
            ChannelFuture f = b.connect(serverName, nioPort).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
