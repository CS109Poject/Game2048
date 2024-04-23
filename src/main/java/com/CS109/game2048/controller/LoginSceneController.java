package com.CS109.game2048.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginSceneController {

    private String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String DB_URL = "jdbc:mysql://localhost:3306/test?" +
            "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "MySQL190504";


    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Label topLabel;
    private String password;

    public void switchToGameScene(ActionEvent event) throws IOException {

        password = passwordField.getText();
        username = usernameField.getText();

        if (ifLogin(username, password)) {

            root = FXMLLoader.load(getClass().getResource("/FXML/game.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("2048");
            stage.setScene(scene);
            stage.show();

        } else {
            passwordField.setText("");
            topLabel.setText("ERROR,TRY AGAIN.");
        }

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

    private String username;


    public void switchToSignupScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/signup.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("2048/signup");
        scene.getStylesheets().add(getClass().getResource("/css/signup.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
