package com.example.game.classes_for_game;

import android.util.Log;

import com.example.game.exeptions.NoRightCoordinate;

public class Coor {
    private int[] coor;
    private int maxX;
    private int maxY;

    public Coor(int x, int y, int maxX, int maxY) {
        if (x < 0 || y < 0) {
            throw new NoRightCoordinate(0 + "");
        }

        this.coor = new int[]{x, y};
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void changeX(int direction) {
        if (coor[0] + direction < 0 || coor[0] + direction > this.maxX) {
            throw new NoRightCoordinate((coor[0] + direction) + " -x");
        }

        this.coor[0] += direction;
    }

    public void changeY(int direction) {
        if (coor[1] + direction < 0 || coor[1] + direction > this.maxY) {
            throw new NoRightCoordinate((coor[1] + direction) + " -y");

        }
        this.coor[1] += direction;
    }

    public int[] getCoor() {
        return this.coor.clone();
    }
}
