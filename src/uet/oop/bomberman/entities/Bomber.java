package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;

public class Bomber extends Entity {
    public static final Coordinate UP = new Coordinate(0, -1);
    public static final Coordinate DOWN = new Coordinate(0, 1);
    public static final Coordinate LEFT = new Coordinate(-1, 0);
    public static final Coordinate RIGHT = new Coordinate(1, 0);

    public static final Coordinate IDLE = new Coordinate(0, 0);
    private final Coordinate dir;
    private double speed = 0.5;

    public Bomber(Coordinate pos, Image img) {
        super(pos, img);
        dir = new Coordinate();
    }

    @Override
    public void update() {
        move();
    }
    public boolean movable(){
        Coordinate tmp=pos.add(dir.multiple(speed));
        if (tmp.y == 0 || tmp.y == BombermanGame.HEIGHT - 1 || tmp.x == 0 || tmp.x == BombermanGame.WIDTH - 1) {
            return false;
        }
        return true;
    }
    public void move() {
        if (movable()) pos=pos.add(dir.multiple(speed));

    }

    @Override
    public void setDir(Coordinate dir) {
        this.dir.setTo(dir);
    }

    @Override
    public Coordinate getDir() {
        return dir;
    }

    @Override
    public void handleEvent(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            if (event.getCode() == KeyCode.RIGHT) {
                dir.x=0;
            } else if (event.getCode() == KeyCode.LEFT) {
                dir.x=0;
            } else if (event.getCode() == KeyCode.UP) {
                dir.y=0;
            } else if (event.getCode() == KeyCode.DOWN) {
                dir.y=0;
            }
        }
        else if(event.getEventType().equals(KeyEvent.KEY_PRESSED)){
            if (event.getCode() == KeyCode.RIGHT) {
                dir.setTo(RIGHT);
            } else if (event.getCode() == KeyCode.LEFT) {
                dir.setTo(LEFT);
            } else if (event.getCode() == KeyCode.UP) {
                dir.setTo(UP);
            } else if (event.getCode() == KeyCode.DOWN) {
                dir.setTo(DOWN);
            }
        }
    }
}
