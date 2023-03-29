package test.netty.io;

import java.io.IOException;

/**
 * @author dewu.de
 * @date 2023-03-29 5:42 下午
 */
public class ServerStartup {

    static int oioPort = 10001;
    static int nioPort = 10002;

    public static void main(String[] args) throws IOException {

//        oioServer();
        nioServer();
    }

    public static void oioServer() throws IOException {
        new PlainOioServer().serve(oioPort);
    }

    public static void nioServer() throws IOException {
        new PlainNioServer().serve(nioPort);
    }

}
