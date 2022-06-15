package com.example.game.exeptions;

// Errors for work with Coor.
public class NoRightCoordinate extends RuntimeException{

    public NoRightCoordinate(String message){
        super("Несоответствующие координаты у игрока. " + message);
    }
}
