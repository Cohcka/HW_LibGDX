package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.dune.game.core.buildings.Platform;
import com.dune.game.core.units.AbstractUnit;
import com.dune.game.core.units.Harvester;
import com.dune.game.core.units.Owner;
import com.dune.game.core.units.UnitType;

import java.util.List;

public class BuildingsController {
    GameController gc;
    Platform[] platforms;
    List<AbstractUnit> aiUnits;
    List<AbstractUnit> playerUnits;


    public BuildingsController(GameController gc){
        this.gc = gc;
        platforms = new Platform[2];
        platforms[0] = new Platform(gc, Owner.AI, 1, 4);
        platforms[1] = new Platform(gc, Owner.PLAYER,5,5);
    }

    public void update(float dt) {
        aiUnits = gc.getUnitsController().getAiUnits();
        playerUnits = gc.getUnitsController().getPlayerUnits();
        for (int i = 0; i < platforms.length; i++) {
            checkUnits(platforms[i]);
        }
    }

    public void checkUnits(Platform platform){
        if(platform.getOwner() == Owner.AI){
            for (int i = 0; i < aiUnits.size(); i++) {
                if(aiUnits.get(i).getUnitType() == UnitType.HARVESTER && aiUnits.get(i).getCellY() == platform.getCellY() && aiUnits.get(i).getCellX() == platform.getCellX()){
                    platform.update((Harvester) aiUnits.get(i));
                }

            }
        } else {
            for (int i = 0; i < playerUnits.size(); i++) {
                if(playerUnits.get(i).getUnitType() == UnitType.HARVESTER && playerUnits.get(i).getCellY() == platform.getCellY() && playerUnits.get(i).getCellX() == platform.getCellX()){
                    platform.update((Harvester) playerUnits.get(i));
                }

            }
        }
    }

    public void render(SpriteBatch batch){
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].render(batch);
        }
    }
}
