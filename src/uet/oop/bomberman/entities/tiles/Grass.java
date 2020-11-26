package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Grass extends Entity {

    public Grass(Coordinate pos, Image img) {
        super(pos, img);
        sprite= Sprite.grass;
    }

    @Override
    public void update() {

    }

    @Override
    public void handleEvent(KeyEvent event) {

    }
}
