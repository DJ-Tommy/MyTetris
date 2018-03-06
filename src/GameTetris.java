import tetris.Game;
import tetris.Matrix;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameTetris extends JFrame {

    private static JPanel panelField;
    private static JPanel panelMenu;

    private final String TITLE = "Tetris v.1.0.3";
    private final int FIELD_HEIGHT = 20; // высота игрового поля в блоках
    private final int FIELD_WIDTH = 8; // ширина игрового поля в блоках
    private final  int SIZE_BlOCK = 28; // размер каждого блока
    private final int MENU_HEIGHT = 4; // высота блока меню
    private final int SLEEP_START = 800; // начальная скорость
    private static int sleep; // скорость будет расти
    private final int x_start; // для отображения следующей фигуры в верхнем блоке
    private final int y_start;


    public static void main(String[] args) {
        new GameTetris();
    }

    private GameTetris() {
        new Game(FIELD_WIDTH, FIELD_HEIGHT);
        x_start = (FIELD_WIDTH - 3) * SIZE_BlOCK;
        y_start = (MENU_HEIGHT - 3) * SIZE_BlOCK + SIZE_BlOCK / 2;
        initGameField();
        initMenuField();
        initFrame();
        sleep = SLEEP_START;
        goTimer();
    }

    private static void goTimer() {
        while (1>0) {
            try {
                Thread.sleep(sleep);
            } catch (Exception e) { }
            Game.moveFigureToDown();
            panelField.repaint();
            panelMenu.repaint();
            sleep--;
        }
    }

    private void initGameField() {
        panelField = new JPanel() {
            @Override
            protected void paintComponent(Graphics gr) {
                super.paintComponent(gr);
                for (int y = 0; y < FIELD_HEIGHT; y++) {
                    for (int x = 0; x < FIELD_WIDTH; x++) {
                        if (x > 0 && y > 0) {
                            Color color = Matrix.getColor(8);
                            gr.setColor(color);
                            gr.drawLine(x * SIZE_BlOCK - SIZE_BlOCK / 5, y * SIZE_BlOCK, x * SIZE_BlOCK + SIZE_BlOCK / 5, y * SIZE_BlOCK);
                            gr.drawLine(x * SIZE_BlOCK, y * SIZE_BlOCK - SIZE_BlOCK / 5, x * SIZE_BlOCK, y * SIZE_BlOCK + SIZE_BlOCK / 5);
                        }

                        if (Matrix.getMatrixFigureOnField(y, x) < 9) {
                            Color color = Matrix.getColor(Matrix.getMatrixFigureOnField(y, x));
                            gr.setColor(color);
                            gr.draw3DRect(x * SIZE_BlOCK + 1, y * SIZE_BlOCK + 1, SIZE_BlOCK - 2, SIZE_BlOCK - 2, true);
                        }

                        if (Matrix.getStatus(y, x) < 9) {
                            Color color = Matrix.getColor(Matrix.getStatus(y, x));
                            gr.setColor(color);
                            gr.fill3DRect(x * SIZE_BlOCK + 1, y * SIZE_BlOCK + 1, SIZE_BlOCK - 2, SIZE_BlOCK - 2, true);
                        }
                    }
                }
                if (Game.gameOver()) {
                    Color color = Color.WHITE;
                    gr.setColor(color);
                    Font font = new Font("Times New Roman", 1, SIZE_BlOCK * 2);
                    gr.setFont(font);
                    gr.drawString("GAME", SIZE_BlOCK,SIZE_BlOCK * 3);
                    gr.drawString("OVER", SIZE_BlOCK,SIZE_BlOCK * 6);
                }
            }
        };
        panelField.setPreferredSize(new Dimension(FIELD_WIDTH * SIZE_BlOCK, FIELD_HEIGHT * SIZE_BlOCK));
        panelField.setBackground(Color.black);
        panelField.setBorder(new BevelBorder(0));
        add(panelField, BorderLayout.CENTER);
    }

    private void initMenuField() {
        panelMenu = new JPanel(){
            @Override
            protected void paintComponent(Graphics gr) {
                super.paintComponent(gr);
                Color color = Color.BLACK;
                gr.setColor(color);
                gr.draw3DRect(x_start - 2, y_start - 2, SIZE_BlOCK * 2 + 2, SIZE_BlOCK * 2 + 2, true);
                Font font = new Font("Times New Roman", 1, SIZE_BlOCK / 5 * 3);
                gr.setFont(font);
                gr.drawString("Next Figure", x_start - SIZE_BlOCK / 2, SIZE_BlOCK);
                gr.drawString("Score:", SIZE_BlOCK, SIZE_BlOCK);
                gr.draw3DRect(SIZE_BlOCK / 2, SIZE_BlOCK * 3 / 2, SIZE_BlOCK * 3, SIZE_BlOCK, true);
                gr.drawString(String.valueOf(Matrix.getScores()), SIZE_BlOCK, SIZE_BlOCK * 2 + SIZE_BlOCK / 5);
                gr.drawString("Press ENTER", SIZE_BlOCK / 2, SIZE_BlOCK * 3 + SIZE_BlOCK / 5);
                gr.drawString("for Start now", SIZE_BlOCK / 2, SIZE_BlOCK * 4 - SIZE_BlOCK / 4);

                color = Matrix.getColor(Game.figureMenu.color);
                gr.setColor(color);
                for (int y = 0; y < 4; y++) {
                    for (int x = 0; x < 4; x++) {
                        if (Game.figureMenu.matrixFigure[y][x] != 9)
                        gr.fill3DRect(x * SIZE_BlOCK / 2 + 1 + x_start, y * SIZE_BlOCK / 2 + 1 + y_start, SIZE_BlOCK / 2 - 2, SIZE_BlOCK / 2 - 2, true);
                    }
                }
            }
        };
        panelMenu.setPreferredSize(new Dimension(FIELD_WIDTH * SIZE_BlOCK, MENU_HEIGHT * SIZE_BlOCK));
        panelMenu.setBackground(Color.WHITE);
        panelMenu.setBorder(new BevelBorder(0));
        add(panelMenu, BorderLayout.NORTH);
    }

    private void initFrame() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e1) { }

            @Override
            public void keyPressed(KeyEvent e2) {
                if (e2.getKeyCode() == KeyEvent.VK_ENTER) {
                    Game.start();
                    sleep = SLEEP_START;
                }

                if (e2.getKeyCode() == KeyEvent.VK_DOWN) {
                    Game.moveFullDown();
                }

                if (e2.getKeyCode() == KeyEvent.VK_UP) {
                    Game.rotateFigure();
                }

                if (e2.getKeyCode() == KeyEvent.VK_LEFT) {
                    Game.moveFigureToLeft();
                }

                if (e2.getKeyCode() == KeyEvent.VK_RIGHT) {
                    Game.moveFigureToRight();
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e2) { }
        });
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(TITLE);
        setVisible(true);
        pack(); //автоматический подбор размера фрейма
        setLocationRelativeTo(null); // отобразить окно по центру экрана, обычно пишется в конце, иначе может не отрабатывать
    }
}
