package com.closetruth.ui;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//import static sun.net.www.protocol.http.AuthCacheValue.Type.Server;

public class ClientReaderThread extends Thread {
    private Socket socket;
    private DataInputStream dis;
    private ChatUI chatUI; // 添加对ChatUI的引用
    
    public ClientReaderThread(Socket socket, ChatUI chatUI) { // 修改构造函数
        this.socket = socket;
        this.chatUI = chatUI;
    }
    @Override
    public void run() {
        try {
            dis = new DataInputStream(socket.getInputStream());
            while (true) {
                int type = dis.readInt();
                switch (type) {
                    //更新在线人数
                    case Constant.TYPE_UPDATE_ONLINE_USERS:
                        updateClientOnlineUsers();
                        break;
                    //群聊
                    case Constant.TYPE_CHAT_ALL:
                        String message = dis.readUTF();
                        //sendMessageToAll(message);
                        Platform.runLater(() -> {
                            chatUI.appendMessage(message);
                        });
                        break;
                    //私聊
                    case Constant.TYPE_PRIVATE_CHAT:
                        String privateMessage = dis.readUTF();
                        Platform.runLater(() -> {
                            //chatUI.appendMessage(privateMessage);
                        });
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToAll(String message) throws IOException {
/*
        String name = Server.onlineSockets.get(socket);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        LocalDateTime now = LocalDateTime.now();
        StringBuilder msgResult = sb.append(name).append(": ").append(now.toLocalTime()).append(" ").append(message);
         for (Socket socket : Server.onlineSockets.keySet()) {
            try {
                //发送给所有在线用户
                dos.writeInt(Constant.TYPE_CHAT_ALL);
                dos.writeUTF(msgResult.toString());
                dos.flush();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
*/
    }

    private void updateClientOnlineUsers() throws IOException {
        //要有消息类型 是更新在线人数的类型
        int count = dis.readInt();
        List<String> onlineUsers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String username = dis.readUTF();
            onlineUsers.add(username);
        }
        
        // 在JavaFX应用线程中更新UI
        Platform.runLater(() -> {
            chatUI.updateOnlineUsers(onlineUsers);
        });
    }

}