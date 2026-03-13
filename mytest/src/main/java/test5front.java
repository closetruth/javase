import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class test5front extends Application {

    private static Stage primaryStage;
    private static Scene loginScene;
    private Scene registerScene;
    private static Scene mainScene;

    // --- 样式常量 ---
    private static final String DARK_BG_COLOR = "#1e1e1e";
    private static final String DARK_BG = "-fx-background-color: " + DARK_BG_COLOR + ";";
    private static final String CARD_BG = "-fx-background-color: #2d2d2d; -fx-background-radius: 15;";
    private final String TEXT_FILL = "-fx-text-fill: #ecf0f1;";
    private final String INPUT_STYLE = "-fx-background-color: #3d3d3d; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10; -fx-border-color: #444; -fx-border-radius: 5;";
    private final String PRIMARY_BTN = "-fx-background-color: #0078D7; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand; -fx-padding: 10 20;";
    private final String LINK_BTN = "-fx-background-color: transparent; -fx-text-fill: #3498db; -fx-cursor: hand; -fx-underline: true;";
    private static final String DANGER_BTN = "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;";

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("安全中心 - 无闪烁黑暗模式");

        // 初始化所有场景
        createLoginScene();
        createRegisterScene();

        // 设置初始页面
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    // 核心优化：创建一个黑色背景的容器包装器，防止切换闪烁
    private Scene createBlackScene(GridPane content, double width, double height) {
        StackPane root = new StackPane(content);
        root.setStyle(DARK_BG); // 强制根容器为黑色
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, width, height);
        scene.setFill(Color.web(DARK_BG_COLOR)); // 核心：设置场景填充色为黑色，消除白色闪光
        return scene;
    }

    private void createLoginScene() {
        GridPane grid = createBaseGrid();
        Label title = new Label("系统登录");
        title.setFont(Font.font("System", FontWeight.BOLD, 26));
        title.setStyle(TEXT_FILL);
        grid.add(title, 0, 0, 2, 1);

        grid.add(createStyledLabel("用户名:"), 0, 1);
        TextField userField = new TextField();
        userField.setPromptText("admin");
        userField.setStyle(INPUT_STYLE);
        grid.add(userField, 1, 1);

        grid.add(createStyledLabel("密码:"), 0, 2);
        PasswordField passField = new PasswordField();
        passField.setPromptText("123456");
        passField.setStyle(INPUT_STYLE);
        grid.add(passField, 1, 2);

        Button loginBtn = new Button("立即登录");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setStyle(PRIMARY_BTN);

        loginBtn.setOnAction(e -> {
            String name = userField.getText();
            String pass = passField.getText();
            if (!name.isEmpty()) {
                //数据输出流发送名字密码到服务端
                try {
                    requestLogin(name, pass);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                showDarkErrorAlert("登录失败", "请输入用户名");
            }
        });

        Button toRegBtn = new Button("新用户注册");
        toRegBtn.setStyle(LINK_BTN);
        toRegBtn.setOnAction(e -> primaryStage.setScene(registerScene));

        grid.add(loginBtn, 1, 3);
        grid.add(toRegBtn, 1, 4);

        loginScene = createBlackScene(grid, 450, 450);
    }


    private void createRegisterScene() {
        GridPane grid = createBaseGrid();
        Label title = new Label("新账号注册");
        title.setFont(Font.font("System", FontWeight.BOLD, 26));
        title.setStyle(TEXT_FILL);
        grid.add(title, 0, 0, 2, 1);

        // Create input fields with explicit variable declarations
        grid.add(createStyledLabel("用户名:"), 0, 1);
        TextField nameField = new TextField();
        nameField.setStyle(INPUT_STYLE);
        grid.add(nameField, 1, 1);

        grid.add(createStyledLabel("电子邮箱:"), 0, 2);
        TextField emailField = new TextField();
        emailField.setStyle(INPUT_STYLE);
        grid.add(emailField, 1, 2);

        grid.add(createStyledLabel("设置密码:"), 0, 3);
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle(INPUT_STYLE);
        grid.add(passwordField, 1, 3);

        Button regBtn = new Button("立即注册");
        regBtn.setMaxWidth(Double.MAX_VALUE);
        regBtn.setStyle(PRIMARY_BTN);
        regBtn.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String pass = passwordField.getText();
            if (!name.isEmpty()) {
                //数据输出流发送名字密码到服务端
                try {
                    requestRegister(name, email, pass);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                showDarkErrorAlert("注册失败", "请输入用户名");
            }
        });

        Button backBtn = new Button("返回登录");
        backBtn.setStyle(LINK_BTN);
        backBtn.setOnAction(e -> primaryStage.setScene(loginScene));

        grid.add(regBtn, 1, 4);
        grid.add(backBtn, 1, 5);

        registerScene = createBlackScene(grid, 450, 500);
    }

    private void requestRegister(String name, String email, String pass) throws IOException {
        Socket socket = new Socket(test5constant.SERVER_IP, test5constant.SERVER_PORT);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(test5constant.TYPE_REGISTER_REQUEST);
        dos.writeUTF(name);
        dos.writeUTF(email);
        dos.writeUTF(pass);
        dos.flush();

        new test5frontThread(name, socket).start();
    }

    public static void createMainScene(String username) {
        VBox card = new VBox(25);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40));
        card.setStyle(CARD_BG);
        card.setEffect(new DropShadow(20, Color.BLACK));

        Label welcomeLabel = new Label("欢迎回来, " + username + "!");
        welcomeLabel.setFont(Font.font("System", FontWeight.BOLD, 30));
        welcomeLabel.setStyle("-fx-text-fill: #00A8FF;");

        Button logoutBtn = new Button("退出系统");
        logoutBtn.setStyle(DANGER_BTN + "-fx-padding: 10 30;");
        logoutBtn.setOnAction(e -> primaryStage.setScene(loginScene));

        card.getChildren().addAll(welcomeLabel, logoutBtn);

        StackPane root = new StackPane(card);
        root.setStyle(DARK_BG);
        root.setPadding(new Insets(50));

        mainScene = new Scene(root, 600, 500);
        mainScene.setFill(Color.web(DARK_BG_COLOR));
    }

    // Public thread-safe helper: create the main scene and switch to it on the JavaFX thread
    public static void showMainScene(final String username) {
        Platform.runLater(() -> {
            createMainScene(username);
            if (primaryStage != null && mainScene != null) {
                primaryStage.setScene(mainScene);
            }
        });
    }

    // Public thread-safe helper to switch to the login scene
    public static void showLoginScene() {
        Platform.runLater(() -> {
            if (primaryStage != null && loginScene != null) {
                primaryStage.setScene(loginScene);
            }
        });
    }

    // Public thread-safe dark-styled error alert usable from background threads
    public static void showDarkError(final String title, final String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(primaryStage);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);

            DialogPane pane = alert.getDialogPane();
            pane.setStyle("-fx-background-color: #2d2d2d; -fx-border-color: #e74c3c; -fx-border-width: 2;");

            Label contentLabel = (Label) pane.lookup(".content.label");
            if (contentLabel != null) contentLabel.setStyle("-fx-text-fill: white;");

            Button okBtn = (Button) pane.lookupButton(ButtonType.OK);
            if (okBtn != null) okBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

            alert.showAndWait();
        });
    }

    // Public thread-safe dark-styled info alert usable from background threads
    public static void showDarkInfo(final String title, final String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(primaryStage);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);

            DialogPane pane = alert.getDialogPane();
            pane.setStyle("-fx-background-color: #2d2d2d; -fx-border-color: #00A8FF; -fx-border-width: 2;");

            Label contentLabel = (Label) pane.lookup(".content.label");
            if (contentLabel != null) contentLabel.setStyle("-fx-text-fill: white;");

            Button okBtn = (Button) pane.lookupButton(ButtonType.OK);
            if (okBtn != null) okBtn.setStyle("-fx-background-color: #00A8FF; -fx-text-fill: white;");

            alert.showAndWait();
        });
    }

    private void showDarkErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage); // 关联父窗口
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        DialogPane pane = alert.getDialogPane();
        pane.setStyle("-fx-background-color: #2d2d2d; -fx-border-color: #e74c3c; -fx-border-width: 2;");

        Label contentLabel = (Label) pane.lookup(".content.label");
        if (contentLabel != null) contentLabel.setStyle("-fx-text-fill: white;");

        Button okBtn = (Button) pane.lookupButton(ButtonType.OK);
        okBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

        alert.showAndWait();
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle(TEXT_FILL);
        return label;
    }

    private GridPane createBaseGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(20);
        grid.setPadding(new Insets(40));
        grid.setMaxSize(400, 400); // 限制卡片大小
        grid.setStyle(CARD_BG);
        grid.setEffect(new DropShadow(15, Color.BLACK));
        return grid;
    }

    private void requestLogin(String name, String pass) throws IOException {
        Socket socket = new Socket(test5constant.SERVER_IP, test5constant.SERVER_PORT);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(test5constant.TYPE_LOGIN_REQUEST);
        dos.writeUTF(name);
        dos.writeUTF(pass);
        dos.flush();

        new test5frontThread(name, socket).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

