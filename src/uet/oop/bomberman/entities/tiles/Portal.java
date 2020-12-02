package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Entity;

public class Portal extends Entity {

    public Portal(Coordinate pos, Image img) {
        super(pos, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void handleEvent(KeyEvent event) {

    }
}
