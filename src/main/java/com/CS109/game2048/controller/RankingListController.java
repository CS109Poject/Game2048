package com.CS109.game2048.controller;

import com.CS109.game2048.repository.dao.UserDAO;
import com.CS109.game2048.repository.impl.UserSQL;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RankingListController {

    private final UserDAO userDAO = new UserSQL();
    private List<Integer> allHighestScores;
    private List<String> allUsernames;

    @FXML
    private Label firstUsernameLabel, secondUsernameLabel, thirdUsernameLabel, firstScoreLabel, secondScoreLabel, thirdScoreLabel;


    @FXML
    void initialize() {
        initializeScores();
        rank();
        try {
            firstUsernameLabel.setText(allUsernames.get(0));
            secondUsernameLabel.setText(allUsernames.get(1));
            thirdUsernameLabel.setText(allUsernames.get(2));
            firstScoreLabel.setText(String.valueOf(allHighestScores.get(0)));
            secondScoreLabel.setText(String.valueOf(allHighestScores.get(1)));
            thirdScoreLabel.setText(String.valueOf(allHighestScores.get(2)));
        }catch (ArrayIndexOutOfBoundsException e){

        }
    }

    private void initializeScores() {

        allHighestScores = userDAO.getAllHighestScores();
        allUsernames = userDAO.getAllEmails();

    }

    private void rank() {

        List<String> rankedUsername = new ArrayList<>();
        List<Integer> rankedScores = new ArrayList<>(allHighestScores);
        rankedScores.sort(Collections.reverseOrder());

        for (Integer rankedScore : rankedScores) {
            for (int j = 0; j < allHighestScores.size(); j++) {
                if (Objects.equals(rankedScore, allHighestScores.get(j)) && !rankedUsername.contains(allUsernames.get(j))) {
                    rankedUsername.add(allUsernames.get(j));
                    break;
                }
            }
        }

        this.allHighestScores = new ArrayList<>(rankedScores);
        this.allUsernames = new ArrayList<>(rankedUsername);

    }

}
