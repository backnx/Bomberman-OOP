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

    public boolean collision(Entity entity) {
/*
        entity.rtg.setY(entity.getY());
        entity.rtg.setX(entity.getX());
        //System.out.println(this.rtg.intersects(entity.rtg.getLayoutBounds()));
        return this.rtg.intersects(entity.rtg.getLayoutBounds());
*/
        return checkCollision(this, entity);
    }

    @Override
    public void update() {

    }
}
