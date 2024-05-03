package com.CS109.game2048.controller;

import com.CS109.game2048.main.Main;
import com.CS109.game2048.repository.impl.UserDAOImpl;
import com.CS109.game2048.service.GridNumbers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.IOException;

public class GameSceneController {
    @FXML
    private GridPane gridPane;
    @FXML
    private Label stepLabel, scoreLabel, endLabel, goal, usernameLabel, highestScoreLabel, modeLabel;
    @FXML
    private AnchorPane endPane;
    @FXML
    private Pane pane;
    @FXML
    private ImageView background;
    @FXML
    private MenuItem emptyBackground, brotherBackground, windowsXPBackground, seaBackground;
    @FXML
    private MenuItem easyButton, normalButton, hardButton, hellButton, insaneButton;
    @FXML
    private MenuItem easyTime, normalTime, hardTime, hellTime, insaneTime;
    @FXML
    private MenuItem turnOff, mirage, touchedByTheSky;
    @FXML
    private VBox vBox;
    @FXML
    private MenuBar menuBar;

    private final UserDAOImpl userDAO = new UserDAOImpl();

    private static final int GRID_SIZE = 4;
    private GridNumbers gridNumbers = new GridNumbers();

    private MediaPlayer mediaPlayer;
    private Media media;

    private Timeline timeline;
    private int timeSeconds;
    private boolean ifRunning = false;
    private Mode mode = Mode.NORMAL_GOAL;

    public void initialize() {

        vBox.minHeightProperty().bind(pane.heightProperty());
        vBox.minWidthProperty().bind(pane.widthProperty());
        menuBar.minWidthProperty().bind(vBox.widthProperty());
    }

    public void initGridPane() {

        this.gridNumbers = new GridNumbers();
        gridNumbers.initGridNumbers();
        fillNumbersIntoGridPane();

        stepLabel.setText(String.valueOf(gridNumbers.getStep()));
        scoreLabel.setText(String.valueOf(gridNumbers.getScore()));
        setHighestScoreLabel();

        switch (mode){
            case EASY_GOAL:
                gridNumbers.setGoal(1024);
                goalMode();
                break;
            case NORMAL_GOAL:
                gridNumbers.setGoal(2048);
                goalMode();
                break;
            case HARD_GOAL:
                gridNumbers.setGoal(4096);
                goalMode();
                break;
            case HELL_GOAL:
                gridNumbers.setGoal(8192);
                goalMode();
                break;
            case INSANE_GOAL:
                gridNumbers.setGoal(16384);
                goalMode();
                break;
            case EASY_TIME:
                timeSeconds = 600;
                timeMode();
                break;
            case NORMAL_TIME:
                timeSeconds = 300;
                timeMode();
                break;
            case HARD_TIME:
                timeSeconds = 180;
                timeMode();
                break;
            case HELL_TIME:
                timeSeconds = 60;
                timeMode();
                break;
            case INSANE_TIME:
                timeSeconds = 30;
                timeMode();
                break;
        }

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
                int num = Integer.parseInt(label.getText());
                switch (num) {
                    case 0:
                        label.setText(" ");
                        label.setStyle("-fx-background-color:#CAC1B4;-fx-border-width:5px;");
                        break;
                    case 2:
                        label.setStyle("-fx-background-color:#ECE4DA;-fx-text-fill:#756E65;-fx-border-width:5px;");
                        break;
                    case 4:
                        label.setStyle("-fx-background-color:#EBE1C9;-fx-text-fill:#756E65;-fx-border-width:5px;");
                        break;
                    case 8:
                        label.setStyle("-fx-background-color:#E9B27B;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 16:
                        label.setStyle("-fx-background-color:#E89766;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 32:
                        label.setStyle("-fx-background-color:#E77C61;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 64:
                        label.setStyle("-fx-background-color:#E5603F;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 128:
                        label.setStyle("-fx-background-color:#E7CF74;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 256:
                        label.setStyle("-fx-background-color:#E6CB63;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 512:
                        label.setStyle("-fx-background-color:#E6C851;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 1024:
                        label.setStyle("-fx-background-color:#E5C441;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 2048:
                        label.setStyle("-fx-background-color:#ECC400;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    default:
                        label.setStyle("-fx-background-color:#FF2021;-fx-text-fill:#F8F6F2;");
                }

            }
        }
    }

    public void rightStep() {
        gridNumbers.right();
        afterStep();
    }

    public void leftStep() {
        gridNumbers.left();
        afterStep();
    }

    public void downStep() {
        gridNumbers.down();
        afterStep();
    }

    public void upStep() {
        gridNumbers.up();
        afterStep();
    }

    public void afterStep() {
        fillNumbersIntoGridPane();
        stepLabel.setText(String.valueOf(gridNumbers.getStep()));
        scoreLabel.setText(String.valueOf(gridNumbers.getScore()));
        setHighestScoreLabel();
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
        endLabel.setText("Game Over!");
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

    public void goalMode() {

        if (ifRunning) {
            timeline.stop();
        }
        modeLabel.setText("GOAL");
        goal.setText(String.valueOf(gridNumbers.getGoal()));

    }

    public void setGoal() {

        easyButton.setOnAction(event -> {
            mode = Mode.EASY_GOAL;
            initGridPane();
        });
        normalButton.setOnAction(event -> {
            mode = Mode.NORMAL_GOAL;
            initGridPane();
            initGridPane();
        });
        hardButton.setOnAction(event -> {
            mode = Mode.HARD_GOAL;
            initGridPane();
        });
        hellButton.setOnAction(event -> {
            mode = Mode.HARD_GOAL;
            initGridPane();
        });
        insaneButton.setOnAction(event -> {
            mode = Mode.INSANE_GOAL;
            initGridPane();
        });

    }

    public void timeMode() {

        if (ifRunning) {
            timeline.stop();
        }
        modeLabel.setText("TIME");
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timeSeconds--;
                    goal.setText(String.valueOf(timeSeconds));
                    if (timeSeconds <= 0 || gridNumbers.loseTheGame()) {
                        timeline.stop();
                        ifRunning = false;
                        loseTheGame();
                        gridNumbers.setIfGameEnd(true);
                    }
                })
        );
        ifRunning = true;

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setTime() {

        easyTime.setOnAction(event -> {
            mode = Mode.EASY_TIME;
            initGridPane();
        });
        normalTime.setOnAction(event -> {
            mode = Mode.NORMAL_TIME;
            initGridPane();
        });
        hardTime.setOnAction(event -> {
            mode = Mode.HARD_TIME;
            initGridPane();
        });
        hellTime.setOnAction(event -> {
            mode = Mode.HELL_TIME;
            initGridPane();
        });
        insaneTime.setOnAction(event -> {
            mode = Mode.INSANE_TIME;
            initGridPane();
        });

    }

    public void logOut() throws IOException {

        Main.changeView("/FXML/login.fxml");
        Main.stage.setTitle("2048/login");

    }

    public void aboutUs() throws IOException {
        Main.addView("/FXML/aboutUs.fxml", "About Us");
    }

    public void setHighestScoreLabel() {

        String username = usernameLabel.getText();
        int higherScore = Integer.parseInt(scoreLabel.getText()) > userDAO.getHighestScoreByUsername(username)
                ? Integer.parseInt(scoreLabel.getText()) : userDAO.getHighestScoreByUsername(username);
        userDAO.updateHighestScore(username, higherScore);
        highestScoreLabel.setText(String.valueOf(userDAO.getHighestScoreByUsername(username)));

    }

    public void rankingList() throws IOException {
        Main.addView("/FXML/rankingList.fxml", "Ranking List");
    }

}

enum Mode {
    EASY_GOAL, NORMAL_GOAL, HARD_GOAL, HELL_GOAL, INSANE_GOAL, EASY_TIME, NORMAL_TIME, HARD_TIME, HELL_TIME, INSANE_TIME
}

