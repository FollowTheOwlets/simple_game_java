package com.example.game.interfaces_for_game;

import android.widget.LinearLayout;

import com.example.game.MainActivity;

public interface Game {

    void start(MainActivity mainActivity, LinearLayout container);

    void show(MainActivity mainActivity, LinearLayout container);

    void stop();

    void playerStep(int x);
}
