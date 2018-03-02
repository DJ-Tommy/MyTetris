package tetris;

import java.awt.*;

// 1   2   3   4
// 5   6   7   8
// 9   10  11  12
// 13  14  15  16
public class Matrix {

    private static int scores;
    private static Coord size;
    private static int [][] matrixField;
    private static int [][] matrixFigureOnField;
// Задаем цвета 0-6 цвета фигур, 7 - разметка поля (красный фиолет, оранжевый, зеленый, синий
    private static Color[] matrixColor = {new Color(250,10,10), new Color(140, 0, 250),
        new Color(255,80,0), new Color(0, 200, 0),
        new Color(0,0,250), new Color(255, 215, 0),
        new Color(0,255,255), new Color(245, 20, 147),
        new Color(192,192,192), new Color(128, 128, 128),};
    protected static int[][] FIGURE = { {2, 6, 10, 14}, // I  номера заполненных ячеек и номер цвета по номеру строки
                                        {6, 7, 10, 11}, // O
                                        {7, 9, 10, 11},   // J
                                        {6, 10, 11, 12},   // L
                                        {6, 9, 10, 11},   // T
                                        {5, 6, 10, 11},   // Z
                                        {7, 8, 10, 11}};  // S


    public static Coord getSize() {
        return size;
    }

    protected static void setSize(Coord coord) {
        size = coord;
    }

    protected static void startMatrixField() {
        matrixField = new int[size.y][size.x];
        matrixFigureOnField = new int[size.y][size.x];
        for (int y = 0; y <size.y; y++) {
            for (int x = 0; x < size.x; x++) {
                matrixField [y][x] = 9;
                matrixFigureOnField[y][x] = 9;
            }
        }


    }

    public static Color getColor(int color) {
        return matrixColor[color];

    }

    public static int getStatus(int y, int x) {
        return matrixField [y][x];
    }

    protected static void setMatrixField(Coord coord, int color) {
        matrixField [coord.y][coord.x] = color;
    }

    public static int getMatrixFigureOnField(int y, int x) {
        return matrixFigureOnField[y][x];
    }

    protected static void setMatrixFigureOnField(Coord coord, int color) {
        matrixFigureOnField[coord.y][coord.x] = color;
    }

    public static int getScores() {
        return scores;
    }

    protected static void setScores(int scores) {
        Matrix.scores += scores;
    }
}
