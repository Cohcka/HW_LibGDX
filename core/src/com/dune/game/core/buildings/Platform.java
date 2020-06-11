package com.dune.game.core.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.Assets;
import com.dune.game.core.GameController;
import com.dune.game.core.GameObject;
import com.dune.game.core.units.AbstractUnit;
import com.dune.game.core.units.Harvester;
import com.dune.game.core.units.Owner;

public class Platform extends GameObject {
    Owner owner;
    int container;
    TextureRegion texture;

    public Platform(GameController gc, Owner owner, int cellX, int cellY){
        super(gc);
        this.owner = owner;
        this.texture = Assets.getInstance().getAtlas().findRegion("platform");
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
        if(unit.dropResource()){
            gc.addMoney(1, unit.getOwnerType());
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, position.x - 40, position.y - 40, 80, 80);
    }
}
