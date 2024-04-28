package com.CS109.game2048.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class RankingListController {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost:3306/test?" +
            "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "MySQL190504";

    @FXML
    private Label firstUsernameLabel, secondUsernameLabel, thirdUsernameLabel, firstScoreLabel, secondScoreLabel, thirdScoreLabel;

    private ArrayList<Integer> allHighestScores;
    private ArrayList<String> allUsernames;

    public void initialize() {
        initializeScores();
        rank();
        firstUsernameLabel.setText(allUsernames.get(0));
        secondUsernameLabel.setText(allUsernames.get(1));
        thirdUsernameLabel.setText(allUsernames.get(2));
        firstScoreLabel.setText(String.valueOf(allHighestScores.get(0)));
        secondScoreLabel.setText(String.valueOf(allHighestScores.get(1)));
        thirdScoreLabel.setText(String.valueOf(allHighestScores.get(2)));
    }

    public void initializeScores() {

        allHighestScores = new ArrayList<>();
        allUsernames = new ArrayList<>();

        Connection connection = null;
        Statement stmt = null;
        ResultSet rs;
        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            String selectQuery = "SELECT username, highestScore FROM new_table";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectQuery);

            while (rs.next()) {
                String username = rs.getString("username");
                int highestScore = rs.getInt("highestScore");
                allUsernames.add(username);
                allHighestScores.add(highestScore);

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            }
            try {
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void rank() {

        ArrayList rankedUsername = new ArrayList(allUsernames);
        ArrayList rankedScores = new ArrayList<>(allHighestScores);
        rankedScores.sort(Collections.reverseOrder());

        for (int i = 0; i < rankedScores.size(); i++) {
            for (int j = 0; j < allHighestScores.size(); j++) {
                if (rankedScores.get(i) == allHighestScores.get(j) && !rankedUsername.contains(allUsernames.get(j))) {
                    rankedUsername.add(allUsernames.get(j));
                    break;
                }
            }
        }

        this.allHighestScores = new ArrayList<>(rankedScores);
        this.allUsernames = new ArrayList<>(rankedUsername);

    }

}
