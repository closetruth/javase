import java.io.IOException;

public class MouseTest {

    public static void main(String[] args) throws IOException {

        // 开启鼠标模式
        System.out.print("\033[?1000h");
        System.out.flush();

        System.out.println("点击终端试试 (Ctrl+C退出)");

        while (true) {

            int ch = System.in.read();

            if (ch == 27) { // ESC
                System.out.print("ESC ");
            } else {
                System.out.print(ch + " ");
            }

        }
    }
}