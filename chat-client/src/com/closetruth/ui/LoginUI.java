package com.closetruth.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LoginUI extends Application {
    private Socket socket;

    @Override
    public void start(Stage stage) {
        // 标题
        Label title = new Label("Welcome Back");
        title.setFont(Font.font(24));
        title.setTextFill(Color.web("#E5E7EB"));

        // 输入框
        TextField username = new TextField();
        username.setPromptText("Username");
        username.setMaxWidth(260);
        username.setStyle("""
            -fx-background-color: #1f2933;
            -fx-text-fill: #E5E7EB;
            -fx-prompt-text-fill: #9CA3AF;
            -fx-background-radius: 10;
            -fx-border-radius: 10;
            -fx-border-color: #374151;
            -fx-padding: 10;
        """);

        // Enter 按钮
        Button enterBtn = new Button("Enter");
        enterBtn.setPrefWidth(120);
        enterBtn.setStyle("""
            -fx-background-color: #3b82f6;
            -fx-text-fill: white;
            -fx-background-radius: 10;
            -fx-font-size: 14;
        """);

        // hover 效果
        enterBtn.setOnMouseEntered(e ->
                enterBtn.setStyle("""
                -fx-background-color: #2563eb;
                -fx-text-fill: white;
                -fx-background-radius: 10;
                -fx-font-size: 14;
            """)
        );

        //按钮监听,发送名字到服务端登录， 登陆成功后跳转到 ChatUI
        enterBtn.setOnAction(e -> {
            String name = username.getText();
            if (!name.isEmpty()) {
                try {
                    login(name);
                    //启动聊天窗口
                    new ChatUI(name, socket).start(new Stage());
                    stage.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        enterBtn.setOnMouseExited(e ->
                enterBtn.setStyle("""
                -fx-background-color: #3b82f6;
                -fx-text-fill: white;
                -fx-background-radius: 10;
                -fx-font-size: 14;
            """)
        );

        // Exit 按钮
        Button exitBtn = new Button("Exit");
        exitBtn.setPrefWidth(120);
        exitBtn.setStyle("""
            -fx-background-color: #374151;
            -fx-text-fill: #E5E7EB;
            -fx-background-radius: 10;
        """);

        exitBtn.setOnAction(e -> stage.close());

        // 卡片容器
        VBox card = new VBox(18);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(35));
        card.setStyle("""
            -fx-background-color: #111827;
            -fx-background-radius: 18;
        """);

        card.setEffect(new DropShadow(25, Color.BLACK));
        card.getChildren().addAll(title, username, enterBtn, exitBtn);

        // 根布局（整体背景）
        VBox root = new VBox(card);
        root.setAlignment(Pos.CENTER);
        root.setStyle("""
            -fx-background-color: radial-gradient(
                focus-distance 0%,
                center 50% 50%,
                radius 60%,
                #1f2937,
                #020617
            );
        """);

        Scene scene = new Scene(root, 420, 320);
        scene.setFill(Color.web("#020617")); // 关键！
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    private void login(String name) throws IOException {
        this.socket = new Socket(Constant.SERVER_IP, Constant.SERVER_PORT);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(Constant.TYPE_LOGIN);
        dos.writeUTF(name);
        dos.flush();
    }

    public static void main(String[] args) {
        launch();
    }
}
