package com.CS109.game2048.controller;

import com.CS109.game2048.main.Main;
import com.CS109.game2048.repository.dao.UserDAO;
import com.CS109.game2048.repository.impl.UserSQL;
import com.CS109.game2048.service.AI.AI;
import com.CS109.game2048.service.Grid;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

/**
 * @author Ruizhe Pang
 */

public class GameSceneController {

    /**
     * The components of FXML.
     */
    @FXML
    private GridPane gridPane;
    @FXML
    private Label stepLabel, scoreLabel, endLabel, goal, emailLabel, highestScoreLabel, modeLabel;
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
    @FXML
    private Button AIButton,up,right,left,down;

    /**
     * Create the connection to MySQL.
     */
    private final UserDAO userDAO = new UserSQL();

    /**
     * Init the 4*4 matrix to fill in the game numbers.
     */
    private static final int GRID_SIZE = 4;
    private Grid grid = new Grid();

    /**
     * The media player to play the background music.
     */
    private MediaPlayer mediaPlayer;
    private Media media;

    /**
     * The timeline is timing for the limit time mode.
     * The AITimeline is for controlling the time AI takes to take each step.
     * The ifTheTimelineRunning is for determining whether the timeline is running.
     */
    private Timeline timeline, AITimeline;
    private int timeSeconds;
    private boolean ifTheTimelineRunning = false;

    /**
     * The game provides ten modes totally, and the initial mode is NORMAL_GOAL mode which is 2048 goal.
     */
    private Mode mode = Mode.NORMAL_GOAL;

    /**
     * Add the listeners for buttons and menuItems.
     */
    public void initialize() {

        vBox.minHeightProperty().bind(pane.heightProperty());
        vBox.minWidthProperty().bind(pane.widthProperty());
        menuBar.minWidthProperty().bind(vBox.widthProperty());
    }

    /**
     *Start or restart a game.
     * Choose the mode depend on current mode.
     */
    public void newStart() {

        if (emailLabel.getText().equals("AI@AI.com")){
            AIButton.setVisible(true);
        }

        this.grid = new Grid();
        this.grid.initGridNumbers();
        fillNumbersIntoGridPane();

        stepLabel.setText(String.valueOf(grid.getStep()));
        scoreLabel.setText(String.valueOf(grid.getScore()));
        setHighestScoreLabel();

        switch (mode) {
            case EASY_GOAL:
                grid.setGoal(1024);
                goalMode();
                break;
            case NORMAL_GOAL:
                grid.setGoal(2048);
                goalMode();
                break;
            case HARD_GOAL:
                grid.setGoal(4096);
                goalMode();
                break;
            case HELL_GOAL:
                grid.setGoal(8192);
                goalMode();
                break;
            case INSANE_GOAL:
                grid.setGoal(16384);
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

    /**
     * Fill the numbers into the labels of grid pane.
     */
    public void fillNumbersIntoGridPane() {

        int[][] numbers = this.grid.getMatrix();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {

                String s = String.valueOf(numbers[row][col]);

                Label label = (Label) gridPane.getChildren().get(row * 4 + col);
                label.setText(s);

            }
        }
        formatStyle();

    }

    /**
     * Format the background color, font size, font color of the labels depend on the numbers in them.
     */
    public void formatStyle() {

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
                        label.setStyle("-fx-background-color:#E5C441;-fx-text-fill:#F8F6F2;-fx-border-width:5px;-fx-font-size:28px;-fx-font-weight:Bold;");
                        break;
                    case 2048:
                        label.setStyle("-fx-background-color:#ECC400;-fx-text-fill:#F8F6F2;-fx-border-width:5px;-fx-font-size:28px;-fx-font-weight:Bold;");
                        break;
                    default:
                        label.setStyle("-fx-background-color:#FF2021;-fx-text-fill:#F8F6F2;-fx-border-width:5px;-fx-font-size:28px;-fx-font-weight:Bold;");
                }

            }
        }
    }

    /**
     * Execute action of right.
     */
    public void moveRight() {
        grid.right();
        afterMove();
    }

    /**
     * Execute action of left.
     */
    public void moveLeft() {
        grid.left();
        afterMove();
    }

    /**
     * Execute action of down.
     */
    public void moveDown() {
        grid.down();
        afterMove();
    }

    /**
     * Execute action of up.
     */
    public void moveUp() {
        grid.up();
        afterMove();
    }

    /**
     * Determine whether lose the game or win.
     * Update the highest score of current user.
     */
    public void afterMove() {
        fillNumbersIntoGridPane();
        stepLabel.setText(String.valueOf(grid.getStep()));
        scoreLabel.setText(String.valueOf(grid.getScore()));
        setHighestScoreLabel();
        if (grid.lose()) {
            lose();
        }
        if (grid.win()) {
            win();
        }
    }

    /**
     * Control moving by keyboard.
     * @param event W,A,S,D or UP,LEFT,DOWN,RIGHT
     */
    public void onKeyPressed(KeyEvent event) {

        switch (event.getCode()) {
            case RIGHT, D:
                moveRight();
                break;
            case LEFT, A:
                moveLeft();
                break;
            case DOWN, S:
                moveDown();
                break;
            case UP, W:
                moveUp();
                break;
            default:
                break;
        }

    }

    /**
     * If lose the game, show the endPane with context "Game Over!".
     */
    public void lose() {
        endLabel.setText("Game Over!");
        endPane.setVisible(true);
        endPane.setOpacity(1);
        gridPane.setOpacity(0.2);
    }

    /**
     * If win the game, show the endPane with the context "You Win!".
     */
    public void win() {
        endLabel.setText("You Win!");
        endPane.setVisible(true);
        endPane.setOpacity(1);
        gridPane.setOpacity(0.2);
    }

    /**
     * Set the background of the game scene.
     */
    public void setBackground() {

        Image brother = new Image(String.valueOf(getClass().getResource("/image/brother.jpg")));
        Image windowsXP = new Image(String.valueOf(getClass().getResource("/image/windowsXP.png")));
        Image sea = new Image(String.valueOf(getClass().getResource("/image/sea.png")));

        emptyBackground.setOnAction(event -> background.setImage(null));
        brotherBackground.setOnAction(event -> background.setImage(brother));
        windowsXPBackground.setOnAction(event -> background.setImage(windowsXP));
        seaBackground.setOnAction(event -> background.setImage(sea));


    }

    /**
     * Set the background music.
     */
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

    /**
     *Initialize the goal mode.
     */
    public void goalMode() {

        if (ifTheTimelineRunning) {
            timeline.stop();
        }
        modeLabel.setText("GOAL");
        goal.setText(String.valueOf(grid.getGoal()));

    }

    /**
     * Switch the game mode to specified goal mode.
     */
    public void setGoal() {

        easyButton.setOnAction(event -> {
            mode = Mode.EASY_GOAL;
            newStart();
        });
        normalButton.setOnAction(event -> {
            mode = Mode.NORMAL_GOAL;
            newStart();
            newStart();
        });
        hardButton.setOnAction(event -> {
            mode = Mode.HARD_GOAL;
            newStart();
        });
        hellButton.setOnAction(event -> {
            mode = Mode.HARD_GOAL;
            newStart();
        });
        insaneButton.setOnAction(event -> {
            mode = Mode.INSANE_GOAL;
            newStart();
        });

    }

    /**
     * Initialize the time mode.
     */
    public void timeMode() {

        if (ifTheTimelineRunning) {
            timeline.stop();
        }
        modeLabel.setText("TIME");
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timeSeconds--;
                    goal.setText(String.valueOf(timeSeconds));
                    if (timeSeconds <= 0 || grid.lose()) {
                        timeline.stop();
                        ifTheTimelineRunning = false;
                        lose();
                        grid.setIfGameEnd(true);
                    }
                })
        );
        ifTheTimelineRunning = true;

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Switch the game mode to specified time mode.
     */
    public void setTime() {

        easyTime.setOnAction(event -> {
            mode = Mode.EASY_TIME;
            newStart();
        });
        normalTime.setOnAction(event -> {
            mode = Mode.NORMAL_TIME;
            newStart();
        });
        hardTime.setOnAction(event -> {
            mode = Mode.HARD_TIME;
            newStart();
        });
        hellTime.setOnAction(event -> {
            mode = Mode.HELL_TIME;
            newStart();
        });
        insaneTime.setOnAction(event -> {
            mode = Mode.INSANE_TIME;
            newStart();
        });

    }

    /**
     * Exit the game scene and turn to login scene.
     */
    public void logOut() throws IOException {

        Main.changeView("/FXML/login.fxml");
        Main.stage.setTitle("2048/login");

    }

    /**
     * Pop up the about-us interface.
     */
    public void aboutUs() throws IOException {
        Main.addView("/FXML/aboutUs.fxml", "About Us");
    }

    /**
     * Update the highest score of current user.
     */
    public void setHighestScoreLabel() {

        String username = emailLabel.getText();
        int higherScore = Math.max(Integer.parseInt(scoreLabel.getText()), userDAO.getHighestScoreByEmail(username));
        userDAO.updateHighestScore(username, higherScore);
        highestScoreLabel.setText(String.valueOf(userDAO.getHighestScoreByEmail(username)));

    }

    /**
     * Pop up the ranking-list interface.
     */
    public void rankingList() throws IOException {
        Main.addView("/FXML/rankingList.fxml", "Ranking List");
    }

    /**
     * Press to start the AI mode.
     * Repress to end the AI mode.
     */
    public void AIMode() {
        if (AITimeline != null && AITimeline.getStatus() == Animation.Status.RUNNING) {
            AITimeline.pause();
            return;
        }
        AITimeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            AI ai = new AI(grid);
            int move = ai.getBestMove(5);
            switch (move) {
                case 0:
                    moveUp();
                    break;
                case 1:
                    moveRight();
                    break;
                case 2:
                    moveDown();
                    break;
                case 3:
                    moveLeft();
                    break;
            }
        }));
        AITimeline.setCycleCount(Animation.INDEFINITE);
        //AITimeline.setCycleCount(1);
        AITimeline.play();
    }

}

enum Mode {
    EASY_GOAL, NORMAL_GOAL, HARD_GOAL, HELL_GOAL, INSANE_GOAL, EASY_TIME, NORMAL_TIME, HARD_TIME, HELL_TIME, INSANE_TIME
}

