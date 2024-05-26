package com.CS109.game2048.controller;

import com.CS109.game2048.net.Client;
import com.CS109.game2048.net.Server;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.BindException;

public class BattleModeController {

    private Client client;
    private Server server;
    private final int GRID_SIZE = 4;
    private boolean ifGameStart = false;

    @FXML
    private GridPane gridPane1;

    @FXML
    private GridPane gridPane2;

    @FXML
    private Button issueChallengeButton;

    @FXML
    private Button searchChallengeButton;

    @FXML
    private Label enemyScoreLabel;

    @FXML
    private Label enemyStepLabel;

    @FXML
    private Label myScoreLabel;

    @FXML
    private Label myStepLabel;

    @FXML
    private Pane myLosePane;

    @FXML
    private Pane enemyLosePane;

    @FXML
    void issueChallenge(ActionEvent event) {
        Thread serverThread = new Thread(() -> {
            try {
                this.server = new Server();
            } catch (Exception e) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Server startup failed.");
                    alert.showAndWait();
                });
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();

        Thread clientThread = new Thread(() -> {
            try {
                this.client = new Client(this);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Client startup failed.");
                alert.showAndWait();
            }
        });
        clientThread.setDaemon(true);
        clientThread.start();
    }

    @FXML
    void searchChallenge(ActionEvent event) {
        Thread clientThread = new Thread(() -> {
            try {
                this.client = new Client(this);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Client startup failed.");
                alert.showAndWait();
            }
        });
        clientThread.setDaemon(true);
        clientThread.start();
    }

    @FXML
    void start() {
        this.client.sendMessage("start");
    }

    @FXML
    void move(KeyEvent event) {
        if (this.client.getMyGrid().isIfGameBegin()&&this.client.getMyGrid().isIfGameBegin()) {
            String message = null;

            switch (event.getCode()) {
                case RIGHT -> message = "right";
                case LEFT -> message = "left";
                case UP -> message = "up";
                case DOWN -> message = "down";
            }

            if (message != null && this.client.isConnectionState()) {
                this.client.sendMessage(message);

                //afterOperate();
            }
        }
    }

    @FXML
    void disconnect() {
        this.client.disconnect();
        this.server.closeServer();
    }

    public void afterOperate() {

        fillNumbersIntoGridPane();

        myStepLabel.setText(String.valueOf(this.client.getMyGrid().getStep()));
        myScoreLabel.setText(String.valueOf(this.client.getMyGrid().getScore()));

        enemyStepLabel.setText(String.valueOf(this.client.getEnemyGrid().getStep()));
        enemyScoreLabel.setText(String.valueOf(this.client.getEnemyGrid().getScore()));

        if (this.client.getMyGrid().lose()) {
            myLose();
        } else if (this.client.getEnemyGrid().lose()) {
            enemyLose();
        } else {
            myLosePane.setVisible(false);
            enemyLosePane.setVisible(false);
            gridPane1.setOpacity(1);
            gridPane2.setOpacity(1);
        }

    }

    public void myLose() {
        myLosePane.setVisible(true);
        gridPane1.setOpacity(0.2);
    }

    public void enemyLose() {
        enemyLosePane.setVisible(true);
        gridPane2.setOpacity(0.2);
    }

    public void fillNumbersIntoGridPane() {
        int[][] numbers1 = this.client.getMyGrid().getMatrix();
        int[][] numbers2 = this.client.getEnemyGrid().getMatrix();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {

                String s1 = String.valueOf(numbers1[row][col]);
                String s2 = String.valueOf(numbers2[row][col]);

                Label label1 = (Label) gridPane1.getChildren().get(row * 4 + col);
                Label label2 = (Label) gridPane2.getChildren().get(row * 4 + col);

                label1.setText(s1);
                label2.setText(s2);

                formatStyle();
            }
        }
    }

    private void formatStyle() {

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Label label1 = (Label) gridPane1.getChildren().get(row * 4 + col);
                Label label2 = (Label) gridPane2.getChildren().get(row * 4 + col);

                int num1 = Integer.parseInt(label1.getText());
                int num2 = Integer.parseInt(label2.getText());

                switch (num1) {
                    case 0:
                        label1.setText("0");
                        label1.setStyle("-fx-background-color:#CAC1B4;-fx-text-fill:#CAC1B4;-fx-border-width:5px;");
                        break;
                    case 2:
                        label1.setStyle("-fx-background-color:#ECE4DA;-fx-text-fill:#756E65;-fx-border-width:5px;");
                        break;
                    case 4:
                        label1.setStyle("-fx-background-color:#EBE1C9;-fx-text-fill:#756E65;-fx-border-width:5px;");
                        break;
                    case 8:
                        label1.setStyle("-fx-background-color:#E9B27B;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 16:
                        label1.setStyle("-fx-background-color:#E89766;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 32:
                        label1.setStyle("-fx-background-color:#E77C61;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 64:
                        label1.setStyle("-fx-background-color:#E5603F;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 128:
                        label1.setStyle("-fx-background-color:#E7CF74;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 256:
                        label1.setStyle("-fx-background-color:#E6CB63;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 512:
                        label1.setStyle("-fx-background-color:#E6C851;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 1024:
                        label1.setStyle("-fx-background-color:#E5C441;-fx-text-fill:#F8F6F2;-fx-border-width:5px;-fx-font-size:28px;-fx-font-weight:Bold;");
                        break;
                    case 2048:
                        label1.setStyle("-fx-background-color:#ECC400;-fx-text-fill:#F8F6F2;-fx-border-width:5px;-fx-font-size:28px;-fx-font-weight:Bold;");
                        break;
                    default:
                        label1.setStyle("-fx-background-color:#FF2021;-fx-text-fill:#F8F6F2;-fx-border-width:5px;-fx-font-size:28px;-fx-font-weight:Bold;");
                }
                switch (num2) {
                    case 0:
                        label2.setText("0");
                        label2.setStyle("-fx-background-color:#CAC1B4;-fx-text-fill:#CAC1B4;-fx-border-width:5px;");
                        break;
                    case 2:
                        label2.setStyle("-fx-background-color:#ECE4DA;-fx-text-fill:#756E65;-fx-border-width:5px;");
                        break;
                    case 4:
                        label2.setStyle("-fx-background-color:#EBE1C9;-fx-text-fill:#756E65;-fx-border-width:5px;");
                        break;
                    case 8:
                        label2.setStyle("-fx-background-color:#E9B27B;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 16:
                        label2.setStyle("-fx-background-color:#E89766;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 32:
                        label2.setStyle("-fx-background-color:#E77C61;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 64:
                        label2.setStyle("-fx-background-color:#E5603F;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 128:
                        label2.setStyle("-fx-background-color:#E7CF74;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 256:
                        label2.setStyle("-fx-background-color:#E6CB63;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 512:
                        label2.setStyle("-fx-background-color:#E6C851;-fx-text-fill:#F8F6F2;-fx-border-width:5px;");
                        break;
                    case 1024:
                        label2.setStyle("-fx-background-color:#E5C441;-fx-text-fill:#F8F6F2;-fx-border-width:5px;-fx-font-size:28px;-fx-font-weight:Bold;");
                        break;
                    case 2048:
                        label2.setStyle("-fx-background-color:#ECC400;-fx-text-fill:#F8F6F2;-fx-border-width:5px;-fx-font-size:28px;-fx-font-weight:Bold;");
                        break;
                    default:
                        label2.setStyle("-fx-background-color:#FF2021;-fx-text-fill:#F8F6F2;-fx-border-width:5px;-fx-font-size:28px;-fx-font-weight:Bold;");
                }
            }
        }
    }
}
