package tetris;

import java.util.Arrays;

public class Game {

    public static Figure figureFeild;
    public static Figure figureMenu;

    public Game(int WIDTH, int HEIGHT) {
        Matrix.setSize(new Coord(WIDTH, HEIGHT));
        Matrix.startMatrixField();
        start();

    }

    public static void newFigureMenu() {
        int random = (int) (Math.random() * 7);
        figureMenu = new Figure(random);

    }

    public static void newFigureField() {
        figureFeild = figureMenu;

    }

    private static int [] getEmptyLineFigure() {
        int countleft0 = 0;
        int countleft1 = 0;
        int countright0 = 0;
        int countright1 = 0;
        int countdown0 = 0;
        int countdown1 = 0;
        int countup0 = 0;
        int countup1 = 0;
        int[] status = new int[4];
        for (int y = 0; y < 4; y++) {

                if (figureFeild.matrixFigure[y][0] == 9) countleft0++;
                if (figureFeild.matrixFigure[y][1] == 9) countleft1++;
                if (figureFeild.matrixFigure[y][3] == 9) countright0++;
                if (figureFeild.matrixFigure[y][2] == 9) countright1++;
                if (figureFeild.matrixFigure[3][y] == 9) countdown0++;
                if (figureFeild.matrixFigure[2][y] == 9) countdown1++;
            if (figureFeild.matrixFigure[0][y] == 9) countup0++;
            if (figureFeild.matrixFigure[1][y] == 9) countup1++;
        }
        if (countleft0 < 4) status[0] = 0;
        if (countleft0 == 4) status[0] = 1;
        if (countleft1 == 4) status[0] = 2;
        if (countright0 < 4) status[2] = 0;
        if (countright0 == 4) status[2] = 1;
        if (countright1 == 4) status[2] = 2;
        if (countdown0 < 4) status[1] = 0;
        if (countdown0 == 4) status[1] = 1;
        if (countdown1 == 4) status[1] = 2;
        if (countup0 < 4) status[3] = 0;
        if (countup0 == 4) status[3] = 1;
        if (countup1 == 4) status[3] = 2;
        return status;
    }

    public static void deleteLine(int line) {
        int [][] deleteMatrix = new int[Matrix.getSize().y][Matrix.getSize().x];
        for (int y = 0; y < line; y++) {
            for (int x = 0; x < Matrix.getSize().x; x++) {
                deleteMatrix[y][x] = Matrix.getStatus(y, x);
            }
        }

        for (int y = 0; y < line + 1; y++) {
            for (int x = 0; x < Matrix.getSize().x; x++) {
                if (y == 0)Matrix.setMatrixField(new Coord(x, y), 9);
                else Matrix.setMatrixField(new Coord(x, y), deleteMatrix [y-1][x]);
            }
        }
    }

    public static void scanLine() {
        int countLine = 0;
        for (int y = 0; y < Matrix.getSize().y; y++) {
            int countOnLine = 0;
            for (int x = 0; x < Matrix.getSize().x; x++) {
                if (Matrix.getStatus(y, x) != 9) countOnLine++;
            }
            if (countOnLine >= Matrix.getSize().x) {
                deleteLine(y);
                countLine++;
            }
        }
        Matrix.setScores((100 + (countLine * 25)) * countLine);
    }

    public static boolean checkRotate() {
        boolean t = true;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                try {
                    if (figureFeild.matrixFigure[x][3 - y] != 9
                            && figureFeild.coord.x + x >= Matrix.getSize().x
                            || figureFeild.coord.x + x < 0) t = false;
                    if (figureFeild.matrixFigure[x][3 - y] != 9
                            && Matrix.getStatus(figureFeild.coord.y + y, figureFeild.coord.x + x) != 9) {
                        t = false;
                    }

                } catch (Exception e) { }
            }
        }
        //if (figureFeild.coord.x + getEmptyLineFigure()[3] < 1
        //        && figureFeild.coord.x - getEmptyLineFigure()[1] + 4 >= Matrix.getSize().x) t = false;
        return t;
    }
    public static void rotateFigure() {
        if (checkRotate()) {
            int[][] rotateMatrix = new int[4][4];
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    rotateMatrix[y][x] = figureFeild.matrixFigure[y][x];
                }
            }
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    figureFeild.matrixFigure[y][x] = rotateMatrix[x][3 - y];
                }
            }
            moveFigure(figureFeild.coord);
        }
    }

    public static void moveFigureToLeft() {
        if (checkLeft()) figureFeild.coord.x--;
        moveFigure(figureFeild.coord);

    }

    public static void moveFigureToRight() {
        if (checkRight()) figureFeild.coord.x++;
        moveFigure(figureFeild.coord);
    }

    public static boolean checkLeft() {
        boolean t = true;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                try { // для обхода ошибки массива при вызове getStatus внизу игрового поля
                    if (figureFeild.matrixFigure[y][x] != 9
                            && Matrix.getStatus(figureFeild.coord.y + y, figureFeild.coord.x + x - 1) != 9) {
                        t = false;
                    }
                } catch (Exception e) {}
            }
        }

        if (figureFeild.coord.x + getEmptyLineFigure()[0] < 1) t = false;
        return t;
    }

    public static boolean checkRight() {
        boolean t = true;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                try { // для обхода ошибки массива при вызове getStatus внизу игрового поля
                    if (figureFeild.matrixFigure[y][x] != 9
                            && Matrix.getStatus(figureFeild.coord.y + y, figureFeild.coord.x + x + 1) != 9) {

                        t = false;
                    }
                } catch (Exception e) {}
            }
        }

        if (figureFeild.coord.x - getEmptyLineFigure()[2] + 4 >= Matrix.getSize().x) t = false;

        return t;
    }

    public static boolean checkDown() {
        boolean t = true;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                try { // для обхода ошибки массива при вызове getStatus внизу игрового поля
                    if (figureFeild.matrixFigure[y][x] != 9
                            && Matrix.getStatus(figureFeild.coord.y + y + 1, figureFeild.coord.x + x) != 9) {

                        t = false;
                    }
                } catch (Exception e) {}
            }

        }
        if (figureFeild.coord.y + 4 >= Matrix.getSize().y
                && figureFeild.coord.y - getEmptyLineFigure()[1] + 4 >= Matrix.getSize().y) t = false;

        return t;
    }

    public static void moveFullDown() {
        while (checkDown()) {
            moveFigureToDown();
        }
        moveFigureToDown();
    }

    public static void moveFigureToDown() {

        if (!checkDown()) {
            addFigureToMatrixField(figureFeild.coord);
            scanLine();
            nextFigure();
        }
        if (checkDown()) figureFeild.coord.y++;
        moveFigure(figureFeild.coord);

    }

    public static void addFigureToMatrixField(Coord coord) {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (figureFeild.matrixFigure[y][x] < 9) {
                    Matrix.setMatrixField(new Coord(figureFeild.coord.x + x, figureFeild.coord.y + y), figureFeild.color);
                }
            }
        }
    }

    public static void moveFigure(Coord coord) {
        figureFeild.coord = coord;
        for (int y = 0; y < Matrix.getSize().y; y++) {
            for (int x = 0; x < Matrix.getSize().x; x++) {
                Matrix.setMatrixFigureOnField(new Coord(x, y), 9);
            }
        }
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (figureFeild.matrixFigure[y][x] < 9) {
                    Matrix.setMatrixFigureOnField(new Coord(figureFeild.coord.x + x, figureFeild.coord.y + y), figureFeild.color);
                }
            }
        }
    }

    public static void nextFigure() {
        if (!gameOver()) {
            newFigureField();
            newFigureMenu();
            moveFigure(figureFeild.coord);
        }
    }


    public static void start() {
        Matrix.startMatrixField();
        newFigureMenu();
        newFigureField();
        newFigureMenu();
        moveFigure(new Coord(1,1));
    }

    public static boolean gameOver() {
        boolean t =  false;
        for (int x = 0; x < Matrix.getSize().x; x++)
            if (Matrix.getStatus(1, x) != 9) t = true;

        return t;
    }
}

