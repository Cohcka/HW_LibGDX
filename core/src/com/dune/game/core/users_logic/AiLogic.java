package com.dune.game.core.users_logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.BattleMap;
import com.dune.game.core.GameController;
import com.dune.game.core.units.AbstractUnit;
import com.dune.game.core.units.BattleTank;
import com.dune.game.core.units.Harvester;
import com.dune.game.core.units.types.Owner;
import com.dune.game.core.units.types.UnitType;

import java.util.ArrayList;
import java.util.List;

public class AiLogic extends BaseLogic {
    private float timer;

    private List<BattleTank> tmpAiBattleTanks;
    private List<Harvester> tmpAiHarvesters;
    private List<Harvester> tmpPlayerHarvesters;
    private List<BattleTank> tmpPlayerBattleTanks;
    private Vector2 tmp;

    public AiLogic(GameController gc) {
        this.gc = gc;
        this.money = 1000;
        this.unitsCount = 10;
        this.unitsMaxCount = 100;
        this.ownerType = Owner.AI;
        this.tmpAiBattleTanks = new ArrayList<>();
        this.tmpAiHarvesters = new ArrayList<>();
        this.tmpPlayerHarvesters = new ArrayList<>();
        this.tmpPlayerBattleTanks = new ArrayList<>();
        this.timer = 10000.0f;
        this.tmp = new Vector2();
    }

    public void update(float dt) {
        timer += dt;
        if (timer > 2.0f) {
            timer = 0.0f;
            gc.getUnitsController().collectTanks(tmpAiBattleTanks, gc.getUnitsController().getAiUnits(), UnitType.BATTLE_TANK);
            gc.getUnitsController().collectTanks(tmpAiHarvesters, gc.getUnitsController().getAiUnits(), UnitType.HARVESTER);
            gc.getUnitsController().collectTanks(tmpPlayerHarvesters, gc.getUnitsController().getPlayerUnits(), UnitType.HARVESTER);
            gc.getUnitsController().collectTanks(tmpPlayerBattleTanks, gc.getUnitsController().getPlayerUnits(), UnitType.BATTLE_TANK);
            for (int i = 0; i < tmpAiBattleTanks.size(); i++) {
                BattleTank aiBattleTank = tmpAiBattleTanks.get(i);
                aiBattleTank.commandAttack(findNearestTarget(aiBattleTank, tmpPlayerBattleTanks));
            }
            for (int i = 0; i < tmpAiHarvesters.size(); i++) {
                Harvester aiHarvester = tmpAiHarvesters.get(i);
                if(aiHarvester.getContainer() < aiHarvester.getContainerCapacity()){
                    aiHarvester.commandMoveTo(findNearestResource(aiHarvester));
                } else {
                    tmp.set(14*gc.getMap().CELL_SIZE+gc.getMap().CELL_SIZE/2,8*gc.getMap().CELL_SIZE-gc.getMap().CELL_SIZE/2);
                    aiHarvester.commandMoveTo(tmp);
                }
            }
        }
    }

    public <T extends AbstractUnit> T findNearestTarget(AbstractUnit currentTank, List<T> possibleTargetList) {
        T target = null;
        float minDist = 1000000.0f;
        for (int i = 0; i < possibleTargetList.size(); i++) {
            T possibleTarget = possibleTargetList.get(i);
            float currentDst = currentTank.getPosition().dst(possibleTarget.getPosition());
            if (currentDst < minDist) {
                target = possibleTarget;
                minDist = currentDst;
            }
        }
        return target;
    }

    public Vector2 findNearestResource(Harvester harvester){
        float minDist = 1000000.0f;
        tmp.set(harvester.getPosition());
        for (int i = 0; i < gc.getMap().ROWS_COUNT; i++) {
            for (int j = 0; j < gc.getMap().COLUMNS_COUNT; j++) {
                if(gc.getMap().getResourceCount(j,i)>0 && harvester.getPosition().dst(j*gc.getMap().CELL_SIZE,i*gc.getMap().CELL_SIZE)<minDist){
                    minDist = harvester.getPosition().dst(j*gc.getMap().CELL_SIZE,i*gc.getMap().CELL_SIZE);
                    tmp.set(j*gc.getMap().CELL_SIZE+gc.getMap().CELL_SIZE/2,i*gc.getMap().CELL_SIZE+gc.getMap().CELL_SIZE/2);
                }
            }
        }
        return tmp;
    }

}
