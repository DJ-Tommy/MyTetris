package tetris;

public class Figure {
    public int[][] matrixFigure = new int[4][4];
    public int color;
    public Coord coord;

    public Figure(int nomber) {
        color = nomber;
        int count  = 0;
        coord = new Coord(Matrix.getSize().x / 2 - 2, 0);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                count++;
                matrixFigure [y][x] = 9;
                if (count == Matrix.FIGURE[nomber][0] || count == Matrix.FIGURE[nomber][1] || count == Matrix.FIGURE[nomber][2] || count == Matrix.FIGURE[nomber][3]) {
                    matrixFigure [y][x] = nomber;
                }
            }
        }

    }
}
