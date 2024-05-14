package com.CS109.game2048.repository.dao;

import com.CS109.game2048.model.User;

import java.util.List;

public interface UserDAO {
    void createUser(User user);
    boolean ifEmailExist(String email);
    boolean ifPasswordCorrect(String email,String password);
    void updateHighestScore(String email,int highestScore);
    int getHighestScoreByEmail(String email);
    void changePassword(String email,String newPassword);
    List<String> getAllEmails();
    List<Integer> getAllHighestScores();
}
