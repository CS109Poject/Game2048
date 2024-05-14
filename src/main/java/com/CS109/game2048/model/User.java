package com.CS109.game2048.model;

public class User {
    private int id;
    private String username;
    private String password;
    private int highestScore;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public User(int id, String username, String password, int highestScore,String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.highestScore = highestScore;
        this.email=email;
    }

    public User() {
    }
}
