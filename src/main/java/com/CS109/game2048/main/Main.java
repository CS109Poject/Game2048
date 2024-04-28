package com.CS109.game2048.main;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;

public class Main extends Application {

    private static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        Main.stage = stage;
        FXMLLoader login = new FXMLLoader(getClass().getResource("/FXML/login.fxml"));
        Scene loginScene = new Scene(login.load());
        Image logo = new Image(getClass().getResourceAsStream("/image/logo.png"));

        stage.setTitle("2048/login");
        stage.setScene(loginScene);
        stage.getIcons().add(logo);

        stage.show();

    }

    public void changeView(String fxml) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        Image logo = new Image(getClass().getResourceAsStream("/image/logo.png"));

        Main.stage.setScene(scene);
        Main.stage.getIcons().add(logo);

        stage.show();

    }

}
