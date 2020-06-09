package com.dune.game.core.buildings;

import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.GameController;
import com.dune.game.core.GameObject;
import com.dune.game.core.units.AbstractUnit;
import com.dune.game.core.units.Harvester;
import com.dune.game.core.units.Owner;

public class Platform extends GameObject {
    Owner owner;
    int container;

    public Platform(GameController gc, Owner owner, int cellX, int cellY){
        super(gc);
        this.owner = owner;
        this.container = 0;
        this.position.set(cellX*80+40, cellY*80+40);
    }

    public BuildingHeight getHeight(){
        return BuildingHeight.FLAT;
    }

    public Owner getOwner(){
        return owner;
    }

    @Override
    public void moveBy(Vector2 value){
    }

    public void update(Harvester unit){
        container += unit.dropResource(1);
    }
}
