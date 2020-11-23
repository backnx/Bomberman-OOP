package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    protected double timeToExplore = 120;

    public Bomb(Coordinate pos, Image img) {
        super(pos, img);
    }

    @Override
    public void update() {
        if (timeToExplore > 0) {
            timeToExplore--;
        }
    }

    @Override
    public void handleEvent(KeyEvent event) {

    }

}

