package com.CS109.game2048.repository.impl;

import com.CS109.game2048.model.User;
import com.CS109.game2048.repository.dao.UserDAO;
import com.CS109.game2048.util.ConnectionUtil;

import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {


    @Override
    public void createUser(User user) {

        String sql = "INSERT INTO new_table (username, password, highestScore) VALUES (?, ?, ?)";
        Object[] params = {user.getUsername(), user.getPassword(), 0};

        try {
            ConnectionUtil.executeUpdate(sql, params);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean ifUsernameExist(String username) {

        String sql = "SELECT username FROM new_table WHERE username = ?";
        Object[] params = {username};

        try {
            List<String> usernames = ConnectionUtil.executeQuery(sql, params, "username");
            return !usernames.isEmpty();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean ifPasswordCorrect(String username, String password) {

        String sql = "SELECT password FROM new_table WHERE username = ?";
        Object[] params = {username};
        try {
            List<String> passwords = ConnectionUtil.executeQuery(sql, params, "password");
            if (!passwords.isEmpty()) {
                return password.equals(passwords.get(0));
            } else {
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateHighestScore(String username, int highestScore) {

        String sql = "UPDATE new_table SET highestScore = ? WHERE username = ?";
        Object[] params = {highestScore, username};
        try {
            ConnectionUtil.executeUpdate(sql, params);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getHighestScoreByUsername(String username) {

        String sql = "SELECT highestScore FROM new_table WHERE username = ?";
        Object[] params = {username};
        try {
            List<String> highestScores = ConnectionUtil.executeQuery(sql, params, "highestScore");
            if (!highestScores.isEmpty()) {
                return Integer.parseInt(highestScores.get(0));
            } else {
                return 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changePassword(String username, String newPassword) {

        String sql = "UPDATE new_table SET password = ? WHERE username = ?";
        Object[] params = {newPassword, username};
        try {
            ConnectionUtil.executeUpdate(sql, params);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<String> getAllUsernames() {

        String sql = "SELECT username FROM new_table";
        try {
            return ConnectionUtil.executeQuery(sql, null, "username");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Integer> getAllHighestScores() {

        String sql = "SELECT highestScore FROM new_table";
        try {
            List<String> strings = ConnectionUtil.executeQuery(sql, null, "highestScore");
            return strings.stream().map(Integer::parseInt).toList();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
