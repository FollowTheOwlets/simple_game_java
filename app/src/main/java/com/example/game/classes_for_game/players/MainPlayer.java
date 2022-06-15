package com.example.game.classes_for_game.players;

public class MainPlayer extends MyPlayer {

    public MainPlayer(int x, int y, int maxX, int maxY) {
        super(0, 22, maxX, maxY);
    }

    @Override
    public void stepX(int directionX) {
        if (this.coordinate.getCoor()[0] == 0 && directionX < 0) {
            directionX = 0;
        }
        if (this.coordinate.getCoor()[0] == this.maxX && directionX > 0) {
            directionX = 0;
        }
        this.coordinate.changeX(directionX);
    }

    @Override
    public void stepY(int directionY) {
        if (this.coordinate.getCoor()[1] == 0 && directionY < 0) {
            directionY = 0;
        }
        if (this.coordinate.getCoor()[1] == this.maxY && directionY > 0) {
            directionY = 0;
        }
        this.coordinate.changeY(directionY);
    }
}
