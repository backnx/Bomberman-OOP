package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Coordinate;

public class Balloon extends Enemy {
    public Balloon(Coordinate pos, Image img, Coordinate dir) {
        super(pos, img, dir);
        speed = 0.025;
        life = 1;
    }
}
