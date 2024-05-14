package com.CS109.game2048.util;

public class ArrayUtil {
    /**
     * Copy the numbers of the matrix.
     *
     * @param srcMatrix the copied matrix.
     * @param destMatrix the copying matrix.
     * @param row the number of rows of scrMatrix.
     * @param col the length of scrMatrix.
     */
    public static void copyMatrix(int[][] srcMatrix, int[][] destMatrix, int row, int col) {
        for (int i = 0; i < row; i++) {
            System.arraycopy(srcMatrix[i], 0, destMatrix[i], 0, col);
        }
    }

    /**
     * Get the maximum number of the matrix.
     */
    public static int getMax(int[][] matrix) {
        int max = 0;
        for (int[] aMatrix : matrix)
            for (int j = 0; j < matrix[0].length; j++)
                if (aMatrix[j] > max) max = aMatrix[j];
        return max;
    }

    /**
     * Determine whether the two matrices are equal.
     */
    public static boolean isMatrixEquals(int[][] matrix_1, int[][] matrix_2){
        for (int i = 0; i < matrix_1.length; i++) {
            for (int j = 0; j < matrix_1[0].length; j++) {
                if (matrix_1[i][j] != matrix_2[i][j]) return true;
            }
        }
        return false;
    }


}
