package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private List<Entity> bombs  = new ArrayList<>();
    private int maxBombs = 2;
    public static final Coordinate UP = new Coordinate(0, -1);
    public static final Coordinate DOWN = new Coordinate(0, 1);
    public static final Coordinate LEFT = new Coordinate(-1, 0);
    public static final Coordinate RIGHT = new Coordinate(1, 0);

    public static final Coordinate IDLE = new Coordinate(0, 0);
    private final Coordinate dir;
    private boolean isMoving = false;
    private double speed = 0.05;

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

    public void checkMapMoveRight() {
        double widthFrame = 24.0;
        double distance = widthFrame / (double) Sprite.SCALED_SIZE;
        int x1 = (int) (pos.x + speed);
        int x2 = (int) (pos.x + speed + distance);
        int y1 = (int) pos.y;
        int y2 = (int) (pos.y + 1);
        if (x1 >= 0 && x2 < BombermanGame.WIDTH && y1 >= 0 && y2 < BombermanGame.HEIGHT) {
            if (BombermanGame.map[y1][x2] != ' ' || BombermanGame.map[y2][x2] != ' ') {
                if (BombermanGame.map[(int) pos.y][x2] != ' ') {
                    if (pos.y == (int) pos.y) {
                        pos.x = x2 - distance;
                    } else {
                        if (pos.y - (int) pos.y >= 0.7) {
                            pos.y = (int) pos.y + 1;
                        } else {
                            pos.x = x2 - distance;
                        }
                    }
                } else if (BombermanGame.map[(int) (pos.y + 1)][x2] != 0) {
                    if (pos.y - (int) pos.y <= 0.1) {
                        pos.y = (int) pos.y;
                    } else {
                        pos.x = x2 - distance;
                    }
                }
            }
        }
    }

    public void checkMapMoveLeft() {
        int x1 = (int) (pos.x - speed);
        int y1 = (int) pos.y;
        int y2 = (int) (pos.y + 1);
        if (x1 >= 0 && x1 < BombermanGame.WIDTH && y1 >= 0 && y2 < BombermanGame.HEIGHT) {
            if (BombermanGame.map[y1][x1] != ' ' || BombermanGame.map[y2][x1] != ' ') {
                if (BombermanGame.map[(int) pos.y][x1] != ' ') {
                    if (pos.y == (int) pos.y) {
                        pos.x = x1 + 1;
                    } else {
                        if (pos.y - (int) pos.y >= 0.7) {
                            pos.y = (int) pos.y + 1;
                        } else {
                            pos.x = x1 + 1;
                        }
                    }
                } else if (BombermanGame.map[(int) (pos.y + 1)][x1] != ' ') {
                    if (pos.y - (int) pos.y <= 0.3) {
                        pos.y = (int) pos.y;
                    } else {
                        pos.x = x1 + 1;
                    }
                }
            }
        }
    }

    public void checkMapMoveUp() {
        double widthFrame = 24.0;
        double distance = widthFrame / (double) Sprite.SCALED_SIZE;
        int x1 = (int) (pos.x);
        int x2 = (int) (pos.x + distance);
        int y1 = (int) pos.y;
        int y2 = (int) (pos.y - speed);
        if (x1 >= 0 && x2 < BombermanGame.WIDTH && y1 >= 0 && y2 < BombermanGame.HEIGHT) {
            if (BombermanGame.map[y2][x1] != ' ' || BombermanGame.map[y2][x2] != ' ') {
                if (BombermanGame.map[y2][x1] != ' ') {
                    if (pos.x - (int) pos.x >= 0.7) {
                        pos.x = (int) pos.x + 1;
                    } else {
                        pos.y = y2 + 1;
                    }
                } else if (BombermanGame.map[y2][x2] != 0) {
                    if (pos.x - (int) pos.x <= 0.45) {
                        pos.x = (int) pos.x + 1 - distance;
                    } else {
                        pos.y = y2 + 1;
                    }
                }
            }
        }
    }

    public void checkMapMoveDown() {
        double widthFrame = 24.0;
        double distance = widthFrame / (double) Sprite.SCALED_SIZE;
        int x1 = (int) (pos.x);
        int x2 = (int) (pos.x + distance);
        int y1 = (int) (pos.y + speed);
        int y2 = (int) (pos.y + speed + 1);
        if (x1 >= 0 && x2 < BombermanGame.WIDTH && y1 >= 0 && y2 < BombermanGame.HEIGHT) {
            if (BombermanGame.map[y2][x1] != ' ' || BombermanGame.map[y2][x2] != ' ') {
                if (BombermanGame.map[(int) (pos.y + 1)][x1] != ' ') {
                    if (pos.x - (int) pos.x >= 0.7) {
                        pos.x = (int) pos.x + 1;
                    } else {
                        pos.y = y1;
                    }
                } else if (BombermanGame.map[(int) (pos.y + 1)][x2] != 0) {
                    if (pos.x - (int) pos.x <= 0.45) {
                        pos.x = (int) pos.x + 1 - distance;
                    } else {
                        pos.y = y1;
                    }
                }
            }
        }
    }

    /*protected boolean movable() {
        Coordinate tmp = pos.add(dir.multiple(speed));
        if (tmp.y <= 1 || tmp.y == BombermanGame.HEIGHT - 1 || tmp.x <= 1 || tmp.x == BombermanGame.WIDTH - 1) {
            return false;
        }
        return true;
    }*/

    protected void move() {
        pos = pos.add(dir.multiple(speed));
        if (dir.equalTo(RIGHT)) {
            checkMapMoveRight();
        } else if (dir.equalTo(LEFT)) {
            checkMapMoveLeft();
        } else if (dir.equalTo(UP)) {
            checkMapMoveUp();
        } else if (dir.equalTo(DOWN)) {
            checkMapMoveDown();
        }
    }

    protected void setBombs() {
        pos = pos.add(dir.multiple(speed));
        if (bombs.size() >= maxBombs) {
            return;
        }
        double posX_bomb = (int) pos.x;
        double posY_bomb = (int) pos.y;
        bombs.add(new Bomb(new Coordinate(posX_bomb,posY_bomb), Sprite.bomb.getFxImage()));
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
        else if(event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            if (event.getCode() == KeyCode.RIGHT) {
                dir.setTo(RIGHT);
            } else if (event.getCode() == KeyCode.LEFT) {
                dir.setTo(LEFT);
            } else if (event.getCode() == KeyCode.UP) {
                dir.setTo(UP);
            } else if (event.getCode() == KeyCode.DOWN) {
                dir.setTo(DOWN);
            } else if (event.getCode() == KeyCode.SPACE) {
                setBombs();
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
        bombs.forEach(g -> g.render(gc));
    }
}
