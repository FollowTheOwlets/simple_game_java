package com.example.game.interfaces_for_game;

import android.widget.LinearLayout;

import com.example.game.classes_for_game.Coor;
import com.example.game.classes_for_game.players.MyPlayer;

//Player can move on X line or Y and check overlapping with another player.
//Player can also be shown on the field.

public interface Player {

    void stepX(int directionX);

    void stepY(int directionY);

    Coor getCoordinate();

    boolean checkCoordinate(Player player);

    void show(LinearLayout point);
}
