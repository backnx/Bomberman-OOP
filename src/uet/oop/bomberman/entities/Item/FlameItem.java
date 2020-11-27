package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.Coordinate;

import java.awt.*;

public class FlameItem extends Item{

    public FlameItem(Coordinate pos, Image img) {
        super(pos, img);
    }

    @Override
    public void handleEvent(KeyEvent event) {

    }

//    @Override
//    public void update() {
//
//    }
}
