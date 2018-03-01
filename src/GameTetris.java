import tetris.Game;
import tetris.Matrix;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameTetris extends JFrame {

    private JPanel panelField;
    private JPanel panelMenu;

    private final String TITLE = "Tetris v.0.1.1";
    private final int FIELD_HEIGHT = 18; // высота игрового поля в блоках
    private final int FIELD_WIDTH = 10; // ширина игрового поля в блоках
    private final  int SIZE_BlOCK = 25; // размер каждого блока
    private final int MENU_HEIGHT = 6; // высота блока меню
    private final int sleep = 1500;


    public static void main(String[] args) {
        new GameTetris();
    }

    private GameTetris() {

        new Game(FIELD_WIDTH, FIELD_HEIGHT);
        initGameField();
        initMenuField();
        initFrame();
        goTimer();

    }

    private void goTimer() {
        while (!Game.gameOver()) {
            try {
                Thread.sleep(sleep);
            } catch (Exception e) { }
            Game.moveFigureToDown();
            panelField.repaint();
            panelMenu.repaint();

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
                for (int y = 0; y < MENU_HEIGHT; y++) {
                    for (int x = 0; x < FIELD_WIDTH; x++) {
                        if (x > 0 && y > 0) {
                            Color color = Matrix.getColor(9);
                            gr.setColor(color);
                            gr.drawLine(x * SIZE_BlOCK - SIZE_BlOCK / 5, y * SIZE_BlOCK, x * SIZE_BlOCK + SIZE_BlOCK / 5, y * SIZE_BlOCK);
                            gr.drawLine(x * SIZE_BlOCK, y * SIZE_BlOCK - SIZE_BlOCK / 5, x * SIZE_BlOCK, y * SIZE_BlOCK + SIZE_BlOCK / 5);
                        }
                        if (x > FIELD_WIDTH - 5 && y > MENU_HEIGHT - 5 && x < FIELD_WIDTH && y < MENU_HEIGHT
                                && Game.figureMenu.matrixFigure[y - MENU_HEIGHT + 4][x - FIELD_WIDTH + 4] < 9) {
                            Color color = Matrix.getColor(Game.figureMenu.color);
                            gr.setColor(color);
                            gr.fill3DRect(x * SIZE_BlOCK + 1, y * SIZE_BlOCK + 1, SIZE_BlOCK - 2, SIZE_BlOCK - 2, true);
                        }
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
            public void keyTyped(KeyEvent e1) {

            }

            @Override
            public void keyPressed(KeyEvent e2) {

                if (e2.getKeyCode() == KeyEvent.VK_DOWN) {
                    //Game.rotateFigure();
                    Game.moveFullDown();
                }

                if (e2.getKeyCode() == KeyEvent.VK_UP) {
                    Game.rotateFigure();
                    //Game.start();
                }

                if (e2.getKeyCode() == KeyEvent.VK_LEFT) {
                    Game.moveFigureToLeft();
                    //MatrixGame.moveKeyboard(9, true);
                }

                if (e2.getKeyCode() == KeyEvent.VK_RIGHT) {
                    Game.moveFigureToRight();
                }
                repaint();

            }

            @Override
            public void keyReleased(KeyEvent e2) {

            }
        });




        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(TITLE);
        setVisible(true);
        pack(); //автоматический подбор размера фрейма
        setLocationRelativeTo(null); // отобразить окно по центру экрана, обычно пишется в конце, иначе может не отрабатывать
    }



}
