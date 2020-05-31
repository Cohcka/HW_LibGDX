package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ProjectilesController extends ObjectPool<Projectile> {
    private GameController gc;
    private TextureRegion projectileTexture;
    private Vector2 tmp;

    @Override
    protected Projectile newObject() {
        return new Projectile(gc);
    }

    public ProjectilesController(GameController gc) {
        this.gc = gc;
        this.projectileTexture = Assets.getInstance().getAtlas().findRegion("bullet");
        this.tmp = new Vector2();
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
    }

    public void setup(Vector2 srcPosition, float angle) {
        Projectile p = activateObject();
        p.setup(srcPosition, angle, projectileTexture);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
            tmp.set(activeList.get(i).getPosition());
            if (tmp.x < 0 || tmp.x > 1280 || tmp.y < 0 || tmp.y > 720 || gc.checkObjectByCO(tmp, 8, 20)) {
                activeList.get(i).deactivate();
            }
        }
        checkPool();
    }
}
