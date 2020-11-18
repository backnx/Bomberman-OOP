package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    public static final Coordinate UP = new Coordinate(0, -1);
    public static final Coordinate DOWN = new Coordinate(0, 1);
    public static final Coordinate LEFT = new Coordinate(-1, 0);
    public static final Coordinate RIGHT = new Coordinate(1, 0);

    public static final Coordinate IDLE = new Coordinate(0, 0);
    private final Coordinate dir;
    private boolean isMoving=false;
    private double speed = 0.1;

    protected int animate = 0;
    protected final int MAX_ANIMATE = 7500;

    /**
     * Hoat hinh
     */
    protected void animate() {
        if(animate < MAX_ANIMATE) animate++;
        else animate = 0;
    }

    public Bomber(Coordinate pos, Image img) {
        super(pos, img);
        dir = new Coordinate();
    }

    @Override
    public void update() {
        animate();
        move();
    }
    protected boolean movable(){
        Coordinate tmp=pos.add(dir.multiple(speed));
        if (tmp.y == 0 || tmp.y == BombermanGame.HEIGHT - 1 || tmp.x == 0 || tmp.x == BombermanGame.WIDTH - 1) {
            return false;
        }
        return true;
    }
    protected void move() {
        if (movable()) pos=pos.add(dir.multiple(speed));
    }

    @Override
    protected void setDir(Coordinate dir) {
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
            isMoving=false;
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
            isMoving=true;
        }
    }
    private void chooseSprite() {
        if (dir.equalTo(UP)) {
            sprite = Sprite.player_up;
            if (isMoving) {
                sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
            }
        } else if (dir.equalTo(RIGHT)) {
            sprite = Sprite.player_right;
            if (isMoving) {
                sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
            }
        } else if (dir.equalTo(DOWN)) {
            sprite = Sprite.player_down;
            if (isMoving) {
                sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
            }
        } else if (dir.equalTo(LEFT)) {
            sprite = Sprite.player_left;
            if (isMoving) {
                sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
            }
        } else {
            sprite = Sprite.player_right;
            if (isMoving) {
                sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
            }
        }
        img=sprite.getFxImage();
    }

    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        gc.drawImage(img, pos.x * Sprite.SCALED_SIZE, pos.y * Sprite.SCALED_SIZE);
    }
}
