package com.CS109.game2048.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class test extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader login = new FXMLLoader(Main.class.getResource("/FXML/game.fxml"));
        Scene loginScene=new Scene(login.load());
        //loginScene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        Image logo = new Image(getClass().getResourceAsStream("/image/logo.png"));

        primaryStage.setTitle("2048/login");
        primaryStage.setScene(loginScene);
        primaryStage.getIcons().add(logo);

        primaryStage.show();
    }
}
