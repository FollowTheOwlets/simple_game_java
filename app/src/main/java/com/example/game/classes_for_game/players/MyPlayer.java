package com.example.game.classes_for_game.players;

import android.widget.LinearLayout;

import com.example.game.R;
import com.example.game.classes_for_game.Coor;
import com.example.game.interfaces_for_game.Player;

public abstract class MyPlayer implements Player {
    protected Coor coordinate;
    protected int maxX;
    protected int maxY;

    public MyPlayer(int x, int y, int maxX, int maxY) {
        this.coordinate = new Coor(x, y, maxX, maxY);
        this.maxX = maxX;
        this.maxY = maxY;
    }

    @Override
    public abstract void stepX(int directionX);

    @Override
    public abstract void stepY(int directionY);

    @Override
    public Coor getCoordinate() {
        return this.coordinate;
    }

    @Override
    public boolean checkCoordinate(Player player) {
        if (player == this) {
            return false;
        }
        int[] firstCoordinate = this.coordinate.getCoor();
        int[] secondCoordinate = player.getCoordinate().getCoor();
        return firstCoordinate[0] == secondCoordinate[0] && firstCoordinate[1] == secondCoordinate[1];
    }

    @Override
    public void show(LinearLayout point) {
        point.setBackgroundResource(R.drawable.player);
    }
}