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
        Selector selector = null;
        try {
            // 打开Selector来处理Channel
            selector = Selector.open();
            // 将ServerSocket注册到Selector以接受链接
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                // 返回的值大于零，表示有连接事件发生，可以通过 SelectedKeys 获取事件的类型和对应的 Channel
                int keysNum = selector.select(TIMEOUT);
                if (keysNum == 0) {
                    System.out.println("等待连接超时。");
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        handleAccept(key);
                    }
                    if (key.isReadable()) {
                        handleRead(key);
                    }
                    if (key.isWritable() && key.isValid()) {
                        handleWrite(key);
                    }
                    if (key.isConnectable()) {
                        System.out.println("isConnectable = true");
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (selector != null) {
                    selector.close();
                }
                serverChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
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
//        if (bytesRead == -1) {
//            socketChannel.close();
//        }
    }

    private static void handleWrite(SelectionKey key) throws IOException {
        ByteBuffer buf = (ByteBuffer) key.attachment();
        buf.flip();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        while (buf.hasRemaining()) {
            socketChannel.write(buf);
        }
        buf.compact();
    }

}