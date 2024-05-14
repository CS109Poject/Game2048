package com.CS109.game2048.service;

import com.CS109.game2048.util.ArrayUtil;

import java.util.Random;

public class Grid {
    private int[][] matrix = new int[4][4];
    private int step = 0;
    private int score = 0;
    private int goal = 2048;
    private boolean ifGameEnd = false;

    public Grid() {
    }

    public Grid(int[][] matrix){
        this.matrix = new int[4][4];
        ArrayUtil.copyMatrix(matrix,this.matrix,4,4);
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
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

    public boolean getIfGameEnd() {
        return this.ifGameEnd;
    }

    public void setIfGameEnd(boolean ifGameEnd){
        this.ifGameEnd = ifGameEnd;

    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void initGridNumbers() {
        generateRandomNumber();
    }

    /**
     * Randomly generate a 2 or 4 on the matrix's blanket area.
     */
    public void generateRandomNumber() {

        Random random = new Random();
        int num = random.nextInt(1, 3) * 2;

        while (true) {

            int row = random.nextInt(4);
            int col = random.nextInt(4);

            if (this.matrix[row][col] == 0) {
                this.matrix[row][col] = num;
                break;

            }
        }
    }

    /**
     * Move right.
     */
    public void right() {

        if (!ifGameEnd) {

            int[][] preMatrix = new int[4][4];
            ArrayUtil.copyMatrix(this.matrix,preMatrix,4,4);

            for (int row = 0; row < 4; row++) {
                for (int col = 3; col >= 0; col--) {
                    for (int i = col - 1; i >= 0; i--) {

                        boolean ifOrtherNumbers = false;
                        if (matrix[row][i] != 0 && matrix[row][i] != matrix[row][col]) {
                            break;
                        }

                        if (!ifOrtherNumbers && matrix[row][col] == matrix[row][i]) {
                            matrix[row][col] = 2 * matrix[row][col];
                            this.score += matrix[row][col];
                            matrix[row][i] = 0;
                            break;
                        }
                    }
                }
            }

            for (int row = 0; row < 4; row++) {
                for (int col = 3; col >= 0; col--) {
                    if (matrix[row][col] == 0) {
                        for (int i = col - 1; i >= 0; i--) {
                            if (matrix[row][i] != 0) {
                                matrix[row][col] = matrix[row][i];
                                matrix[row][i] = 0;
                                break;
                            }
                        }
                    }
                }
            }

            if (ArrayUtil.isMatrixEquals(preMatrix, this.matrix)) {
                generateRandomNumber();
                this.step++;
            }
        }
    }

    /**
     * Move left.
     */
    public void left() {

        if (!ifGameEnd) {

            int[][] preMatrix = new int[4][4];
            ArrayUtil.copyMatrix(this.matrix,preMatrix,4,4);

            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    for (int i = col + 1; i < 4; i++) {

                        boolean ifOrtherNumbers = false;
                        if (matrix[row][i] != 0 && matrix[row][i] != matrix[row][col]) {
                            break;
                        }

                        if (!ifOrtherNumbers && matrix[row][col] == matrix[row][i]) {
                            matrix[row][col] = 2 * matrix[row][col];
                            this.score += matrix[row][col];
                            matrix[row][i] = 0;
                            break;
                        }
                    }
                }
            }

            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    if (matrix[row][col] == 0) {
                        for (int i = col; i < 4; i++) {
                            if (matrix[row][i] != 0) {
                                matrix[row][col] = matrix[row][i];
                                matrix[row][i] = 0;
                                break;
                            }
                        }
                    }
                }
            }

            if (ArrayUtil.isMatrixEquals(preMatrix, this.matrix)) {
                generateRandomNumber();
                this.step++;
            }
        }
    }

    /**
     * Move down.
     */
    public void down() {

        if (!ifGameEnd) {

            int[][] preMatrix = new int[4][4];
            ArrayUtil.copyMatrix(this.matrix,preMatrix,4,4);

            for (int col = 0; col < 4; col++) {
                for (int row = 3; row >= 0; row--) {
                    for (int i = row - 1; i >= 0; i--) {

                        boolean ifOrtherNumbers = false;
                        if (matrix[i][col] != 0 && matrix[i][col] != matrix[row][col]) {
                            break;
                        }

                        if (!ifOrtherNumbers && matrix[row][col] == matrix[i][col]) {
                            matrix[row][col] = 2 * matrix[row][col];
                            this.score += matrix[row][col];
                            matrix[i][col] = 0;
                            break;
                        }
                    }
                }
            }

            for (int col = 0; col < 4; col++) {
                for (int row = 3; row >= 0; row--) {
                    if (matrix[row][col] == 0) {
                        for (int i = row; i >= 0; i--) {
                            if (matrix[i][col] != 0) {
                                matrix[row][col] = matrix[i][col];
                                matrix[i][col] = 0;
                                break;
                            }
                        }
                    }
                }
            }

            if (ArrayUtil.isMatrixEquals(preMatrix, this.matrix)) {
                generateRandomNumber();
                this.step++;
            }
        }
    }

    /**
     * Move up.
     */
    public void up() {

        if (!ifGameEnd) {

            int[][] preMatrix = new int[4][4];
            ArrayUtil.copyMatrix(this.matrix,preMatrix,4,4);

            for (int col = 0; col < 4; col++) {
                for (int row = 0; row < 4; row++) {
                    for (int i = row + 1; i < 4; i++) {

                        boolean ifOrtherNumbers = false;
                        if (matrix[i][col] != 0 && matrix[i][col] != matrix[row][col]) {
                            break;
                        }

                        if (!ifOrtherNumbers && matrix[row][col] == matrix[i][col]) {
                            matrix[row][col] = 2 * matrix[row][col];
                            this.score += matrix[row][col];
                            matrix[i][col] = 0;
                            break;
                        }
                    }
                }
            }

            for (int col = 0; col < 4; col++) {
                for (int row = 0; row < 4; row++) {
                    if (matrix[row][col] == 0) {
                        for (int i = row; i < 4; i++) {
                            if (matrix[i][col] != 0) {
                                matrix[row][col] = matrix[i][col];
                                matrix[i][col] = 0;
                                break;
                            }
                        }
                    }
                }
            }

            if (ArrayUtil.isMatrixEquals(preMatrix, this.matrix)) {
                generateRandomNumber();
                this.step++;
            }
        }

    }

    /**
     * Determine whether losing the game.
     * @return
     */
    public boolean lose() {

        boolean result = true;

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (this.matrix[row][col] == 0) {
                    return false;
                }
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (this.matrix[row][col] == this.matrix[row + 1][col]) {
                    return false;
                }
            }
        }

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 4; row++) {
                if (this.matrix[row][col] == this.matrix[row][col + 1]) {
                    return false;
                }
            }
        }
        this.ifGameEnd = true;
        return result;

    }

    /**
     * Determine whether winning the game.
     * @return
     */
    public boolean win() {
        if(ArrayUtil.getMax(this.matrix)>=this.goal){
            this.ifGameEnd=true;
            return true;
        }else {
            return false;
        }
    }

}
