package com.CS109.game2048.controller;

import com.CS109.game2048.main.Main;
import com.CS109.game2048.model.Email;
import com.CS109.game2048.model.User;
import com.CS109.game2048.repository.dao.UserDAO;
import com.CS109.game2048.repository.impl.UserSQL;
import com.CS109.game2048.util.ArrayUtil;
import com.CS109.game2048.util.EmailUtil;
import com.CS109.game2048.util.StringUtil;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class LoginSceneController {

    private final UserDAO userDAO = new UserSQL();
    private final Stage stage = Main.stage;

    private boolean ifLoginScene = true;

    private Email email;


    @FXML
    private PasswordField loginPassword, signupPassword, signupConfirm;
    @FXML
    private TextField loginEmail, signUpEmail, signUpVerification;
    @FXML
    private Label loginError, signupError;
    @FXML
    private Button slideButton;
    @FXML
    private Pane slidePane, pane;
    @FXML
    private VBox vbox;


    public void initialize() {
        vbox.prefHeightProperty().bind(pane.heightProperty());
        vbox.prefWidthProperty().bind(pane.widthProperty());
    }

    public void switchToGameScene() throws IOException {

        String email = loginEmail.getText();
        String password = loginPassword.getText();

        if (ifLogin(email, password)) {

            Main.changeView("/fxml/game.fxml");
            Scene scene = stage.getScene();

            Label label = (Label) scene.lookup("#emailLabel");
            label.setText(email);

            stage.setTitle("2048");
        }
    }

    public void guestMode() throws IOException {

        Main.changeView("/FXML/game.fxml");

        stage.setTitle("2048");

    }

    public boolean ifLogin(String email, String password) {

        if (StringUtil.ifEmpty(email) || StringUtil.ifEmpty(password)) {
            loginError.setText("The fields can't be empty!");
        }
        if (!userDAO.ifEmailExist(email)) {
            loginError.setText("Email doesn't exist!");
            loginEmail.setText("");
            loginPassword.setText("");
            return false;
        }
        if (userDAO.ifPasswordCorrect(email, password)) {
            return true;
        } else {
            loginError.setText("Password Error!");
            loginPassword.setText("");
            return false;
        }

    }

    public void slide() {

        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), slidePane);

        if (ifLoginScene) {

            transition.setToX(400);
            transition.playFromStart();

            slidePane.setStyle("-fx-background-color:#F3F3B5;");
            slideButton.setText("Log In");
            //slideButton.setStyle("-fx-background-color:#ECC400;");

            ifLoginScene = false;
        } else {

            transition.setToX(0);
            transition.playFromStart();

            slidePane.setStyle("-fx-background-color:#F3F31B;");
            slideButton.setText("Sign Up");
            //slideButton.setStyle("-fx-background-color:#ECC400;");

            ifLoginScene = true;
        }
    }

    public void signup() {

        String password = signupPassword.getText();
        String confirm = signupConfirm.getText();
        String email = signUpEmail.getText();
        String username = "user";
        String verificationCode = signUpVerification.getText();

        if (userDAO.ifEmailExist(email)) {
            signUpEmail.setText("");
            signupPassword.setText("");
            signupConfirm.setText("");
            signUpVerification.setText("");
            signupError.setText("The email has been registered!");
            return;
        }
        if (!Objects.equals(verificationCode, this.email.getVerificationCode()) || !Objects.equals(email, this.email.getEmail())) {
            signUpVerification.setText("");
            signupError.setText("Verification Code Wrong!");
            return;
        }

        if (StringUtil.ifEmpty(email) || StringUtil.ifEmpty(password) || StringUtil.ifEmpty(confirm)) {
            signupError.setText("The field can't be empty!");
            return;
        }

        if (!confirm.equals(password)) {
            signupPassword.setText("");
            signupConfirm.setText("");
            signupError.setText("Passwords are different!");
            return;
        }

        User user = new User(0, username, password, 0, email);
        userDAO.createUser(user);

        signupError.setText("Successfully Sign Up!");

    }

    public void switchToChangePasswordScene() throws IOException {
        Main.addView("/FXML/changePassword.fxml", "Change Password");
    }

    public void sendVerificationCode() {

        String email = signUpEmail.getText();
        if (userDAO.ifEmailExist(email)) {
            signUpEmail.setText("");
            signupPassword.setText("");
            signupConfirm.setText("");
            signupError.setText("The email existed!");
            return;
        }

//        if (StringUtil.ifEmpty(email)){
//            signupError.setText("The email is empty!");
//            return;
//        }
        String verification = EmailUtil.sendEmail(email);
        if(StringUtil.ifEmpty(verification)){
            signupError.setText("Email format is incorrect!");
            return;
        }
        signupError.setText("Send successfully!");
        this.email = new Email(email, verification);
    }

}
