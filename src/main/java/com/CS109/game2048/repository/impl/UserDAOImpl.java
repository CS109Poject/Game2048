package com.CS109.game2048.repository.impl;

import com.CS109.game2048.model.User;
import com.CS109.game2048.repository.dao.UserDAO;
import com.CS109.game2048.util.ConnectionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public void createUser(User user) {

        String sql = "INSERT INTO new_table (username, password, highestScore) VALUES (?, ?, 0)";
        Object[] params = {user.getUsername(), user.getPassword()};

        try {
            ConnectionUtil.executeUpdate(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean ifUsernameExist(String username) {

        String sql = "SELECT COUNT(*) FROM new_table WHERE username = ?";
        Object[] params = {username};

        try {
            ResultSet rs = ConnectionUtil.executeQuery(sql, params);
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public boolean ifPasswordCorrect(String username, String password) {

        String sql = "SELECT password FROM new_table WHERE username = ?";
        Object[] params = {username};
        try {
            ResultSet rs = ConnectionUtil.executeQuery(sql, params);
            if (rs.next()) {
                String pass = rs.getString("password");
                return password.equals(pass);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public void updateHighestScore(String username, int highestScore) {

        String sql = "UPDATE new_table SET highestScore = ? WHERE username = ?";
        Object[] params = {highestScore, username};
        try{
            ConnectionUtil.executeUpdate(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getHighestScore(String username) {

        String sql = "SELECT highestScore FROM new_table WHERE username = ?";
        Object[] params = {username};
        try {
            ResultSet rs = ConnectionUtil.executeQuery(sql, params);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


}
