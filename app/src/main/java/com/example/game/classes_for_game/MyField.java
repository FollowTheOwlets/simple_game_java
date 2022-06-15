package com.example.game.classes_for_game;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.game.MainActivity;
import com.example.game.R;
import com.example.game.interfaces_for_game.Field;
import com.example.game.interfaces_for_game.Player;

import java.util.List;

public class MyField implements Field {
    private int width;
    private int height;
    private int[][] map;

    private static int WALL = 1;
    private static int WAY = 0;

    public MyField(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new int[height][width];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.map[i][j] = WAY;
            }
        }

    }

    @Override
    public void create(MainActivity mainActivity, LinearLayout container) {
        LinearLayout field = new LinearLayout(mainActivity);
        field.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        field.setLayoutParams(params);
        for (int i = 0; i < this.height; i++) {
            field.addView(createRow(mainActivity, i));
        }
        container.addView(field);
    }

    @Override
    public void change() {
        for (int i = 0; i < this.height - 1; i++) {
            for (int j = 0; j < this.width; j++) {
                this.map[i][j] = this.map[i + 1][j];
            }
        }

        int newDoor = (int) (Math.random() * (this.width - 1));
        if (this.map[this.height - 1][this.width - 1] == 0 && this.map[this.height - 1][0] == 0) {
            for (int i = 0; i < this.width; i++) {
                if (i != newDoor && i != newDoor + 1) {
                    this.map[this.height - 1][i] = WALL;
                } else {
                    this.map[this.height - 1][i] = WAY;
                }
            }
        } else {
            for (int i = 0; i < this.width; i++) {
                this.map[this.height - 1][i] = WAY;
            }
        }

    }

    @Override
    public boolean checkWall(int[] coordinate) {
        return coordinate[1] == this.height
                || coordinate[0] == this.width
                || this.map[coordinate[1]][coordinate[0]] == 1;
    }

    @Override
    public void show(MainActivity mainActivity, List<Player> players) {
        for (int i = 0; i < this.height - 1; i++) {
            for (int j = 0; j < this.width; j++) {
                LinearLayout point = (LinearLayout) mainActivity.findViewById(createId(i, j));
                if (map[i][j] == WALL) {
                    point.setBackgroundResource(R.drawable.wall);
                } else {
                    point.setBackgroundResource(R.drawable.way);
                }
            }
        }
        for (Player player : players) {
            int[] coordinate = player.getCoordinate().getCoor();
            LinearLayout point = (LinearLayout) mainActivity.findViewById(createId(coordinate[1], coordinate[0]));
            if (coordinate[1] != this.height - 1){
                player.show(point);
            }
        }
    }

    private LinearLayout createRow(MainActivity mainActivity, int i) {
        LinearLayout row = new LinearLayout(mainActivity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(params);
        for (int j = 0; j < this.width; j++) {
            row.addView(createPoint(mainActivity, i, j));
        }
        return row;
    }

    private LinearLayout createPoint(MainActivity mainActivity, int i, int j) {
        LinearLayout point = new LinearLayout(mainActivity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60, 60);
        point.setLayoutParams(params);
        point.setId(createId(i, j));
        return point;
    }

    public int createId(int i, int j) {
        return i * 1000 + j;
    }
}
