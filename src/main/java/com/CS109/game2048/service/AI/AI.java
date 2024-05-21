package com.CS109.game2048.service.AI;

import com.CS109.game2048.service.Grid;
import com.CS109.game2048.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AI {
    private boolean ifPlayerTurn = true;
    private final Grid grid;
    private final int[][] matrix;
    private boolean[][] marked;

    private final int[][] vectors = {
            {0, -1},
            {1, 0},
            {0, 1},
            {-1, 0}
    };

    public AI(Grid grid) {
        this.grid = new Grid(grid.getMatrix());
        this.matrix = this.grid.getMatrix();
    }

    public boolean isCellAvailable(int x, int y) {
        return matrix[x][y] == 0;
    }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < 4 && y >= 0 && y < 4;
    }

    public List<int[]> getAvailableCells() {
        List<int[]> cells = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] == 0) {
                    int[] tmp = {i, j};
                    cells.add(tmp);
                }
            }
        }
        return cells;
    }

    public void insertTitle(int x, int y, int value) {
        this.matrix[x][y] = value;
    }

    public void removeTitle(int x, int y) {
        this.matrix[x][y] = 0;
    }

    public int getZeroNumbers(int[][] matrix) {
        int sum = 0;
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (ints[j] == 0) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public double smoothness() {
        double smoothness = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] != 0) {
                    double value = Math.log(matrix[i][j]) / Math.log(2);
                    for (int direction = 1; direction <= 2; direction++) {
                        int[] vector = this.vectors[direction];
                        int x = i, y = j;
                        do {
                            x += vector[0];
                            y += vector[1];
                        } while (isInBounds(x, y) && isCellAvailable(x, y));
                        if (isInBounds(x, y)) {
                            if (matrix[x][y] != 0) {
                                double targetValue = Math.log(matrix[x][y]) / Math.log(2);
                                smoothness -= Math.abs(value - targetValue);
                            }
                        }
                    }
                }
            }
        }
        return smoothness;
    }

    public double monotonicity() {

        double[] totals = {0, 0, 0, 0};

        for (int i = 0; i < 4; i++) {
            int current = 0;
            int next = current + 1;
            while (next < 4) {
                while (next < 4 && matrix[i][next] == 0) next++;
                if (next >= 4) next--;
                double currentValue = (matrix[i][current] != 0) ? Math.log(matrix[i][current]) / Math.log(2) : 0;
                double nextValue = (matrix[i][next] != 0) ? Math.log(matrix[i][next]) / Math.log(2) : 0;
                if (currentValue > nextValue) {
                    totals[0] += nextValue - currentValue;
                } else if (nextValue > currentValue) {
                    totals[1] += currentValue - nextValue;
                }
                current = next;
                next++;
            }
        }

        for (int i = 0; i < 4; i++) {
            int current = 0;
            int next = current + 1;
            while (next < 4) {
                while (next < 4 && matrix[next][i] == 0) next++;
                if (next >= 4) next--;
                double currentValue = (matrix[current][i] != 0) ? Math.log(matrix[current][i]) / Math.log(2) : 0;
                double nextValue = (matrix[next][i] != 0) ? Math.log(matrix[next][i]) / Math.log(2) : 0;
                if (currentValue > nextValue) {
                    totals[2] += nextValue - currentValue;
                } else if (nextValue > currentValue) {
                    totals[3] += currentValue - nextValue;
                }
                current = next;
                next++;
            }
        }
        return Math.max(totals[0], totals[1]) + Math.max(totals[2], totals[3]);
    }

    public double maxValue() {
        return Math.log(ArrayUtil.getMax(matrix)) / Math.log(2);
    }

    public int islands() {
        int islands = 0;

        marked = new boolean[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] != 0) {
                    marked[i][j] = false;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] != 0 && !marked[i][j]) {
                    islands++;
                    mark(i, j, matrix[i][j]);
                }
            }
        }

        return islands;
    }

    public void mark(int x, int y, int value) {
        if (x >= 0 && x <= 3 && y >= 0 && y <= 3 && this.matrix[x][y] != 0 && this.matrix[x][y] == value && !this.marked[x][y]) {
            this.marked[x][y] = true;
            for (int direction = 0; direction < 4; direction++) {
                int[] vector = this.vectors[direction];
                mark(x + vector[0], y + vector[1], value);
            }
        }
    }

    public void right() {


        int[][] preMatrix = new int[4][4];
        ArrayUtil.copyMatrix(this.matrix, preMatrix, 4, 4);

        for (int row = 0; row < 4; row++) {
            for (int col = 3; col >= 0; col--) {
                for (int i = col - 1; i >= 0; i--) {

                    boolean ifOrtherNumbers = false;
                    if (matrix[row][i] != 0 && matrix[row][i] != matrix[row][col]) {
                        break;
                    }

                    if (!ifOrtherNumbers && matrix[row][col] == matrix[row][i]) {
                        matrix[row][col] = 2 * matrix[row][col];
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

    }

    public void left() {


        int[][] preMatrix = new int[4][4];
        ArrayUtil.copyMatrix(this.matrix, preMatrix, 4, 4);

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                for (int i = col + 1; i < 4; i++) {

                    boolean ifOrtherNumbers = false;
                    if (matrix[row][i] != 0 && matrix[row][i] != matrix[row][col]) {
                        break;
                    }

                    if (!ifOrtherNumbers && matrix[row][col] == matrix[row][i]) {
                        matrix[row][col] = 2 * matrix[row][col];
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
    }

    public void down() {


        int[][] preMatrix = new int[4][4];
        ArrayUtil.copyMatrix(this.matrix, preMatrix, 4, 4);

        for (int col = 0; col < 4; col++) {
            for (int row = 3; row >= 0; row--) {
                for (int i = row - 1; i >= 0; i--) {

                    boolean ifOrtherNumbers = false;
                    if (matrix[i][col] != 0 && matrix[i][col] != matrix[row][col]) {
                        break;
                    }

                    if (!ifOrtherNumbers && matrix[row][col] == matrix[i][col]) {
                        matrix[row][col] = 2 * matrix[row][col];
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

    }

    public void up() {


        int[][] preMatrix = new int[4][4];
        ArrayUtil.copyMatrix(this.matrix, preMatrix, 4, 4);

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                for (int i = row + 1; i < 4; i++) {

                    boolean ifOrtherNumbers = false;
                    if (matrix[i][col] != 0 && matrix[i][col] != matrix[row][col]) {
                        break;
                    }

                    if (!ifOrtherNumbers && matrix[row][col] == matrix[i][col]) {
                        matrix[row][col] = 2 * matrix[row][col];
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
    }

    public boolean move(int direction) {
        int[][] preMatrix = new int[4][4];
        ArrayUtil.copyMatrix(matrix, preMatrix, 4, 4);

        boolean moved = false;
        switch (direction) {
            case 0:
                up();
                break;
            case 1:
                right();
                break;
            case 2:
                down();
                break;
            case 3:
                left();
                break;
        }

        if (ArrayUtil.isMatrixEquals(preMatrix, this.matrix)) {
            moved = true;
            ifPlayerTurn = false;
        }

        return moved;
    }

    private double evaluate() {
        double smoothWeight = 0.1,
                monoWeight = 1.2,
                emptyWeight = 2.6,
                maxWeight = 1;
        return smoothness() * smoothWeight
                + monotonicity() * monoWeight
                + Math.log(getZeroNumbers(matrix) + 0.000001) * emptyWeight
                + maxValue() * maxWeight;
    }

    private SearchResult search(int depth, double alpha, double beta, int positions, int cutoffs) {
        double bestScore;
        int bestMove = -1;
        SearchResult result = new SearchResult();
        int[] directions = {0, 1, 2, 3};

        if (this.ifPlayerTurn) {
            bestScore = alpha;
            for (int direction : directions) {
                AI newAI = new AI(grid);
                if (newAI.move(direction)) {
                    this.ifPlayerTurn = true;
                    positions++;
                    newAI.ifPlayerTurn = false;

                    if (depth == 0) {
                        result.move = direction;
                        result.score = newAI.evaluate();
                    } else {
                        result = newAI.search(depth - 1, bestScore, beta, positions, cutoffs);
                        if (result.score > 9900) {
                            result.score--;
                        }
                        positions = result.position;
                        cutoffs = result.cutoffs;
                    }

                    if (result.score > bestScore) {
                        bestScore = result.score;
                        bestMove = direction;
                    }
                    if (bestScore > beta) {
                        cutoffs++;
                        return new SearchResult(bestMove, beta, positions, cutoffs);
                    }
                }
            }
        } else {
            bestScore = beta;
            List<Candidate> candidates = new ArrayList<>();
            List<int[]> cells = getAvailableCells();
            int[] fill = {2, 4};
            List<Double> scores_2 = new ArrayList<>();
            List<Double> scores_4 = new ArrayList<>();
            for (int value : fill) {
                for (int i = 0; i < cells.size(); i++) {
                    insertTitle(cells.get(i)[0], cells.get(i)[1], value);
                    if (value == 2) scores_2.add(i, -smoothness() + islands());
                    if (value == 4) scores_4.add(i, -smoothness() + islands());
                    removeTitle(cells.get(i)[0], cells.get(i)[1]);
                }
            }
            double maxScore = 0;
            if (!scores_2.isEmpty() && scores_4.isEmpty()) {
                maxScore = Collections.max(scores_2);
            } else if (scores_2.isEmpty() && !scores_4.isEmpty()) {
                maxScore = Collections.max(scores_4);
            } else if (!scores_2.isEmpty()) {
                maxScore = Math.max(Collections.max(scores_2), Collections.max(scores_4));
            }

            for (int value : fill) {
                if (value == 2) {

                    for (Double fitness : scores_2) {
                        if (fitness == maxScore) {
                            int index = scores_2.indexOf(fitness);
                            candidates.add(new Candidate(cells.get(index)[0], cells.get(index)[1], value));
                        }
                    }

                }
                if (value == 4) {
                    for (Double fitness : scores_4) {
                        if (fitness == maxScore) {
                            int index = scores_4.indexOf(fitness);
                            candidates.add(new Candidate(cells.get(index)[0], cells.get(index)[1], value));
                        }
                    }
                }
            }

            for (Candidate candidate : candidates) {
                int pos_x = candidate.x;
                int pos_y = candidate.y;
                int value = candidate.value;
                AI newAI = new AI(this.grid);
                newAI.insertTitle(pos_x, pos_y, value);
                positions++;
                newAI.ifPlayerTurn = true;
                result = newAI.search(depth, alpha, bestScore, positions, cutoffs);
                positions = result.position;
                cutoffs = result.cutoffs;

                if (result.score < bestScore) {
                    bestScore = result.score;
                }
                if (bestScore < alpha) {
                    cutoffs++;
                    return new SearchResult(-1, alpha, positions, cutoffs);
                }

            }
        }
        return new SearchResult(bestMove, bestScore, positions, cutoffs);
    }

    public int getBestMove(double minSearchTime) {
        return this.iterativeDeep(minSearchTime);
    }

    public int iterativeDeep(double minSearchTime) {
        long start = new Date().getTime();
        int depth = 0;
        int best = -1;
        do {
            SearchResult newBest = this.search(depth, -10000, 10000, 0, 0);
            if (newBest.move == -1) break;
            else best = newBest.move;
            depth++;
        } while (new Date().getTime() - start < minSearchTime);
        return best;
    }

}
