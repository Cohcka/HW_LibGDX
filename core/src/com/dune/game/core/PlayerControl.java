package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PlayerControl {
    GameController gc;
    Vector2 posPressedLBM;
    Vector2 startPosLBM;
    Vector2 endPosLBM;
    float tmp;


    public PlayerControl(GameController gc) {
        this.gc = gc;
        posPressedLBM = new Vector2();
        startPosLBM = new Vector2();
        endPosLBM = new Vector2();
    }

    public void controlListener() {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && startPosLBM.isZero()) {
            startPosLBM.set(Gdx.input.getX(),720 - Gdx.input.getY());
            posPressedLBM.set(Gdx.input.getX(),720 - Gdx.input.getY());
        } else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !startPosLBM.isZero()){
            posPressedLBM.set(Gdx.input.getX(),720 - Gdx.input.getY());
        } else if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !startPosLBM.isZero()){
            endPosLBM.set(posPressedLBM);
            orderingCoordinates();
            gc.switchSelected(startPosLBM, endPosLBM);
            startPosLBM.set(0,0);
            endPosLBM.set(0,0);
        }
    }

    private void orderingCoordinates(){
        if(startPosLBM.x > endPosLBM.x){
            tmp = startPosLBM.x;
            startPosLBM.x = endPosLBM.x;
            endPosLBM.x = tmp;
        }
        if(startPosLBM.y > endPosLBM.y){
            tmp = startPosLBM.y;
            startPosLBM.y = endPosLBM.y;
            endPosLBM.y = tmp;
        }
    }
}
