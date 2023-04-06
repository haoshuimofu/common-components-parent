package test.netty.io;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * @author dewu.de
 * @date 2023-04-04 7:13 下午
 */
public class NettyNioServer {

    public void server(int port) throws Exception {

        // 为非阻塞模式NioEventLoopGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建Server-Bootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // 使用OioEventLoopGroup以允许阻塞IO
                    .channel(NioServerSocketChannel.class)
                    // 指定 ChannelInitializer，对于每个已接受的连接都调用它
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 添加一个 ChannelInboundHandleAdapter 以拦截和处理事件
                            ch.pipeline().addLast(
                                    new ChannelInboundHandlerAdapter() {

                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            ByteBuf activeBuf = Unpooled.copiedBuffer("Server active!\r\n", CharsetUtil.UTF_8);
                                            ctx.writeAndFlush(activeBuf.duplicate());
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            ByteBuf in = (ByteBuf) msg;
                                            // 将消息记录到控制台
                                            System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
                                            // 将接收到的消息写给发送者, 而不冲刷出站消息
                                            ByteBuf replyBuf = Unpooled.copiedBuffer("Server reply!\r\n", CharsetUtil.UTF_8);
                                            ctx.write(replyBuf);
                                        }

                                        @Override
                                        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                                                    .addListener(ChannelFutureListener.CLOSE);
                                        }
                                    });
                        }
                    });
            // 绑定服务器以接受连接
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }

}
