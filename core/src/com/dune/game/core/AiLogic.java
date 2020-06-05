package com.dune.game.core;

import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.units.AbstractUnit;
import com.dune.game.core.units.UnitType;

import java.util.List;

public class AiLogic {
    private GameController gc;
    private List<AbstractUnit> units;
    private List<AbstractUnit> enemy;
    private BattleMap map;
    private Vector2 tmp;
    private float tmp2;


    public AiLogic (GameController gc){
        this.gc = gc;
        map = gc.getMap();
    }

    public void update (float dt){
        units = gc.getUnitsController().getAiUnits();
        enemy = gc.getUnitsController().getPlayerUnits();
        tmp = new Vector2();
        for (int i = 0; i < units.size(); i++) {
            if(units.get(i).getUnitType() == UnitType.HARVESTER){
                commandHarvestResource(units.get(i));
            } else if (units.get(i).getUnitType() == UnitType.BATTLE_TANK){
                commandAttackTarget(units.get(i));
            }
        }
    }

    public void commandAttackTarget (AbstractUnit unit){
        tmp2 = 999;
        for (int i = 0; i < enemy.size(); i++) {
            if(unit.getPosition().dst(enemy.get(i).getPosition()) < tmp2){
                tmp2 = unit.getPosition().dst(enemy.get(i).getPosition());
                unit.commandAttack(enemy.get(i));
            }
        }
    }

    public void commandDefendTarget (){

    }

    public void commandHarvestResource (AbstractUnit unit){
        unit.commandMoveTo(findNearestResource(unit.getPosition()));
    }

    public Vector2 findNearestResource (Vector2 unitPosition){
        tmp2 = 999;

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if((map.getResourceCount(i, j) > 0) && (unitPosition.dst(i*80, j*80) < tmp2)){
                    tmp2 = unitPosition.dst(i*80 + 40, j*80 + 40);
                    tmp.set(i*80 + 40, j*80 + 40);
                }
            }
        }

        if (tmp2 != 999){
            return tmp;
        } else {
            return unitPosition;
        }
    }
}
