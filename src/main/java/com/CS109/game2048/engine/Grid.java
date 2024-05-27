package com.CS109.game2048.engine;

import com.CS109.game2048.util.ArrayUtil;
import com.CS109.game2048.util.Mode;

import java.io.Serializable;
import java.util.Random;

public class Grid implements Serializable {
    private int[][] matrix = new int[4][4];
    private int step = 0;
    private int score = 0;
    private int goal = 2048;
    private boolean ifGameEnd = false;
    private boolean ifGameBegin = false;
    private Grid parentGrid = null;
    public Mode mode = Mode.NORMAL_GOAL;
    public int timeSeconds;
    private boolean ifStepBack = false;

    public void copy(Grid grid) {
        this.ifGameBegin = grid.isIfGameBegin();
        this.matrix = grid.getMatrix();
        this.step = grid.step;
        this.score = grid.getScore();
        this.ifGameEnd = grid.ifGameEnd;
        this.parentGrid = grid.parentGrid;
        this.ifStepBack = grid.isIfStepBack();
        this.mode = grid.mode;
        this.timeSeconds = grid.timeSeconds;
        ;
    }

    public boolean isIfGameBegin() {
        return ifGameBegin;
    }

    public void setIfGameBegin(boolean ifGameBegin) {
        this.ifGameBegin = ifGameBegin;
    }

    public boolean isIfStepBack() {
        return ifStepBack;
    }

    public void setIfStepBack(boolean ifStepBack) {
        this.ifStepBack = ifStepBack;
    }

    public Grid getParentGrid() {
        return parentGrid;
    }

    public void setParentGrid(Grid parentGrid) {
        this.parentGrid = parentGrid;
    }

    public Grid() {
    }

    public Grid(int[][] matrix) {
        this.matrix = new int[4][4];
        ArrayUtil.copyMatrix(matrix, this.matrix, 4, 4);
    }

    public Grid(Grid origin) {
        this.parentGrid = origin.parentGrid;
        ArrayUtil.copyMatrix(origin.matrix, this.matrix, 4, 4);
        this.score = origin.score;
        this.goal = origin.goal;
        this.step = origin.step;
        this.ifGameEnd = origin.ifGameEnd;
        this.mode = origin.mode;
        this.timeSeconds = origin.timeSeconds;
        this.ifStepBack = origin.ifStepBack;
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

    public void setScore(int score) {
        this.score = score;
    }

    public int getGoal() {
        return this.goal;
    }

    public boolean getIfGameEnd() {
        return this.ifGameEnd;
    }

    public void setIfGameEnd(boolean ifGameEnd) {
        this.ifGameEnd = ifGameEnd;

    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void initGridNumbers() {

        Random random = new Random();

        while (true) {

            int row1 = random.nextInt(4);
            int col1 = random.nextInt(4);
            int row2 = random.nextInt(4);
            int col2 = random.nextInt(4);

            if (row1 != row2 || col1 != col2) {
                this.matrix[row1][col1] = 2;
                this.matrix[row2][col2] = 4;
                break;
            }

        }
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

            Grid parentGrid = new Grid(this);

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

            if (ArrayUtil.isMatrixEquals(parentGrid.matrix, this.matrix)) {
                this.setParentGrid(parentGrid);
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

            Grid parentGrid = new Grid(this);

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

            if (ArrayUtil.isMatrixEquals(parentGrid.matrix, this.matrix)) {
                this.setParentGrid(parentGrid);
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

            Grid parentGrid = new Grid(this);

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

            if (ArrayUtil.isMatrixEquals(parentGrid.matrix, this.matrix)) {
                this.setParentGrid(parentGrid);
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

            Grid parentGrid = new Grid(this);

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

            if (ArrayUtil.isMatrixEquals(parentGrid.matrix, this.matrix)) {
                this.setParentGrid(parentGrid);
                generateRandomNumber();
                this.step++;
            }
        }

    }

    /**
     * Determine whether losing the game.
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
     */
    public boolean win() {
        if (ArrayUtil.getMax(this.matrix) >= this.goal) {
            this.ifGameEnd = true;
            return true;
        } else {
            return false;
        }
    }

}
