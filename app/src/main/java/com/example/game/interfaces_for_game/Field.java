package com.example.game.interfaces_for_game;

import android.widget.LinearLayout;

import com.example.game.MainActivity;

import java.util.List;

//Field must be able to create and modify rows.
//Method "checkWall" checks the presence of a wall by coordinates.

public interface Field {
    // Create matrix.
    void create(MainActivity mainActivity, LinearLayout container);

    // Move rows y-1
    void change();

    boolean checkWall(int[] coordinate);

    int createId(int i, int j);

    void show(MainActivity mainActivity, List<Player> players);
}