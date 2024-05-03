package com.CS109.game2048.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        Main.stage = stage;

        changeView("/FXML/login.fxml");
        stage.setTitle("2048/login");
        stage.setFullScreen(true);

    }

    public static void changeView(String fxml) throws IOException {

        Parent root = FXMLLoader.load(Main.class.getResource(fxml));
        Scene scene = new Scene(root);
        Image logo = new Image(Main.class.getResourceAsStream("/image/logo.png"));

        Main.stage.setScene(scene);
        Main.stage.getIcons().add(logo);

        stage.show();

    }

    public static void addView(String fxml,String title) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Main.class.getResource(fxml));
        Scene scene = new Scene(root);
        Image logo = new Image(Main.class.getResourceAsStream("/image/logo.png"));

        stage.setTitle(title);
        stage.setScene(scene);
        stage.getIcons().add(logo);

        stage.show();

    }

}
