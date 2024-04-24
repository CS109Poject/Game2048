package com.CS109.game2048.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.lang.reflect.Method;
import java.util.Random;

import com.CS109.game2048.engine.*;

public class GameSceneController {
    @FXML
    private GridPane gridPane;
    @FXML
    private Label stepLabel;
    @FXML
    private Label scoreLabel;

    private static final int GRID_SIZE = 4;
    GridNumbers gridNumbers = new GridNumbers();

    public void initGridPane() {

        this.gridNumbers = new GridNumbers();
        gridNumbers.initGridNumbers();
        fillNumbersIntoGridPane();
        gridNumbers.setStep(0);
        gridNumbers.setScore(0);
        stepLabel.setText(String.valueOf(gridNumbers.getStep()));
        scoreLabel.setText(String.valueOf(gridNumbers.getScore()));

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
                        label.setStyle("-fx-background-color:white;");
                        break;
                    case 2:
                        label.setStyle("-fx-background-color:orange;");
                        break;
                    case 4:
                        label.setStyle("-fx-background-color:red;");
                        break;
                    case 8:
                        label.setStyle("-fx-background-color:coral;");
                        break;
                    case 16:
                        label.setStyle("-fx-background-color:green;");
                        break;
                    case 32:
                        label.setStyle("-fx-background-color:blue;");
                        break;
                    case 64:
                        label.setStyle("-fx-background-color:black;");
                        break;
                    case 128:
                        label.setStyle("-fx-background-color:pink;");
                        break;
                    case 256:
                        label.setStyle("-fx-background-color:brown;");
                        break;
                    case 512:
                        label.setStyle("-fx-background-color:purple;");
                        break;
                    case 1024:
                        label.setStyle("-fx-background-color:cyan;");
                        break;
                    case 2048:
                        label.setStyle("-fx-background-color:maroon;");
                        break;
                    default:
                        label.setStyle("-fx-background-color:lime;");
                }

            }
        }
    }

    public void rightStep(){

        gridNumbers.right();
        fillNumbersIntoGridPane();
        stepLabel.setText(String.valueOf(gridNumbers.getStep()));
        scoreLabel.setText(String.valueOf(gridNumbers.getScore()));

    }

    public void leftStep(){

        gridNumbers.left();
        fillNumbersIntoGridPane();
        stepLabel.setText(String.valueOf(gridNumbers.getStep()));
        scoreLabel.setText(String.valueOf(gridNumbers.getScore()));

    }

    public void downStep(){

        gridNumbers.down();
        fillNumbersIntoGridPane();
        stepLabel.setText(String.valueOf(gridNumbers.getStep()));
        scoreLabel.setText(String.valueOf(gridNumbers.getScore()));

    }

    public void upStep(){

        gridNumbers.up();
        fillNumbersIntoGridPane();
        stepLabel.setText(String.valueOf(gridNumbers.getStep()));
        scoreLabel.setText(String.valueOf(gridNumbers.getScore()));

    }

}

