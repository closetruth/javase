package com.closetruth.ui;

import com.sun.javafx.charts.Legend;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ChatUI extends Application {
    private Socket socket;
    private String name;
    private Label onlineCount = new Label("在线人数：0");
    ListView<String> userList = new ListView<>();
    TextArea messageArea = new TextArea();

    public ChatUI(String name, Socket socket) {
        this.socket = socket;
        this.name = name;
        new ClientReaderThread(socket, this).start();
    }


    @Override
    public void start(Stage stage) {

        // ===== 顶部栏 =====
        Label title = new Label(name);
        title.setFont(Font.font(18));
        title.setTextFill(Color.web("#E5E7EB"));

        onlineCount.setTextFill(Color.web("#9CA3AF"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topBar = new HBox(10, title, spacer, onlineCount);
        topBar.setPadding(new Insets(12));
        topBar.setStyle("-fx-background-color: #020617;");

        // ===== 左侧在线列表 =====
        userList.setPrefWidth(140);
        userList.setStyle("""
            -fx-control-inner-background: #020617;
            -fx-text-fill: #E5E7EB;
        """);

        VBox leftPane = new VBox(userList);
        leftPane.setStyle("-fx-background-color: #020617;");

        // ===== 消息展示区 =====
        messageArea.setEditable(false);
        messageArea.setWrapText(true);
        messageArea.setStyle("""
            -fx-control-inner-background: #020617;
            -fx-text-fill: #E5E7EB;
            -fx-font-size: 13;
        """);

        // ===== 输入区 =====
        TextField inputField = new TextField();
        inputField.setPromptText("输入消息...");
        inputField.setStyle("""
            -fx-background-color: #020617;
            -fx-text-fill: #E5E7EB;
            -fx-prompt-text-fill: #6B7280;
            -fx-background-radius: 10;
        """);

        Button sendBtn = new Button("发送");
        sendBtn.setStyle("""
            -fx-background-color: #3b82f6;
            -fx-text-fill: white;
            -fx-background-radius: 10;
        """);

        HBox inputBar = new HBox(10, inputField, sendBtn);
        inputBar.setPadding(new Insets(10));
        HBox.setHgrow(inputField, Priority.ALWAYS);

        VBox rightPane = new VBox(messageArea, inputBar);
        VBox.setVgrow(messageArea, Priority.ALWAYS);
        rightPane.setStyle("-fx-background-color: #020617;");

        // ===== 中心布局 =====
        BorderPane center = new BorderPane();
        center.setLeft(leftPane);
        center.setCenter(rightPane);

        // ===== 发送逻辑（本地模拟） =====
        sendBtn.setOnAction(e -> {
            sendMessage(messageArea, inputField);
        });

        inputField.setOnAction(e -> {
            sendMessage(messageArea, inputField);
        });

        // ===== 根布局 =====
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(center);
        root.setStyle("-fx-background-color: #020617;");

        Scene scene = new Scene(root, 720, 480);
        scene.setFill(Color.web("#020617")); // 关键！
        stage.setTitle("LAN Chat");
        stage.setScene(scene);
        stage.show();
    }

    private void sendMessage(TextArea area, TextField input) {
        String msg = input.getText().trim();
        //发送群聊
        String msgResult = name  + ": " + msg;
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(Constant.TYPE_CHAT_ALL);
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void updateOnlineUsers(List<String> onlineUsers) {
        onlineCount.setText("在线人数：" + onlineUsers.size());
        //  更新在线列表
        userList.getItems().clear();
        userList.getItems().addAll(onlineUsers);
    }

    //在线用户
    public void appendMessage(String message) {
        messageArea.appendText(message + "\n");
    }
}
