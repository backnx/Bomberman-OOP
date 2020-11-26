package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.entities;

public class Bomber extends Entity {


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

    private int posX_bomb = 32;
    private int posY_bomb = 14;

    private List<Entity> bombs = new ArrayList<>();
    private int maxBombs = 2;

    /**
     * Hoat hinh
     */
    protected void animate() {
        if (animate < MAX_ANIMATE) {
            animate++;
        } else {
            animate = 0;
        }
    }

    public Bomber(Coordinate pos, Image img) {
        super(pos, img);
        dir = new Coordinate();
        sprite = Sprite.player_left;
    }

    @Override
    public void update() {
        animate();
        move();
    }

    public void checkMapMoveRight() {
        double distance = 24.0 / (double) Sprite.SCALED_SIZE;
        int x1 = (int) (pos.x + speed);
        int x2 = (int) (pos.x + speed + distance);
        int y1 = (int) pos.y;
        int y2 = (int) (pos.y + 1);
        if (x1 >= 0 && x2 < Map.WIDTH && y1 >= 0 && y2 < Map.HEIGHT) {
            if (Map.map[y1][x2] != ' ' || Map.map[y2][x2] != ' ') {
                if (Map.map[(int) pos.y][x2] != ' ') {
                    if (pos.y == (int) pos.y) {
                        pos.x = x2 - distance;
                    } else {
                        if (pos.y - (int) pos.y >= 0.7) {
                            pos.y = (int) pos.y + 1;
                        } else {
                            pos.x = x2 - distance;
                        }
                    }
                } else if (Map.map[(int) (pos.y + 1)][x2] != 0) {
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
        if (x1 >= 0 && x1 < Map.WIDTH && y1 >= 0 && y2 < Map.HEIGHT) {
            if (Map.map[y1][x1] != ' ' || Map.map[y2][x1] != ' ') {
                if (Map.map[(int) pos.y][x1] != ' ') {
                    if (pos.y == (int) pos.y) {
                        pos.x = x1 + 1;
                    } else {
                        if (pos.y - (int) pos.y >= 0.7) {
                            pos.y = (int) pos.y + 1;
                        } else {
                            pos.x = x1 + 1;
                        }
                    }
                } else if (Map.map[(int) (pos.y + 1)][x1] != ' ') {
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
        if (x1 >= 0 && x2 < Map.WIDTH && y1 >= 0 && y2 < Map.HEIGHT) {
            if (Map.map[y2][x1] != ' ' || Map.map[y2][x2] != ' ') {
                if (Map.map[y2][x1] != ' ') {
                    if (pos.x - (int) pos.x >= 0.7) {
                        pos.x = (int) pos.x + 1;
                    } else {
                        pos.y = y2 + 1;
                    }
                } else if (Map.map[y2][x2] != 0) {
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
        if (x1 >= 0 && x2 < Map.WIDTH && y1 >= 0 && y2 < Map.HEIGHT) {
            if (Map.map[y2][x1] != ' ' || Map.map[y2][x2] != ' ') {
                if (Map.map[(int) (pos.y + 1)][x1] != ' ') {
                    if (pos.x - (int) pos.x >= 0.7) {
                        pos.x = (int) pos.x + 1;
                    } else {
                        pos.y = y1;
                    }
                } else if (Map.map[(int) (pos.y + 1)][x2] != 0) {
                    if (pos.x - (int) pos.x <= 0.45) {
                        pos.x = (int) pos.x + 1 - distance;
                    } else {
                        pos.y = y1;
                    }
                }
            }
        }
    }

    protected boolean movable(Coordinate des) {
        Entity entity = getObject(des);
        if (entity == null) {
            return true;
        }
        if (new Rectangle(pos.x, pos.y, 1, 1).intersects(entity.getX(), entity.getY(), 1, 1)) {

            if (entity instanceof Wall) {
                return false;
            }
            if (entity instanceof Brick) {
                return false;
            }
        }
        return true;
    }

    protected void move() {
        if (movable(pos.add(dir.multiple(speed)))) {
            pos = pos.add(dir.multiple(speed));
        }

        /*
        if (dir.equalTo(RIGHT)) {
            checkMapMoveRight();
        } else if (dir.equalTo(LEFT)) {
            checkMapMoveLeft();
        } else if (dir.equalTo(UP)) {
            checkMapMoveUp();
        } else if (dir.equalTo(DOWN)) {
            checkMapMoveDown();
        }

         */
    }

    protected void setBombs() {
        /*if (bombs.size() >= maxBombs) {
            return;
        }*/
        if (posX_bomb != 32 && posY_bomb != 14) {
            Map.map[posY_bomb][posX_bomb] = ' ';
        }
        posX_bomb = (int) Math.round(pos.x);
        posY_bomb = (int) Math.round(pos.y);
        if (Map.map[posY_bomb][posX_bomb] != ' ') {
            return;
        }
        bombs.add(new Bomb(new Coordinate(posX_bomb, posY_bomb), Sprite.bomb.getFxImage()));
        Map.map[posY_bomb][posX_bomb] = 'b';
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
                dir.x = 0;
            } else if (event.getCode() == KeyCode.LEFT) {
                dir.x = 0;
            } else if (event.getCode() == KeyCode.UP) {
                dir.y = 0;
            } else if (event.getCode() == KeyCode.DOWN) {
                dir.y = 0;
            }
            isMoving = false;
        } else if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
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
            isMoving = true;
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
        img = sprite.getFxImage();
    }

    private Entity getObject(Coordinate des) {
        //System.out.println(thisTileX + " " + thisTileY);
        for (Entity entity : entities) {
            if (Coordinate.round(des).equalTo(entity.getPos())) {
                return entity;
            }
        }

        return null;
    }

    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        gc.drawImage(img, pos.x * Sprite.SCALED_SIZE, pos.y * Sprite.SCALED_SIZE);
        bombSprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 20);
        _img = bombSprite.getFxImage();
        gc.drawImage(_img, posX_bomb * Sprite.SCALED_SIZE, posY_bomb * Sprite.SCALED_SIZE);
    }
}
