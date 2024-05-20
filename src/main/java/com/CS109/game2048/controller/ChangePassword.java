package com.CS109.game2048.controller;


import com.CS109.game2048.model.Email;
import com.CS109.game2048.repository.dao.UserDAO;
import com.CS109.game2048.repository.impl.UserSQL;
import com.CS109.game2048.util.EmailUtil;
import com.CS109.game2048.util.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Objects;

public class ChangePassword {

    UserDAO userDAO = new UserSQL();
    Email email;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField confirmField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button sendButton;

    @FXML
    private TextField verificationField;

    @FXML
    private Label errorLabel;

    @FXML
    void sendVerificationCode() {
        String email = emailField.getText();
        if (!userDAO.ifEmailExist(email)) {
            emailField.setText("");
            passwordField.setText("");
            confirmField.setText("");
            errorLabel.setText("The email has not been registered!");
            return;
        }

//        if (StringUtil.ifEmpty(email)){
//            signupError.setText("The email is empty!");
//            return;
//        }
        String verification = EmailUtil.sendEmail(email);
        if (StringUtil.ifEmpty(verification)) {
            errorLabel.setText("Email format is incorrect!");
            return;
        }
        errorLabel.setText("Send successfully!");
        this.email = new Email(email, verification);
    }

    @FXML
    void changePassword() {
        String password = passwordField.getText();
        String confirm = confirmField.getText();
        String email = emailField.getText();
        String verificationCode = verificationField.getText();

        if (!userDAO.ifEmailExist(email)) {
            emailField.setText("");
            passwordField.setText("");
            confirmField.setText("");
            verificationField.setText("");
            errorLabel.setText("The email has not been registered!");
            return;
        }
        if (!Objects.equals(verificationCode, this.email.getVerificationCode()) || !Objects.equals(email, this.email.getEmail())) {
            verificationField.setText("");
            errorLabel.setText("Verification Code Wrong!");
            return;
        }

        if (StringUtil.ifEmpty(email) || StringUtil.ifEmpty(password) || StringUtil.ifEmpty(confirm)) {
            errorLabel.setText("The field can't be empty!");
            return;
        }

        if (!confirm.equals(password)) {
            passwordField.setText("");
            confirmField.setText("");
            errorLabel.setText("Passwords are different!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Dialog");
        alert.setHeaderText("Are you sure to change your password?");
        alert.showAndWait();

        userDAO.changePassword(email, password);

        errorLabel.setText("Successfully Change!");
    }

}
