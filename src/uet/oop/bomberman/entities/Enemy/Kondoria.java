package uet.oop.bomberman.entities.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {
    public Kondoria(Coordinate pos, Image img, Coordinate dir) {
        super(pos, img, dir);
        speed = 0.025;
        life = 1;
    }

    public Kondoria(Coordinate pos_, Image img) {
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
    public void chooseSprite() {
        if (!isKilled()) {
            if (dir.equalTo(UP)||dir.equalTo(LEFT)) {
                sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2,Sprite.kondoria_left3, animate, 20);
            } else {
                sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2,Sprite.kondoria_right3, animate, 20);
            }
        } else {
            sprite = Sprite.bombExplodeSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate);
        }
        img = sprite.getFxImage();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
