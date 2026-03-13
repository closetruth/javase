package com.closetruth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerReaderThread extends Thread {
    private Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while (true) {
                int type = dis.readInt();
                switch (type) {
                    //登录
                    case Constant.TYPE_LOGIN:
                        String username = dis.readUTF();
                        Server.onlineSockets.put(socket, username);
                        System.out.println(username + "上线了");
                        updateClientOnlineUsers();
                        break;
                    //群聊
                    case Constant.TYPE_CHAT_ALL:
                        String message = dis.readUTF();
                        sendMessageToAll(message);
                        System.out.println(message);
                        break;
                    //私聊
                    case Constant.TYPE_PRIVATE_CHAT:
                        String privateMessage = dis.readUTF();
                        System.out.println(privateMessage);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("客户端断开连接" + socket.getInetAddress());
            Server.onlineSockets.remove(socket);
            updateClientOnlineUsers();
        }
    }

    private void sendMessageToAll(String message) throws IOException {
        StringBuilder sb = new StringBuilder();
        String name = Server.onlineSockets.get(socket);
        LocalDateTime now = LocalDateTime.now();
        StringBuilder msgResult = sb.append(name).append(": ").append(now.toLocalTime()).append(" ").append(message);
        for (Socket clientSocket : Server.onlineSockets.keySet()) {
            try {
                //为每个客户端创建独立的输出流
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                //发送给所有在线用户
                dos.writeInt(Constant.TYPE_CHAT_ALL);
                dos.writeUTF(msgResult.toString());
                dos.flush();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateClientOnlineUsers() {
        //要有消息类型 是更新在线人数的类型
        System.out.println("log:更新在线人数");
        for (Socket socket : Server.onlineSockets.keySet()) {
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(Constant.TYPE_UPDATE_ONLINE_USERS);
                dos.writeInt(Server.onlineSockets.size());
                for (String username : Server.onlineSockets.values()) {
                    dos.writeUTF(username);
                }
                dos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //显示在线用户
        System.out.println("当前在线用户：");
        for (String username : Server.onlineSockets.values()) {
            System.out.println(username);
        }
        //显示在线人数
        System.out.println("当前在线人数：" + Server.onlineSockets.size());
    }

}
