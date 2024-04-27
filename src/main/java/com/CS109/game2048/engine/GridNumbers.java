package com.CS109.game2048.engine;

import java.util.Random;

public class GridNumbers {
    private int[][] numbers = new int[4][4];
    private int step = 0;
    private int score = 0;
    private int goal = 2048;
    private boolean ifGameEnd = false;
    private int highestScore = 0;

    public GridNumbers() {
    }

    public GridNumbers(int[][] numbers) {
        this.numbers = numbers;
    }

    public int[][] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[][] numbers) {
        this.numbers = numbers;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score){
        this.score=score;
    }

    public int getGoal(){
        return this.goal;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public boolean getIfTheGameEnd() {
        return this.ifGameEnd;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void initGridNumbers() {
        this.numbers = generateANumber(this.numbers);
    }


    public static int[][] generateANumber(int[][] numbers) {

        Random random = new Random();
        int num = random.nextInt(1, 3) * 2;

        while (true) {

            int row = random.nextInt(4);
            int col = random.nextInt(4);

            if (numbers[row][col] == 0) {
                numbers[row][col] = num;
                break;

            }
        }
        return numbers;
    }

    public void right() {

        if (!ifGameEnd) {

            int[][] initialNumbers = generateInitialNumbers();

            for (int row = 0; row < 4; row++) {
                for (int col = 3; col >= 0; col--) {
                    for (int i = col - 1; i >= 0; i--) {

                        boolean ifOrtherNumbers = false;
                        if (numbers[row][i] != 0 && numbers[row][i] != numbers[row][col]) {
                            break;
                        }

                        if (!ifOrtherNumbers && numbers[row][col] == numbers[row][i]) {
                            numbers[row][col] = 2 * numbers[row][col];
                            this.score += numbers[row][col];
                            numbers[row][i] = 0;
                            break;
                        }
                    }
                }
            }

            for (int row = 0; row < 4; row++) {
                for (int col = 3; col >= 0; col--) {
                    if (numbers[row][col] == 0) {
                        for (int i = col - 1; i >= 0; i--) {
                            if (numbers[row][i] != 0) {
                                numbers[row][col] = numbers[row][i];
                                numbers[row][i] = 0;
                                break;
                            }
                        }
                    }
                }
            }

            if (ifMove(initialNumbers, this.numbers)) {
                this.numbers = generateANumber(this.numbers);
                this.step++;
            }
        }
    }

    public void left() {

        if (!ifGameEnd) {

            int[][] initialNumbers = generateInitialNumbers();

            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    for (int i = col + 1; i < 4; i++) {

                        boolean ifOrtherNumbers = false;
                        if (numbers[row][i] != 0 && numbers[row][i] != numbers[row][col]) {
                            break;
                        }

                        if (!ifOrtherNumbers && numbers[row][col] == numbers[row][i]) {
                            numbers[row][col] = 2 * numbers[row][col];
                            this.score += numbers[row][col];
                            numbers[row][i] = 0;
                            break;
                        }
                    }
                }
            }

            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    if (numbers[row][col] == 0) {
                        for (int i = col; i < 4; i++) {
                            if (numbers[row][i] != 0) {
                                numbers[row][col] = numbers[row][i];
                                numbers[row][i] = 0;
                                break;
                            }
                        }
                    }
                }
            }

            if (ifMove(initialNumbers, this.numbers)) {
                this.numbers = generateANumber(this.numbers);
                this.step++;
            }
        }
    }

    public void down() {

        if (!ifGameEnd) {

            int[][] initialNumbers = generateInitialNumbers();

            for (int col = 0; col < 4; col++) {
                for (int row = 3; row >= 0; row--) {
                    for (int i = row - 1; i >= 0; i--) {

                        boolean ifOrtherNumbers = false;
                        if (numbers[i][col] != 0 && numbers[i][col] != numbers[row][col]) {
                            break;
                        }

                        if (!ifOrtherNumbers && numbers[row][col] == numbers[i][col]) {
                            numbers[row][col] = 2 * numbers[row][col];
                            this.score += numbers[row][col];
                            numbers[i][col] = 0;
                            break;
                        }
                    }
                }
            }

            for (int col = 0; col < 4; col++) {
                for (int row = 3; row >= 0; row--) {
                    if (numbers[row][col] == 0) {
                        for (int i = row; i >= 0; i--) {
                            if (numbers[i][col] != 0) {
                                numbers[row][col] = numbers[i][col];
                                numbers[i][col] = 0;
                                break;
                            }
                        }
                    }
                }
            }

            if (ifMove(initialNumbers, this.numbers)) {
                this.numbers = generateANumber(this.numbers);
                this.step++;
            }
        }
    }

    public void up() {

        if (!ifGameEnd) {

            int[][] initialNumbers = generateInitialNumbers();

            for (int col = 0; col < 4; col++) {
                for (int row = 0; row < 4; row++) {
                    for (int i = row + 1; i < 4; i++) {

                        boolean ifOrtherNumbers = false;
                        if (numbers[i][col] != 0 && numbers[i][col] != numbers[row][col]) {
                            break;
                        }

                        if (!ifOrtherNumbers && numbers[row][col] == numbers[i][col]) {
                            numbers[row][col] = 2 * numbers[row][col];
                            this.score += numbers[row][col];
                            numbers[i][col] = 0;
                            break;
                        }
                    }
                }
            }

            for (int col = 0; col < 4; col++) {
                for (int row = 0; row < 4; row++) {
                    if (numbers[row][col] == 0) {
                        for (int i = row; i < 4; i++) {
                            if (numbers[i][col] != 0) {
                                numbers[row][col] = numbers[i][col];
                                numbers[i][col] = 0;
                                break;
                            }
                        }
                    }
                }
            }

            if (ifMove(initialNumbers, this.numbers)) {
                this.numbers = generateANumber(this.numbers);
                this.step++;
            }
        }

    }

    public int[][] generateInitialNumbers() {

        int[][] initialNumbers = new int[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                initialNumbers[row][col] = this.numbers[row][col];
            }
        }
        return initialNumbers;

    }

    public boolean ifMove(int[][] initialNumbers, int[][] finalNumbers) {

        boolean result = false;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (initialNumbers[row][col] != finalNumbers[row][col]) {
                    result = true;
                }
            }
        }
        return result;

    }

    public boolean loseTheGame() {

        boolean result = true;

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (this.numbers[row][col] == 0) {
                    return false;
                }
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (this.numbers[row][col] == this.numbers[row + 1][col]) {
                    return false;
                }
            }
        }

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 4; row++) {
                if (this.numbers[row][col] == this.numbers[row][col + 1]) {
                    return false;
                }
            }
        }
        this.ifGameEnd = true;
        return result;

    }

    public boolean win() {

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (this.numbers[row][col] >= this.goal) {
                    this.ifGameEnd = true;
                    return true;
                }
            }
        }
        return false;

    }

}
