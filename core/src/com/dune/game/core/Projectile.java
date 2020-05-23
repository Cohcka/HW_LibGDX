package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Projectile {

    Boolean init = false;
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private TextureRegion texture;


    public Projectile(TextureAtlas atlas){
        this.texture = new TextureRegion(atlas.findRegion("bullet"));
    }

    public void setup(Vector2 startPosition, float angle) {
        velocity.set(900.0f * MathUtils.cosDeg(angle), 900.0f * MathUtils.sinDeg(angle));
        this.position.set(startPosition.x - 16 + (40.0f * MathUtils.cosDeg(angle)), startPosition.y - 16 + (40.0f * MathUtils.sinDeg(angle)));
        this.init = true;
    }

    public void update(float dt) {
        if(init) {
            checkFrame();
            // position.x += velocity.x * dt;
            // position.y += velocity.y * dt;
            this.position.mulAdd(velocity, dt);
        }
    }

    public void checkFrame(){
        if(position.x < 0 || position.x > 1280 || position.y < 0 || position.y > 720){
            init = false;
        }
    }

    public void render(SpriteBatch batch) {
        if(init) {
            batch.draw(texture, position.x, position.y, 32, 32);
        }
    }

    public boolean getInit(){
        return init;
    }
}
