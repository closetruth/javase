
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class test5back {
    public static final Map<String, Socket> onlineSockets = new HashMap();
    public static void main(String[] args) {
        System.out.println("服务器启动");
        File userFile = new File("user.txt");
        System.out.println("用户文件：" + userFile.getAbsolutePath());
        if (!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            ServerSocket serverSocket = new ServerSocket(test5constant.SERVER_PORT);
            while (true) {
                System.out.println("等待客户端连接...");
                System.out.println("**************************");
                Socket socket = serverSocket.accept();
                new test5backThread(socket).start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
