package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.game.classes_for_game.MyField;
import com.example.game.classes_for_game.MyGame;
import com.example.game.classes_for_game.players.MainPlayer;
import com.example.game.interfaces_for_game.Field;
import com.example.game.interfaces_for_game.Game;
import com.example.game.interfaces_for_game.Player;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    // Field's container
    private LinearLayout fieldLayout;

    // Game's state
    private boolean state;

    // Main objects
    private Player player;
    private Field field;
    private Game game;

    // Size
    private static final int MAX_Y = 24;
    private static final int MAX_X = 12;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException ignored) {
        }

        this.state = false;
        fieldLayout = (LinearLayout) findViewById(R.id.field);

        Button startBtn = (Button) findViewById(R.id.start);
        startBtn.setOnClickListener(v -> {
            if (this.state) {
                return;
            } else {
                this.state = true;
            }
            this.fieldLayout.removeAllViews();
            this.player = new MainPlayer(MAX_X / 2, MAX_Y - 1, MAX_X - 1, MAX_Y - 1);
            this.field = new MyField(MAX_X, MAX_Y);
            this.game = new MyGame(this.player, this.field, MAX_X, MAX_Y);
            this.game.start(this, findViewById(R.id.field));

            // The screen is updated recursively (with a delay so as not to overflow the stack).
            if (this.state) {
                this.fieldLayout.postDelayed(() -> {
                    show(game);
                }, 50);
            }
        });

        Button stopBtn = (Button) findViewById(R.id.stop);
        stopBtn.setOnClickListener(v -> {
            game.stop();
            this.state = false;
            fieldLayout.removeAllViews();
        });

        Button leftBtn = (Button) findViewById(R.id.left);
        Button rightBtn = (Button) findViewById(R.id.right);

        //Move's flag
        AtomicBoolean goFlag = new AtomicBoolean(true);

        // An alternative Thread.sleep, so as not to transfer control
        leftBtn.setOnTouchListener((v, event) -> {
            Thread thread = new Thread(() -> {
                double inc = 0.00001;
                while (goFlag.get()) {
                    if (this.state) {
                        this.game.playerStep(-(int) inc);
                        if (((int) inc) == 1) {
                            inc = 0.00001;
                        }
                    }
                    inc += 0.00001;
                }
            });
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    goFlag.set(true);
                    thread.start();
                    break;
                case MotionEvent.ACTION_UP:
                    goFlag.set(false);
                    break;
            }
            return false;
        });

        rightBtn.setOnTouchListener((v, event) -> {
            Thread thread = new Thread(() -> {
                double inc = 0.00001;
                while (goFlag.get()) {
                    if (this.state) {
                        this.game.playerStep((int) inc);
                        if (((int) inc) == 1) {
                            inc = 0.00001;
                        }
                    }
                    inc += 0.00001;
                }
            });
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    goFlag.set(true);
                    thread.start();
                    break;
                case MotionEvent.ACTION_UP:
                    goFlag.set(false);
                    break;
            }
            return false;
        });

    }

    private void show(Game game) {
        game.show(this, fieldLayout);
        if (this.state) {
            this.fieldLayout.postDelayed(() -> {
                show(game);
            }, 50);
        }
    }
}