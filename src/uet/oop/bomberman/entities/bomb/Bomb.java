package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    protected double timeToExplore = 120;
    protected int animate = 0;
    protected final int MAX_ANIMATE = 7500;

    public Bomb(Coordinate pos, Image img) {
        super(pos, img);
        sprite=Sprite.bomb;
    }

    @Override
    public void update() {
        if (timeToExplore > 0) {
            timeToExplore--;
        }
    }

    @Override
    public void handleEvent(KeyEvent event) {

    }

    public void render(GraphicsContext gc) {
    }
}

