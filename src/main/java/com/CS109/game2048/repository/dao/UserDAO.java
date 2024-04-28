package com.CS109.game2048.repository.dao;

import com.CS109.game2048.model.User;

import java.util.List;

public interface UserDAO {
    void createUser(User user);
    boolean ifUsernameExist(String username);
    boolean ifPasswordCorrect(String username,String password);
    void updateHighestScore(String Username,int highestScore);
    int getHighestScore(String username);
}
