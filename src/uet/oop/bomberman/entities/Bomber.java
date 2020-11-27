package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.*;

public class Bomber extends Entity {
    public static final Coordinate UP = new Coordinate(0, -1);
    public static final Coordinate DOWN = new Coordinate(0, 1);
    public static final Coordinate LEFT = new Coordinate(-1, 0);
    public static final Coordinate RIGHT = new Coordinate(1, 0);

    private final Coordinate dir;
    private boolean isMoving = false;
    private double speed;
    private boolean killed = false;
    private int bombRange = 1;
    public int bombLimit = 1;

    protected int animate = 0;
    protected final int MAX_ANIMATE = 7500;

    private int posX_bomb = 32;
    private int posY_bomb = 14;

    private List<Bomb> bombs = new ArrayList<>();
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
        speed=0.05;
    }

    @Override
    public void update() {
        animate();
        move();
        if (!bombs.isEmpty()) {
            if (bombs.get(bombs.size() - 1).isExploded()) {
                bombs.remove(bombs.size() - 1);
            }
        }
    }

    public void checkMapMoveRight() {
        double distance = 24.0 / (double) Sprite.SCALED_SIZE;
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
        /*if (bombs.size() >= maxBombs) {
            return;
        }*/
        if (posX_bomb != 32 && posY_bomb != 14) {
            map[posY_bomb][posX_bomb] = ' ';
        }
        posX_bomb = (int) Math.round(pos.x);
        posY_bomb = (int) Math.round(pos.y);
        if (map[posY_bomb][posX_bomb] != ' ') {
            return;
        }
        bombs.add(new Bomb(new Coordinate(posX_bomb, posY_bomb), Sprite.bomb.getFxImage()));
        map[posY_bomb][posX_bomb] = 'b';
        flames.addAll(new Bomb(new Coordinate(posX_bomb, posY_bomb), Sprite.bomb.getFxImage()).getFlames());
    }

    public Bomb placeBomb() {
        Bomb bom = new Bomb(Coordinate.round(pos),
                Sprite.bomb.getFxImage(), this.bombRange);
        this.bombs.add(bom);
        return bom;
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
            switch (event.getCode()) {
                case RIGHT:
                    dir.setTo(RIGHT);
                    isMoving = true;
                    break;
                case LEFT:
                    dir.setTo(LEFT);
                    isMoving = true;
                    break;
                case UP:
                    dir.setTo(UP);

                    isMoving = true;
                    break;
                case DOWN:
                    dir.setTo(DOWN);
                    isMoving = true;
                    break;
                case SPACE:
                    int curX = (int) Math.round(getX()), curY = (int) Math.round(getY());
                    if (map[curY][curX] != 't') {
                        if (bombs.size() < bombLimit) {
                            // dat bom
                            Entity bomb = placeBomb();
                            entities.add(bomb);
                            flames.addAll(((Bomb) bomb).getFlames());
                            map[curY][curX] = ' ';
                        }
                    }
                    break;
                default:
                    break;
            }
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


    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        gc.drawImage(img, pos.x * Sprite.SCALED_SIZE, pos.y * Sprite.SCALED_SIZE);
        //bombSprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 20);
        //_img = bombSprite.getFxImage();
        //gc.drawImage(_img, posX_bomb * Sprite.SCALED_SIZE, posY_bomb * Sprite.SCALED_SIZE);
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public int getBombRange() {
        return bombRange;
    }

    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    public void setBombLimit(int bombLimit) {
        this.bombLimit = bombLimit;
    }
}
