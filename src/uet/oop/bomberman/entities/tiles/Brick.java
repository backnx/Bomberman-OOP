package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.Item;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {

    private Item item = null;
    private boolean damaged = false;
    private boolean done = false;
    private int deathCountDown = 90;

    public Brick(Coordinate pos, Image img) {
        super(pos, img);
        rtg = new Rectangle(pos.getX(), pos.getY(), 0.99, 0.99);
    }

    public Brick(Coordinate pos, Image img, Item item_) {
        super(pos, img);
        item = item_;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void collapsingImg() {
        if (deathCountDown == 0) {
            this.img = null;
            setDone(true);
        } else {
            this.img = Sprite
                    .bombExplodeSprite(Sprite.brick_exploded2, Sprite.brick_exploded1, Sprite.brick_exploded, deathCountDown)
                    .getFxImage();
            deathCountDown--;
        }
    }

    @Override
    public void update() {
        if (this.isDamaged()) {
            collapsingImg();
        }
    }

    @Override
    public void handleEvent(KeyEvent event) {

    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
