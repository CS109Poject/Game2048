package com.CS109.game2048.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.Random;

public class GameSceneController {
    @FXML
    private GridPane gridPane;
    @FXML
    private Label stepLabel;
    @FXML
    private Label scoreLabel;

    private static final int GRID_SIZE = 4;

    public void initGridPane() {

        Random random = new Random();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {

                int num = random.nextInt(3);
                if(num==1) num = 0;
                String s = String.valueOf(num);

                StackPane cell = new StackPane();
                cell.setPrefSize(100, 100);
                cell.setStyle("-fx-background-color:white;-fx-border-color:black;");

                Label label = new Label();
                label.setText(s);
                label.setStyle("-fx-font-size:32px;");
                cell.getChildren().add(label);
                gridPane.add(cell, col, row);

            }
        }

    }
}
