package com.CS109.game2048.repository.impl;

import com.CS109.game2048.model.User;
import com.CS109.game2048.repository.dao.UserDAO;
import com.CS109.game2048.util.ConnectionUtil;

import java.sql.SQLException;
import java.util.List;

public class UserSQL implements UserDAO {


    @Override
    public void createUser(User user) {

        String sql = "INSERT INTO users (username, password, highestScore, email) VALUES (?, ?, ?, ?)";
        Object[] params = {user.getUsername(), user.getPassword(), 0, user.getEmail()};

        try {
            ConnectionUtil.executeUpdate(sql, params);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean ifEmailExist(String email) {

        String sql = "SELECT email FROM users WHERE email = ?";
        Object[] params = {email};

        try {
            List<String> emails = ConnectionUtil.executeQuery(sql, params, "email");
            return !emails.isEmpty();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean ifPasswordCorrect(String email, String password) {

        String sql = "SELECT password FROM users WHERE email = ?";
        Object[] params = {email};
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
    public void updateHighestScore(String email, int highestScore) {

        String sql = "UPDATE users SET highestScore = ? WHERE email = ?";
        Object[] params = {highestScore, email};
        try {
            ConnectionUtil.executeUpdate(sql, params);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getHighestScoreByEmail(String email) {

        String sql = "SELECT highestScore FROM users WHERE email = ?";
        Object[] params = {email};
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
    public void changePassword(String email, String newPassword) {

        String sql = "UPDATE users SET password = ? WHERE email = ?";
        Object[] params = {newPassword, email};
        try {
            ConnectionUtil.executeUpdate(sql, params);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<String> getAllEmails() {

        String sql = "SELECT email FROM users";
        try {
            return ConnectionUtil.executeQuery(sql, null, "email");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Integer> getAllHighestScores() {

        String sql = "SELECT highestScore FROM users";
        try {
            List<String> strings = ConnectionUtil.executeQuery(sql, null, "highestScore");
            return strings.stream().map(Integer::parseInt).toList();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
