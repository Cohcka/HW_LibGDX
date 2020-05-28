package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class BattleMap {
    private TextureRegion grassTexture;
    private TextureRegion flowerTexture;
    private int map [][];
    int grass = 0;
    int flower = 1;
    Random random;

    public BattleMap() {
        this.grassTexture = Assets.getInstance().getAtlas().findRegion("grass");
        this.flowerTexture = Assets.getInstance().getAtlas().findRegion("flower");
        map = new int[16][9];
        random = new Random();
        mapGenerator();

    }


    private void mapGenerator(){
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if(random.nextInt(3) == 1){
                    map[i][j] = flower;
                }
            }
        }
    }

    public void flowerCheck(Vector2 objPosition){
        int posX = (int) objPosition.x/80;
        int posY = (int) objPosition.y/80;
        if(map[posX][posY] == flower){
            map[posX][posY] = grass;
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (map[i][j] == 1){
                    batch.draw(flowerTexture, i * 80, j * 80);
                }else{
                    batch.draw(grassTexture, i * 80, j * 80);
                }
            }
        }
    }
}
