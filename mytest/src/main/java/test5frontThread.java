import java.io.DataInputStream;
import java.net.Socket;

public class test5frontThread extends Thread{
    private Socket socket;
    private String name;

    public test5frontThread(String name, Socket socket) {
        this.socket = socket;
        this.name = name;
    }
    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while (true) {
                int type = dis.readInt();
                switch (type) {
                    case test5constant.TYPE_LOGIN_SUCCESS:
                        //登录成功 显示登陆成功
                        test5front.showMainScene(name);
                        System.out.println("登录成功");
                        break;

                    case test5constant.TYPE_LOGIN_FAIL:
                        //登录失败 显示登录失败
                        test5front.showDarkError("登录失败", "用户名或密码错误");
                        System.out.println("登录失败");
                        // 回到登录场景以便重试
                        test5front.showLoginScene();
                        break;

                    case test5constant.TYPE_REGISTER_SUCCESS:
                        //注册成功 显示注册成功
                        test5front.showDarkInfo("注册成功", "您的账号已注册成功，请登录。");
                        System.out.println("注册成功");
                        test5front.showLoginScene();
                        break;

                    case test5constant.TYPE_REGISTER_FAIL:
                        //注册失败 显示注册失败
                        test5front.showDarkError("注册失败", "注册过程中出现错误，请重试。");
                        System.out.println("注册失败");
                        test5front.showLoginScene();
                        break;

                    default:
                        System.out.println("未知消息类型: " + type);
                        break;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 网络或流异常时通知用户
            test5front.showDarkError("网络错误", "与服务器的连接已断开: " + e.getMessage());
            test5front.showLoginScene();
        }
    }
}
