package core;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Image;

public class GameLogic {
    int boardWidth;
    int boardHeight;

    Bird bird;
    ArrayList<Pipe> pipes;

    int velocityY = 0;
    int gravity = 1;

    int pipeX;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    int velocityX = -4;
    boolean gameOver = false;
    double score = 0;

    Random random = new Random();

    // adding pipe images
    Image topPipeImg;
    Image bottomPipeImg;

    public GameLogic(int boardWidth, int boardHeight, Bird bird, Image topPipeImg, Image bottomPipeImg) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.bird = bird;
        this.pipes = new ArrayList<>();
        this.pipeX = boardWidth;

        // defining pipe images
        this.topPipeImg = topPipeImg;
        this.bottomPipeImg = bottomPipeImg;
    }

    public void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(topPipeImg, pipeX, randomPipeY, pipeWidth, pipeHeight);
        Pipe bottomPipe = new Pipe(bottomPipeImg, pipeX, randomPipeY + pipeHeight + openingSpace, pipeWidth, pipeHeight);

        pipes.add(topPipe);
        pipes.add(bottomPipe);
    }

    public void move() {
        velocityY += gravity;
        bird.y += velocityY;
        if (bird.y < 0) {
            bird.y = 0;
        }

        for (Pipe pipe : pipes) {
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                score += 0.5;
                pipe.passed = true;
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    private boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    public void resetGame() {
        bird.y = boardHeight / 2;
        velocityY = 0;
        pipes.clear();
        gameOver = false;
        score = 0;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public double getScore() {
        return score;
    }
}
