
import java.io.*;
import java.net.Socket;

public class test5backThread extends Thread{
    private Socket socket;
    File userFile = new File("user.txt");
    public test5backThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while (true) {
                int type = dis.readInt();
                switch (type) {
                    case test5constant.TYPE_LOGIN_REQUEST:
                        //登录请求
                        String loginName = dis.readUTF();
                        String loginPass = dis.readUTF();
                        System.out.println("------------------------------");
                        System.out.println("登录请求");
                        System.out.println("用户名：" + loginName);
                        System.out.println("密码：" + loginPass);
                        System.out.println("------------------------------");
                        loginUser(loginName, loginPass);
                        break;
                    case test5constant.TYPE_REGISTER_REQUEST:
                        //注册请求
                        String registerName = dis.readUTF();
                        String registerEmail = dis.readUTF();
                        String registerPass = dis.readUTF();
                        System.out.println("------------------------------");
                        System.out.println("注册请求");
                        System.out.println("用户名：" + registerName);
                        System.out.println("邮箱：" + registerEmail);
                        System.out.println("密码：" + registerPass);
                        System.out.println("------------------------------");
                        registerUser(registerName, registerEmail, registerPass);

                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loginUser(String loginName, String loginPass) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(userFile));

            String line;

            while ((line = br.readLine()) != null) {
                System.out.println("用户信息：" + line);

                String[] arr = line.split(",");

                if (arr[0].equals(loginName) && arr[2].equals(loginPass)) {
                    System.out.println(loginName + "用户登录成功");

                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeInt(test5constant.TYPE_LOGIN_SUCCESS);
                    dos.flush();
                    return;
                }
            }

            System.out.println(loginName + "用户登录失败");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(test5constant.TYPE_LOGIN_FAIL);
            dos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerUser(String registerName, String registerEmail, String registerPass) {
        try {
            //搜索文件是否有该用户，没有该用户就写入到文件中
            FileInputStream fis = new FileInputStream(userFile);
            //详解：创建一个字节数组，长度为1024，用于存储文件内容
            byte[] bytes = new byte[1024];
            //详解：从文件中读取数据到字节数组中
            int len = fis.read(bytes);
            System.out.println("读取数据");
            while (len != -1) {
                String userInfo = new String(bytes, 0, len);
                String[] userInfoArray = userInfo.split(",");
                if (userInfoArray[0].equals(registerName)) {
                    //用户名已存在
                    System.out.println(registerName + "用户已存在");
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeInt(test5constant.TYPE_REGISTER_FAIL);
                    return;
                }
                //详解：从文件中读取数据长度为0，则说明文件已读完
                len = fis.read(bytes);
                if (len == -1) {
                    //用户名不存在，可以注册
                    System.out.println(registerName + "用户不存在，用户注册成功");
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeInt(test5constant.TYPE_REGISTER_SUCCESS);
                    //将用户信息写入文件中
                    FileOutputStream fos = new FileOutputStream(userFile, true);
                    fos.write((registerName + "," + registerEmail + "," + registerPass + "\r\n").getBytes());
                    fos.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
