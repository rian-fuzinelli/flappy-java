package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    Bird bird;
    GameLogic gameLogic;

    Timer gameLoop;
    Timer placePipeTimer;

    public GamePanel() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        backgroundImg = new ImageIcon("res/flappybirdbg.png").getImage();
        birdImg = new ImageIcon("res/flappybird.png").getImage();
        topPipeImg = new ImageIcon("res/toppipe.png").getImage();
        bottomPipeImg = new ImageIcon("res/bottompipe.png").getImage();

        bird = new Bird(birdImg, boardWidth / 8, boardHeight / 2, 34, 24);
        gameLogic = new GameLogic(boardWidth, boardHeight, bird, topPipeImg, bottomPipeImg);

        placePipeTimer = new Timer(1500, e -> gameLogic.placePipes());
        placePipeTimer.start();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
        bird.draw(g);

        for (Pipe pipe : gameLogic.pipes) {
            pipe.draw(g);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameLogic.isGameOver()) {
            g.drawString("Game Over: " + (int) gameLogic.getScore(), 10, 35);
        } else {
            g.drawString(String.valueOf((int) gameLogic.getScore()), 10, 35);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameLogic.move();
        repaint();
        if (gameLogic.isGameOver()) {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameLogic.velocityY = -9;

            if (gameLogic.isGameOver()) {
                gameLogic.resetGame();
                gameLoop.start();
                placePipeTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }
}
