package com.CS109.game2048.controller;

import com.CS109.game2048.main.Main;
import com.CS109.game2048.repository.dao.UserDAO;
import com.CS109.game2048.repository.impl.UserSQL;
import com.CS109.game2048.service.AI.AI;
import com.CS109.game2048.service.Grid;
import com.CS109.game2048.util.Mode;
import com.CS109.game2048.util.SaveGameUtil;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Ruizhe Pang
 */

public class GameSceneController {

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
    private boolean ifTheTimelineRunning = false;

    /**
     * The components of FXML.
     */
    @FXML
    private Button AIButton;

    @FXML
    private ImageView background;

    @FXML
    private MenuItem brotherBackground;

    @FXML
    private Button down;

    @FXML
    private MenuItem easyButton;

    @FXML
    private MenuItem easyTime;

    @FXML
    private Label emailLabel;

    @FXML
    private MenuItem emptyBackground;

    @FXML
    private Label endLabel;

    @FXML
    private AnchorPane endPane;

    @FXML
    private Label goalLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private MenuItem hardButton;

    @FXML
    private MenuItem hardTime;

    @FXML
    private MenuItem hellButton;

    @FXML
    private MenuItem hellTime;

    @FXML
    private Label highestScoreLabel;

    @FXML
    private MenuItem insaneButton;

    @FXML
    private MenuItem insaneTime;

    @FXML
    private Button left;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem mirage;

    @FXML
    private Label modeLabel;

    @FXML
    private MenuItem normalButton;

    @FXML
    private MenuItem normalTime;

    @FXML
    private Pane pane;

    @FXML
    private Pane mainPane;

    @FXML
    private Button right;

    @FXML
    private Label scoreLabel;

    @FXML
    private MenuItem seaBackground;

    @FXML
    private Button start;

    @FXML
    private Label stepLabel;

    @FXML
    private MenuItem touchedByTheSky;

    @FXML
    private MenuItem turnOff;

    @FXML
    private Button up;

    @FXML
    private MenuItem windowsXPBackground;

    @FXML
    private Button stepBackButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button loadButton;

    /**
     * Add the listeners for buttons and menuItems.
     */
    @FXML
    void initialize() {

        menuBar.minWidthProperty().bind(pane.widthProperty());
        mainPane.layoutXProperty().bind(pane.widthProperty().subtract(mainPane.widthProperty()).divide(2));
        mainPane.layoutYProperty().bind(pane.heightProperty().subtract(mainPane.heightProperty()).divide(2));
        //stepBackButton.setFocusTraversable(false);
    }

    /**
     * Start or restart a game.
     * Choose the mode depend on current mode.
     */
    @FXML
    void newStart() {

        if (emailLabel.getText().equals("AI@AI.com")) {
            AIButton.setVisible(true);
        }
        if (!emailLabel.getText().equals("Guest")) {
            saveButton.setVisible(true);
            loadButton.setVisible(true);
        }

        Mode mode = this.grid.mode;

        this.grid = new Grid();
        this.grid.mode = mode;
        this.grid.setIfStepBack(false);

        this.grid.initGridNumbers();
        afterOperate();

        switch (this.grid.mode) {
            case EASY_GOAL:
                this.grid.setGoal(1024);
                goalMode();
                break;
            case NORMAL_GOAL:
                this.grid.setGoal(2048);
                goalMode();
                break;
            case HARD_GOAL:
                this.grid.setGoal(4096);
                goalMode();
                break;
            case HELL_GOAL:
                this.grid.setGoal(8192);
                goalMode();
                break;
            case INSANE_GOAL:
                this.grid.setGoal(16384);
                goalMode();
                break;
            case EASY_TIME:
                this.grid.timeSeconds = 600;
                timeMode();
                break;
            case NORMAL_TIME:
                this.grid.timeSeconds = 300;
                timeMode();
                break;
            case HARD_TIME:
                this.grid.timeSeconds = 180;
                timeMode();
                break;
            case HELL_TIME:
                this.grid.timeSeconds = 60;
                timeMode();
                break;
            case INSANE_TIME:
                this.grid.timeSeconds = 30;
                timeMode();
                break;
        }

        endPane.setVisible(false);
        gridPane.setOpacity(1.0);

    }

    /**
     * Execute action of right.
     */
    @FXML
    void moveRight() {
        this.grid.right();
        afterOperate();
    }

    /**
     * Execute action of left.
     */
    @FXML
    void moveLeft() {
        this.grid.left();
        afterOperate();
    }

    /**
     * Execute action of down.
     */
    @FXML
    void moveDown() {
        this.grid.down();
        afterOperate();
    }

    /**
     * Execute action of up.
     */
    @FXML
    void moveUp() {
        this.grid.up();
        afterOperate();
    }

    @FXML
    void stepBack() {
        if (this.grid.getParentGrid() != null) {
            this.grid = this.grid.getParentGrid();
            afterOperate();
            this.grid.setIfStepBack(true);
        }
    }

    /**
     * Control moving by keyboard.
     *
     * @param event W,A,S,D or UP,LEFT,DOWN,RIGHT
     */
    @FXML
    void onKeyPressed(KeyEvent event) {

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
     * Set the background of the game scene.
     */
    @FXML
    void setBackground() {

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
    @FXML
    void setMusic() {


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
     * Switch the game mode to specified goal mode.
     */
    @FXML
    void setGoal() {

        easyButton.setOnAction(event -> {
            this.grid.mode = Mode.EASY_GOAL;
            newStart();
        });
        normalButton.setOnAction(event -> {
            this.grid.mode = Mode.NORMAL_GOAL;
            newStart();
            newStart();
        });
        hardButton.setOnAction(event -> {
            this.grid.mode = Mode.HARD_GOAL;
            newStart();
        });
        hellButton.setOnAction(event -> {
            this.grid.mode = Mode.HARD_GOAL;
            newStart();
        });
        insaneButton.setOnAction(event -> {
            this.grid.mode = Mode.INSANE_GOAL;
            newStart();
        });

    }

    /**
     * Switch the game mode to specified time mode.
     */
    @FXML
    void setTime() {

        easyTime.setOnAction(event -> {
            this.grid.mode = Mode.EASY_TIME;
            newStart();
        });
        normalTime.setOnAction(event -> {
            this.grid.mode = Mode.NORMAL_TIME;
            newStart();
        });
        hardTime.setOnAction(event -> {
            this.grid.mode = Mode.HARD_TIME;
            newStart();
        });
        hellTime.setOnAction(event -> {
            this.grid.mode = Mode.HELL_TIME;
            newStart();
        });
        insaneTime.setOnAction(event -> {
            this.grid.mode = Mode.INSANE_TIME;
            newStart();
        });

    }

    /**
     * Exit the game scene and turn to login scene.
     */
    @FXML
    void logOut() throws IOException {
        Main.changeView("/FXML/login.fxml");
        Main.stage.setTitle("2048/login");
    }

    /**
     * Pop up the about-us interface.
     */
    @FXML
    void aboutUs() throws IOException {
        Main.addView("/FXML/aboutUs.fxml", "About Us");
    }


    /**
     * Pop up the ranking-list interface.
     */
    @FXML
    void rankingList() throws IOException {
        Main.addView("/FXML/rankingList.fxml", "Ranking List");
    }

    /**
     * Press to start the AI mode.
     * Repress to end the AI mode.
     */
    @FXML
    void AIMode() {
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

    @FXML
    void saveGame() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Game");
        dialog.setHeaderText("Please enter the file name you want to save.");
        dialog.setContentText("File Name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(fileName -> {
            SaveGameUtil.saveGame(this.grid, fileName, emailLabel.getText());
        });
    }

    @FXML
    void loadGame() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load Game");
        dialog.setHeaderText("Please enter the file name you want to load.");
        dialog.setContentText("File Name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(fileName -> {

            if (SaveGameUtil.loadGame(fileName, emailLabel.getText()) != null) {
                this.grid = SaveGameUtil.loadGame(fileName, emailLabel.getText());
                afterOperate();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The file is invalid!");
                alert.showAndWait();
            }
        });
    }

    @FXML
    void changePassword() throws IOException {
        Main.addView("/FXML/changePassword.fxml", "Change Password");
    }

    /**
     * Fill the numbers into the labels of grid pane.
     */
    private void fillNumbersIntoGridPane() {

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
    private void formatStyle() {

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
     * Determine whether lose the game or win.
     * Update the highest score of current user.
     */
    private void afterOperate() {
        fillNumbersIntoGridPane();
        stepLabel.setText(String.valueOf(this.grid.getStep()));
        scoreLabel.setText(String.valueOf(this.grid.getScore()));
        setHighestScoreLabel();
        if (this.grid.lose()) {
            lose();
        } else if (this.grid.win()) {
            win();
        } else {
            endPane.setVisible(false);
            endLabel.setText("");
            gridPane.setOpacity(1);
        }

    }

    /**
     * If lose the game, show the endPane with context "Game Over!".
     */
    private void lose() {
        endLabel.setText("Game Over!");
        endPane.setVisible(true);
        endPane.setOpacity(1);
        gridPane.setOpacity(0.2);
    }

    /**
     * If win the game, show the endPane with the context "You Win!".
     */
    private void win() {
        endLabel.setText("You Win!");
        endPane.setVisible(true);
        endPane.setOpacity(1);
        gridPane.setOpacity(0.2);
    }

    /**
     * Initialize the goal mode.
     */
    private void goalMode() {

        if (ifTheTimelineRunning) {
            timeline.stop();
        }
        modeLabel.setText("GOAL");
        goalLabel.setText(String.valueOf(grid.getGoal()));

    }

    /**
     * Initialize the time mode.
     */
    private void timeMode() {

        if (ifTheTimelineRunning) {
            timeline.stop();
        }
        modeLabel.setText("TIME");
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    this.grid.timeSeconds--;
                    goalLabel.setText(String.valueOf(this.grid.timeSeconds));
                    if (this.grid.timeSeconds <= 0 || grid.lose()) {
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
     * Update the highest score of current user.
     */
    private void setHighestScoreLabel() {
        if (!this.grid.isIfStepBack()) {
            String email = emailLabel.getText();
            int higherScore = Math.max(Integer.parseInt(scoreLabel.getText()), userDAO.getHighestScoreByEmail(email));
            userDAO.updateHighestScore(email, higherScore);
            highestScoreLabel.setText(String.valueOf(userDAO.getHighestScoreByEmail(email)));
        }
    }

}
