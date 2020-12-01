package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.Coordinate;

public class Doll extends Enemy {
    public Doll(Coordinate pos, Image img, Coordinate dir) {
        super(pos, img, dir);
        speed = 0.025;
        life = 1;
    }

    public Doll(Coordinate pos_, Image img) {
        super(pos_, img);
    }

    @Override
    public void handleEvent(KeyEvent event) {

    }
}
