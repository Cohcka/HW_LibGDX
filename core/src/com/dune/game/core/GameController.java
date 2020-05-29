package com.dune.game.core;

import com.badlogic.gdx.math.Vector2;

public class GameController {
    private BattleMap map;
    private ProjectilesController projectilesController;
    private TanksController tanksController;
    private PlayerControl control;

    public TanksController getTanksController() {
        return tanksController;
    }

    public ProjectilesController getProjectilesController() {
        return projectilesController;
    }

    public BattleMap getMap() {
        return map;
    }

    // Инициализация игровой логики
    public GameController() {
        Assets.getInstance().loadAssets();
        this.map = new BattleMap();
        this.projectilesController = new ProjectilesController(this);
        this.tanksController = new TanksController(this);
        this.tanksController.setup(200, 200, Tank.Owner.PLAYER);
        this.tanksController.setup(400, 400, Tank.Owner.PLAYER);
        this.control = new PlayerControl(this);
    }

    public void update(float dt) {
        tanksController.update(dt);
        projectilesController.update(dt);
        map.update(dt);
        checkCollisions(dt);
        control.controlListener();
    }

    public void checkCollisions(float dt) {
        for (int i = tanksController.activeList.size()-1; i >= 0; i--) {
            for (int j = tanksController.activeList.size()-1; j >= 0; j--) {
                if(i != j){
                    if(checkCoordinates(tanksController.activeList.get(i).getPosition(), tanksController.activeList.get(j).getPosition(),80)){
                        tanksController.activeList.get(j).blockMove();
                    }
                }
            }
        }
    }

    public void switchSelected(Vector2 startPos, Vector2 endPos){
        for (int i = tanksController.activeList.size()-1; i >= 0; i--) {
            if(tanksController.activeList.get(i).getPosition().x > startPos.x - 40
                    && tanksController.activeList.get(i).getPosition().x < endPos.x + 40
                    && tanksController.activeList.get(i).getPosition().y > startPos.y - 40
                    && tanksController.activeList.get(i).getPosition().y < endPos.y + 40){
                tanksController.activeList.get(i).setSelected(true);
            }else{
                tanksController.activeList.get(i).setSelected(false);
            }
        }
    }

    protected boolean checkCoordinates (Vector2 obj1, Vector2 obj2, int distance){
        return(Math.abs(obj1.x-obj2.x) < distance && Math.abs(obj1.y-obj2.y) < distance);
    }
}
