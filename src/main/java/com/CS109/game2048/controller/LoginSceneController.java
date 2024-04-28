package com.CS109.game2048.controller;

import com.CS109.game2048.main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import static com.CS109.game2048.main.Main.changeView;

public class LoginSceneController {

    private String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String DB_URL = "jdbc:mysql://localhost:3306/test?" +
            "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "MySQL190504";


    private Stage stage = Main.stage;
    private Scene scene;
    private Parent root;


    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Label topLabel;
    @FXML
    private ImageView closeButton;

    private String username;
    private String password;

    public void initialize() {

    }

    public void switchToGameScene() throws IOException {

        username = usernameField.getText();
        password = passwordField.getText();

        if (ifLogin(username, password)) {

            changeView("/fxml/game.fxml");
            scene = stage.getScene();

            Label label = (Label) scene.lookup("#usernameLabel");
            label.setText(username);

            stage.setTitle("2048");

            stage.show();

        } else {

            passwordField.setText("");
            topLabel.setText("ERROR,TRY AGAIN.");

        }

    }

    public void guestMode() throws IOException {

        Main.changeView("/FXML/game.fxml");

        stage.setTitle("2048");

    }

    public boolean ifLogin(String username, String password) {

        Connection conn = null;
        Statement stmt = null;
        boolean result = false;

        try {

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT username, password FROM new_table";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("username");
                String passwd = rs.getString("password");
                if (name.equals(username)) {
                    if (passwd.equals(password)) {
                        result = true;
                    }
                }
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {

            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return result;

    }

    public void switchToSignupScene(ActionEvent event) throws IOException {

        Main.changeView("/FXML/signup.fxml");

        stage.setTitle("2048/signup");

    }

    public void handleCloseButtonClicked() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
