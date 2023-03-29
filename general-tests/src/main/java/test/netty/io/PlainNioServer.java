package test.netty.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Java NIO（New IO）是 Java 中的一种 I/O（输入/输出）操作方式，它可以提高系统的效率和响应速度，特别是在处理大量的并发连接时，比传统的 Java I/O 更具优势。
 * <p>
 * 要实现一个 Java NIO服务器，可按照以下步骤：
 * <p>
 * 创建 ServerSocketChannel 实例并绑定端口号。
 * 创建 Selector 实例，并将 ServerSocketChannel 注册到 Selector 上，以便 Selector 可以监控 ServerSocketChannel 上的连接事件。
 * 阻塞 Selector.select() 方法，等待连接事件的发生。
 * 如果 Selector.select() 返回的值大于零，表示有连接事件发生，可以通过 SelectedKeys 获取事件的类型和对应的 Channel。
 * 如果事件是 OP_ACCEPT，则表示有新的连接请求，需要创建 SocketChannel 实例，并将其注册到 Selector 上，以便 Selector 可以监控该连接的读写事件。
 * 如果事件是 OP_READ，则表示有数据可读，可以通过 SocketChannel读取数据，并进行相应的业务处理。
 * 如果事件是 OP_WRITE，则表示可以向客户端写入数据，可以通过 SocketChannel 将数据写入连接中。
 * 下面是一个简单的 Java NIO服务器示例代码：
 *
 * @author dewu.de
 * @date 2023-03-29 4:23 下午
 */
public class PlainNioServer {

    private static final int BUF_SIZE = 1024;
    private static final int TIMEOUT = 3000;

    public void serve(int port) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        // 打开Selector来处理Channel
        Selector selector = Selector.open();
        // 将ServerSocket注册到Selector以接受链接
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {
            // 返回的值大于零，表示有连接事件发生，可以通过 SelectedKeys 获取事件的类型和对应的 Channel
            int keysNum = selector.select(TIMEOUT);
            if (keysNum == 0) {
                System.out.println("等待连接超时。");
                continue;
            }
        }




        final ByteBuffer msg = ByteBuffer.wrap("Hi! \r\n".getBytes());
        for (; ; ) {
            try {
                // 等待需要处理的新事件, 阻塞 将一致持续到下一个传入事件
                selector.select();
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
            // 获取所有接收事件的Selection-Key实例
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    // 检查事件是否是一个新的已经就绪可以被接受的连接
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        // 接受客户端, 并将它注册到选择器
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                        System.out.println("NIO Server accepted connection from " + client);
                    }
                    // 检查套接字是否已经准备好写数据
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        // 将数据写到已经连接的客户端
                        while (client.write(buffer) == 0) {
                            break;
                        }
                        // 关闭连接
                        client.close();
                    }
                } catch (IOException ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException cex) {
                        // ignore on close
                    }

                }
            }
        }
    }


    private static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
    }

    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment();
        long bytesRead = socketChannel.read(buf);
        while (bytesRead > 0) {
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead = socketChannel.read(buf);
        }
        if (bytesRead == -1) {
            socketChannel.close();
        }


    }
