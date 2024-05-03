package com.CS109.game2048.controller;

import com.CS109.game2048.main.Main;
import com.CS109.game2048.model.User;
import com.CS109.game2048.repository.impl.UserDAOImpl;
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

public class LoginSceneController {

    private final UserDAOImpl userDAO = new UserDAOImpl();
    private final Stage stage = Main.stage;

    private boolean ifLoginScene = true;


    @FXML
    private PasswordField loginPassword, signupPassword, signupConfirm;
    @FXML
    private TextField loginUsername, signupUsername;
    @FXML
    private Label loginError, signupError;
    @FXML
    private Button slideButton;
    @FXML
    private Pane slidePane,pane;
    @FXML
    private VBox vbox;
    public void initialize() {
        vbox.prefHeightProperty().bind(pane.heightProperty());
        vbox.prefWidthProperty().bind(pane.widthProperty());
    }

    public void switchToGameScene() throws IOException {

        String username = loginUsername.getText();
        String password = loginPassword.getText();

        if (ifLogin(username, password)) {

            Main.changeView("/fxml/game.fxml");
            Scene scene = stage.getScene();

            Label label = (Label) scene.lookup("#usernameLabel");
            label.setText(username);

            stage.setTitle("2048");
        }
    }

    public void guestMode() throws IOException {

        Main.changeView("/FXML/game.fxml");

        stage.setTitle("2048");

    }

    public boolean ifLogin(String username, String password) {

        if(StringUtil.ifEmpty(username)||StringUtil.ifEmpty(password)){
            loginError.setText("The fields can't be empty!");
        }
        if (!userDAO.ifUsernameExist(username)) {
            loginError.setText("Username doesn't exist!");
            loginUsername.setText("");
            loginPassword.setText("");
            return false;
        }
        if (userDAO.ifPasswordCorrect(username, password)) {
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

            slidePane.setStyle("-fx-background-color:#B5D9EA;");
            slideButton.setText("Log In");
            slideButton.setStyle("-fx-background-color:#E6E3E2;");

            ifLoginScene = false;
        } else {

            transition.setToX(0);
            transition.playFromStart();

            slidePane.setStyle("-fx-background-color:#E6E3E2;");
            slideButton.setText("Sign Up");
            slideButton.setStyle("-fx-background-color:#B5D9EA;");

            ifLoginScene = true;
        }
    }

    public void signup() {

        String password = signupPassword.getText();
        String confirm = signupConfirm.getText();
        String username = signupUsername.getText();

        if (userDAO.ifUsernameExist(username)) {
            signupUsername.setText("");
            signupPassword.setText("");
            signupConfirm.setText("");
            signupError.setText("The username existed!");
            return;
        }

        if (StringUtil.ifEmpty(username) || StringUtil.ifEmpty(password) || StringUtil.ifEmpty(confirm)) {
            signupError.setText("The field can't be empty!");
            return;
        }

        if (!confirm.equals(password)) {
            signupPassword.setText("");
            signupConfirm.setText("");
            signupError.setText("Passwords are different!");
            return;
        }

        User user = new User(0,username,password,0);
        userDAO.createUser(user);

        signupError.setText("Successfully Sign Up!");

    }

}
