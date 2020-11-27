package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Entity;

public abstract class Item extends Entity {
    private boolean isCollision = false;

    public Item(Coordinate pos, Image img) {
        super(pos, img);
        rtg= new Rectangle(pos.getX(), pos.getY(), 0.98, 0.98);
    }


    //Collision Bomber

    public boolean collision(Entity entities) {
        rtg.setX(pos.getX());
        rtg.setY(pos.getY());
        if (this.rtg.intersects(entities.rtg.getLayoutBounds())) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {

    }
}
