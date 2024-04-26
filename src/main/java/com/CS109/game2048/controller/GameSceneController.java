package com.CS109.game2048.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import com.CS109.game2048.engine.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class GameSceneController {
    @FXML
    private GridPane gridPane;
    @FXML
    private Label stepLabel, scoreLabel, endLabel, goal;
    @FXML
    private AnchorPane endPane;
    @FXML
    private ImageView background;
    @FXML
    private MenuItem emptyBackground, brotherBackground, windowsXPBackground, seaBackground;
    @FXML
    private MenuItem easyButton, normalButton, hardButton, hellButton, insaneButton;
    @FXML
    private MenuItem turnOff, mirage, touchedByTheSky;

    private static final int GRID_SIZE = 4;
    boolean ifGameEnd = false;
    GridNumbers gridNumbers = new GridNumbers();

    private MediaPlayer mediaPlayer;
    private Media media;

    public void initialize() {
    }

    public void initGridPane() {

        this.gridNumbers = new GridNumbers();
        gridNumbers.initGridNumbers();
        fillNumbersIntoGridPane();
        gridNumbers.setStep(0);
        gridNumbers.setScore(0);
        stepLabel.setText(String.valueOf(gridNumbers.getStep()));
        scoreLabel.setText(String.valueOf(gridNumbers.getScore()));

        endPane.setVisible(false);
        gridPane.setOpacity(1.0);

    }

    public void fillNumbersIntoGridPane() {

        int[][] numbers = this.gridNumbers.getNumbers();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {

                String s = String.valueOf(numbers[row][col]);

                Label label = (Label) gridPane.getChildren().get(row * 4 + col);
                label.setText(s);
                label.setStyle("-fx-font-size:36px;-fx-text-fill:white;");

            }
        }
        formatColor();

    }

    public void formatColor() {

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Label label = (Label) gridPane.getChildren().get(row * 4 + col);
                int num = Integer.valueOf(label.getText());
                switch (num) {
                    case 0:
                        label.setText(" ");
                        label.setStyle("-fx-border-color:black;-fx-border-width:5px;");
                        break;
                    case 2:
                        label.setStyle("-fx-background-color:orange;-fx-border-color:black;-fx-border-width:5px;");
                        break;
                    case 4:
                        label.setStyle("-fx-background-color:red;-fx-border-color:black;-fx-border-width:5px;");
                        break;
                    case 8:
                        label.setStyle("-fx-background-color:coral;-fx-border-color:black;-fx-border-width:5px;");
                        break;
                    case 16:
                        label.setStyle("-fx-background-color:green;-fx-border-color:black;-fx-border-width:5px;");
                        break;
                    case 32:
                        label.setStyle("-fx-background-color:blue;-fx-border-color:black;-fx-border-width:5px;");
                        break;
                    case 64:
                        label.setStyle("-fx-background-color:#4B0082;-fx-border-color:black;-fx-border-width:5px;");
                        break;
                    case 128:
                        label.setStyle("-fx-background-color:pink;-fx-border-color:black;-fx-border-width:5px;");
                        break;
                    case 256:
                        label.setStyle("-fx-background-color:brown;-fx-border-color:black;-fx-border-width:5px;");
                        break;
                    case 512:
                        label.setStyle("-fx-background-color:purple;-fx-border-color:black;-fx-border-width:5px;");
                        break;
                    case 1024:
                        label.setStyle("-fx-background-color:cyan;-fx-border-color:black;-fx-border-width:5px;");
                        break;
                    case 2048:
                        label.setStyle("-fx-background-color:maroon;-fx-border-color:black;-fx-border-width:5px;");
                        break;
                    default:
                        label.setStyle("-fx-background-color:lime;");
                }

            }
        }
    }

    public void rightStep() {

        gridNumbers.right();
        fillNumbersIntoGridPane();
        stepLabel.setText(String.valueOf(gridNumbers.getStep()));
        scoreLabel.setText(String.valueOf(gridNumbers.getScore()));
        if (gridNumbers.loseTheGame()) {
            loseTheGame();
        }
        if (gridNumbers.win()) {
            win();
        }

    }

    public void leftStep() {

        gridNumbers.left();
        fillNumbersIntoGridPane();
        stepLabel.setText(String.valueOf(gridNumbers.getStep()));
        scoreLabel.setText(String.valueOf(gridNumbers.getScore()));
        if (gridNumbers.loseTheGame()) {
            loseTheGame();
        }
        if (gridNumbers.win()) {
            win();
        }

    }

    public void downStep() {

        gridNumbers.down();
        fillNumbersIntoGridPane();
        stepLabel.setText(String.valueOf(gridNumbers.getStep()));
        scoreLabel.setText(String.valueOf(gridNumbers.getScore()));
        if (gridNumbers.loseTheGame()) {
            loseTheGame();
        }
        if (gridNumbers.win()) {
            win();
        }

    }

    public void upStep() {

        gridNumbers.up();
        fillNumbersIntoGridPane();
        stepLabel.setText(String.valueOf(gridNumbers.getStep()));
        scoreLabel.setText(String.valueOf(gridNumbers.getScore()));
        if (gridNumbers.loseTheGame()) {
            loseTheGame();
        }
        if (gridNumbers.win()) {
            win();
        }

    }

    public void onKeyPressed(KeyEvent event) {

        switch (event.getCode()) {
            case RIGHT, D:
                rightStep();
                break;
            case LEFT, A:
                leftStep();
                break;
            case DOWN, S:
                downStep();
                break;
            case UP, W:
                upStep();
                break;
            default:
                break;
        }
    }

    public void loseTheGame() {

        endPane.setVisible(true);
        endPane.setOpacity(1);
        gridPane.setOpacity(0.2);

    }

    public void win() {

        endLabel.setText("You Win!");
        endPane.setVisible(true);
        endPane.setOpacity(1);
        gridPane.setOpacity(0.2);

    }

    public void setBackground() {

        Image brother = new Image(String.valueOf(getClass().getResource("/image/brother.jpg")));
        Image windowsXP = new Image(String.valueOf(getClass().getResource("/image/windowsXP.png")));
        Image sea = new Image(String.valueOf(getClass().getResource("/image/sea.png")));

        emptyBackground.setOnAction(event -> background.setImage(null));
        brotherBackground.setOnAction(event -> background.setImage(brother));
        windowsXPBackground.setOnAction(event -> background.setImage(windowsXP));
        seaBackground.setOnAction(event -> background.setImage(sea));


    }

    public void setMusic() {


        String mirageFile = String.valueOf(getClass().getResource("/music/Mirage.mp3"));
        String touchedByTheSkyFile = String.valueOf(getClass().getResource("/music/Touched by the Sky.mp3"));

        turnOff.setOnAction(event -> {

            if (media != null) {
                mediaPlayer.stop();
            }

        });
        mirage.setOnAction(event -> {

            if (media != null) {
                mediaPlayer.stop();
            }

            media = new Media(mirageFile);
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();

        });
        touchedByTheSky.setOnAction(event -> {

            if (media != null) {
                mediaPlayer.stop();
            }

            media = new Media(touchedByTheSkyFile);
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();

        });

    }

    public void setGoal() {

        easyButton.setOnAction(event -> {
            initGridPane();
            gridNumbers.setGoal(1024);
            goal.setText(String.valueOf(gridNumbers.getGoal()));
        });
        normalButton.setOnAction(event -> {
            initGridPane();
            gridNumbers.setGoal(2048);
            goal.setText(String.valueOf(gridNumbers.getGoal()));
        });
        hardButton.setOnAction(event -> {
            initGridPane();
            gridNumbers.setGoal(4096);
            goal.setText(String.valueOf(gridNumbers.getGoal()));
        });
        hellButton.setOnAction(event -> {
            initGridPane();
            gridNumbers.setGoal(8196);
            goal.setText(String.valueOf(gridNumbers.getGoal()));
        });
        insaneButton.setOnAction(event -> {
            initGridPane();
            gridNumbers.setGoal(16384);
            goal.setText(String.valueOf(gridNumbers.getGoal()));
        });

        //goal.setText(String.valueOf(gridNumbers.getGoal()));

    }

    public void logOut(ActionEvent event) throws IOException {

        Parent root;
        Stage stage;
        Scene scene;

        root = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
        stage = (Stage) stepLabel.getScene().getWindow();
        scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("2048/login");
        stage.show();

    }

    public void aboutUs() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/FXML/aboutUs.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Image logo = new Image(getClass().getResourceAsStream("/image/logo.png"));

        stage.getIcons().add(logo);
        stage.setTitle("About Us");
        stage.setScene(scene);
        stage.show();

    }

}

