package com.CS109.game2048.controller;

import javafx.scene.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class SignupSceneController {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost:3306/test?" +
            "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "MySQL190504";

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmField;
    @FXML
    private TextField usernameField;
    @FXML
    private Label topLabel;

    public void switchToLoginScene(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("2048/login");
        stage.show();

    }

    public void signup(ActionEvent event) throws IOException {

        String password = passwordField.getText();
        String confirm = confirmField.getText();
        String username = usernameField.getText();

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT username FROM new_table";
            ResultSet rs = stmt.executeQuery(sql);

            boolean ifEmpty = password.isEmpty() || confirm.isEmpty() || username.isEmpty();
            if (ifEmpty) {
                topLabel.setText("The Field can't be empty!");
                return;
            }

            boolean ifTwoPasswordsEquals = password.equals(confirm);
            if (!ifTwoPasswordsEquals) {
                passwordField.setText("");
                confirmField.setText("");
                topLabel.setText("Passwords are different!");
                return;
            }

            boolean ifUsernameExits = false;
            while (rs.next()) {
                String name = rs.getString("username");
                if (username.equals(name)) {
                    ifUsernameExits = true;
                }
            }

            if (!ifUsernameExits) {

                String sql2 = "INSERT INTO new_table (username, password,highestScore) VALUES (?, ?,0)";
                PreparedStatement pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1,username);
                pstmt.setString(2,password);
                pstmt.executeUpdate();

                switchToLoginScene(event);

                rs.close();
                stmt.close();
                pstmt.close();
                conn.close();

            } else {
                topLabel.setText("The username existed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
