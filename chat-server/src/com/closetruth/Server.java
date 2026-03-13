package com.closetruth;


import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static final Map<Socket, String> onlineSockets = new HashMap<>();
    public static void main(String[] args) {
        System.out.println("服务器启动");
        try {
            ServerSocket serverSocket = new ServerSocket(Constant.SERVER_PORT);
            //主线程负责接受客户端连接
            while (true) {
                System.out.println("等待客户端连接...");
                System.out.println("**************************");
                Socket socket = serverSocket.accept();
                new ServerReaderThread(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
