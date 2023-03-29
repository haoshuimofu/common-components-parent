package test.netty.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author dewu.de
 * @date 2023-03-29 5:07 下午
 */
public class PlainOioServer {

    public void serve(int port) throws IOException {
        final ServerSocket socket = new ServerSocket(port);
        try {
            for (; ; ) {
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Server handler thread run...");
                        InputStream input;
                        OutputStream out;
                        try {
                            input = clientSocket.getInputStream();


                            DataInputStream dataInput = new DataInputStream(input);
                            String message = dataInput.readUTF();
                            System.out.println("OIO Server received: " + message);

                            out = clientSocket.getOutputStream();
                            DataOutputStream dataOutput = new DataOutputStream(out);
                            dataOutput.writeUTF("OIO Server has received: " + message + "\r\n");
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                                // ignore on close
                                ex.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
