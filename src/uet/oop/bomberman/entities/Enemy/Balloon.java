package uet.oop.bomberman.entities.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.Coordinate;

public class Balloon extends Enemy {
    public Balloon(Coordinate pos, Image img, Coordinate dir) {
        super(pos, img, dir);
        speed = 0.025;
        life = 1;
    }

    public Balloon(Coordinate pos_, Image img) {
        super(pos_, img);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void handleEvent(KeyEvent event) {

    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
