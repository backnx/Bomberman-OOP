package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.Coordinate;

public class SpeedItem extends Item{

    public SpeedItem(Coordinate pos, Image img) {
        super(pos, img);
    }

    @Override
    public void handleEvent(KeyEvent event) {

    }
}
