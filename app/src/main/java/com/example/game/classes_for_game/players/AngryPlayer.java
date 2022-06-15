package com.example.game.classes_for_game.players;

import android.widget.LinearLayout;

import com.example.game.R;

public class AngryPlayer extends MyPlayer {
    private int inc;

    public AngryPlayer(int x, int y, int maxX, int maxY) {
        super(x, y, maxX, maxY);
    }

    @Override
    public void stepX(int directionX) {
        this.coordinate.changeX(directionX);
    }

    @Override
    public void stepY(int directionY) {
        this.coordinate.changeY(directionY);
    }

    @Override
    public void show(LinearLayout point){
        point.setBackgroundResource(R.drawable.angry_player);
    }

    // Player behavior regardless of the field.
    public void start() {
        Thread thread = new Thread(() -> {
            this.inc = 1;
            while (this.coordinate.getCoor()[1] != 0) {
                if (this.coordinate.getCoor()[0] == this.maxX) {
                    this.inc = -1;
                }
                stepX(inc);
                if (this.coordinate.getCoor()[0] == 0) {
                    this.inc = 1;
                }
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        thread.start();
    }

}
