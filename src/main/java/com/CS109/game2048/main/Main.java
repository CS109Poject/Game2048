package com.CS109.game2048.main;

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader login = new FXMLLoader(Main.class.getResource("/FXML/login.fxml"));
        Scene loginScene=new Scene(login.load());
        loginScene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        Image logo = new Image(getClass().getResourceAsStream("/image/logo.png"));

        stage.setTitle("2048/login");
        stage.setScene(loginScene);
        stage.getIcons().add(logo);

        stage.show();

    }
}
