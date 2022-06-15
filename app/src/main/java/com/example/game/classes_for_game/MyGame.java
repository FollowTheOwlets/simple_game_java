package com.example.game.classes_for_game;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.game.MainActivity;
import com.example.game.classes_for_game.players.AngryPlayer;
import com.example.game.interfaces_for_game.Game;
import com.example.game.interfaces_for_game.Field;
import com.example.game.interfaces_for_game.Player;


import java.util.ArrayList;
import java.util.List;

public class MyGame implements Game {
    private final Field field;
    private final List<Player> players;
    private final Player mainPlayer;
    private int createAngryPlayerFlag;
    private boolean state;
    private final int maxX;
    private final int maxY;

    public MyGame(Player mainPlayer, Field field, int maxX, int maxY) {
        this.field = field;
        this.mainPlayer = mainPlayer;
        this.maxX = maxX;
        this.maxY = maxY;
        this.players = new ArrayList<>();
        this.players.add(mainPlayer);
        this.createAngryPlayerFlag = 0;
    }

    @Override
    public void start(MainActivity mainActivity, LinearLayout container) {
        this.field.create(mainActivity, container);
        this.state = true;
        startGame();
        startPlayer();
    }

    @Override
    public void stop() {
        this.state = false;
    }

    @Override
    public void show(MainActivity mainActivity, LinearLayout container) {
        if (this.state) {
            this.field.show(mainActivity, this.players);
        } else {
            container.removeAllViews();
            TextView over = new TextView(mainActivity);
            over.setText("Game Over");
            container.addView(over);
        }
    }

    // Concealment motion control.
    @Override
    public void playerStep(int x) {
        int[] mainPlayerCoordinate = this.mainPlayer.getCoordinate().getCoor();
        if (mainPlayerCoordinate[0] == 0 && x < 0) {
            return;
        }
        if (mainPlayerCoordinate[0] == maxX - 1 && x > 0) {
            return;
        }
        mainPlayerCoordinate[0] += x;
        if (!this.field.checkWall(mainPlayerCoordinate)) {
            this.mainPlayer.stepX(x);
        }
    }

    // TODO Updates the player's position.
    //  Stops the game when wall is on the zero line and when it coincides with the position of AngryPlayer.
    public void startPlayer() {
        Thread playerThread = new Thread(() -> {
            int[] mainPlayerCoordinate;
            while (this.state) {
                mainPlayerCoordinate = this.mainPlayer.getCoordinate().getCoor();
                if (this.field.checkWall(mainPlayerCoordinate) && mainPlayerCoordinate[1] == 0) {
                    stop();
                    break;
                } else {
                    if (!this.field.checkWall(mainPlayerCoordinate)) {
                        while (!this.field.checkWall(mainPlayerCoordinate)) {
                            this.mainPlayer.stepY(1);
                            mainPlayerCoordinate[1]++;
                        }
                    }
                    this.mainPlayer.stepY(-1);
                }
                for (Player player : players) {
                    if (mainPlayer.checkCoordinate(player)) {
                        stop();
                        break;
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        playerThread.start();
    }

    // TODO Updates the field of play
    public void startGame() {
        Thread gameThread = new Thread(() -> {
            while (this.state) {
                this.field.change();
                Player delete = null;
                for (Player player : players) {
                    if (player.getCoordinate().getCoor()[1] != 0) {
                        player.stepY(-1);
                    } else {
                        delete = player;
                    }
                }
                if (delete != null) {
                    this.players.remove(delete);
                }
                if (this.createAngryPlayerFlag == 5) {
                    AngryPlayer angryPlayer = new AngryPlayer(0, this.maxY - 1, this.maxX - 1, this.maxY - 1);
                    players.add(angryPlayer);
                    angryPlayer.start();
                    this.createAngryPlayerFlag = 0;
                } else {
                    this.createAngryPlayerFlag++;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gameThread.start();
    }
}
