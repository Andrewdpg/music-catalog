
package model;

import java.util.Random;

/**
 * CodeGenerator
 */
public class UtilMatrix {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 0;
    public static final int UP = 1;

    public static String vLine(int[][] matrix, int direction, int xStart, int yStart, int less) {
        String line = null;
        if (matrix != null) {
            if (matrix[0].length > xStart && xStart >= 0) {
                less = less < 0 ? -less : less;
                line = "";
                int i = yStart;
                while (direction == UP ? i >= 0 + less : i < matrix.length - less) {
                    line += String.valueOf(matrix[i][xStart]);
                    i = direction == UP ? i - 1 : i + 1;
                }
            }
        }
        return line;
    }

    public static String hLine(int[][] matrix, int direction, int xStart, int yStart, int less) {
        String line = null;
        if (matrix != null) {
            if (matrix.length > yStart && yStart >= 0) {
                less = less < 0 ? -less : less;
                line = "";
                int i = direction == RIGHT ? xStart : matrix[0].length - 1;
                while (direction == RIGHT ? i < matrix[0].length - less : i >= 0 + less) {
                    line += String.valueOf(matrix[yStart][i]);
                    i = direction == RIGHT ? i + 1 : i - 1;
                }
            }
        }
        return line;
    }

    public static String dLine(int[][] matrix, int vDirection, int hDirection, int xStart, int yStart, int less) {
        String line = null;
        if (matrix != null) {
            if (matrix.length > yStart && yStart >= 0 && matrix[0].length > xStart && xStart >= 0) {
                line = "";
                less = less < 0 ? -less : less;
                int xMax = hDirection == RIGHT ? matrix[0].length - xStart : xStart + 1;
                int yMax = vDirection == UP ? yStart + 1 : matrix.length - yStart;
                int totalSteps = Math.min(yMax, xMax) - less;
                int y = yStart;
                int x = xStart;
                for (int i = 0; i < totalSteps; i++) {
                    line += String.valueOf(matrix[y][x]);
                    x = hDirection == RIGHT ? x + 1 : x - 1;
                    y = vDirection == UP ? y - 1 : y + 1;
                }

            }
        }
        return line;
    }

    public static int[][] randomMatrix(int rows, int columns) {

        try {
            Random rand = new Random();
            int[][] matrix = new int[rows][columns];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j] = rand.nextInt(10);
                }
            }
            return matrix;
        } catch (Exception e) {
            throw new RuntimeException("Both, columns and rows values must be positive integers.");

        }
    }

    public static String generateCode(int type, int[][] matrix) {
        String code = "";

        switch (type) {
            case 1:
                code += UtilMatrix.vLine(matrix, UtilMatrix.UP, 0, 5, 1);
                code += UtilMatrix.dLine(matrix, UtilMatrix.DOWN, UtilMatrix.RIGHT, 0, 0, 1);
                code += UtilMatrix.vLine(matrix, UtilMatrix.UP, 5, 5, 0);
                break;
            case 2:
                code += UtilMatrix.hLine(matrix, UtilMatrix.RIGHT, 0, 0, -3);
                code += UtilMatrix.vLine(matrix, UtilMatrix.DOWN, 2, 1, 0);
                code += UtilMatrix.vLine(matrix, UtilMatrix.UP, 3, 5, -1);
                code += UtilMatrix.hLine(matrix, UtilMatrix.RIGHT, 3, 0, 0);
                break;
            case 3:
                for (int i = 5; i >= 0 && code.length() < 16; i--) {
                    for (int j = 5; j >= 0 && code.length() < 16; j--) {
                        if ((i + j) % 2 != 0 && (i + j) > 1) {
                            code += matrix[i][j];
                        }
                    }
                }
                break;
            default:
                break;
        }
        return code;
    }
}