package uet.oop.bomberman.entities.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Entity {
    public static final Coordinate UP = new Coordinate(0, -1);
    public static final Coordinate DOWN = new Coordinate(0, 1);
    public static final Coordinate LEFT = new Coordinate(-1, 0);
    public static final Coordinate RIGHT = new Coordinate(1, 0);

    private final Coordinate dir;
    protected double speed=0.025;
    protected int life=2;
    private boolean damaged=false;
    private boolean killed = false;
    protected final int MAX_DEAD_ANIMATE_LOOP = 5;

    private int dead_animate_loop = MAX_DEAD_ANIMATE_LOOP;

    public Enemy(Coordinate pos, Image img, Coordinate dir) {
        super(pos, img);
        this.dir = dir;
        damaged=false;
    }

    /**
     * Hoat hinh
     */
    protected void animate() {
        if (!isKilled()) {
            if (animate < MAX_ANIMATE) {
                animate++;
            } else {
                animate = 0;
            }
        } else {
            if (dead_animate_loop == MAX_DEAD_ANIMATE_LOOP) {
                animate = 0;
                dead_animate_loop--;
            }
            if (dead_animate_loop == 0) {
                return;
            }
            if (animate < 15) {
                animate++;
            } else {
                animate = 0;
                dead_animate_loop--;
            }
        }
    }

    public Enemy(Coordinate pos, Image img) {
        super(pos, img);
        dir = new Coordinate();
        speed = 0.05;
    }

    @Override
    public void update() {
        animate();
        if (isDamaged() && life == 2) {
            setDamaged(false);
            life = 1;
        } else if (isDamaged() && life == 1) {
            setDamaged(false);
            life = 0;
        }
        if (!isKilled()) {
            move();
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
                    if (pos.y - (int) pos.y <= 0.3) {
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
        switch ((int) (Math.random() * 4 + 1)) {
            case 1:
                dir.setTo(RIGHT);
                break;
            case 2:
                dir.setTo(LEFT);
                break;
            case 3:
                dir.setTo(UP);
                break;
            case 4:
                dir.setTo(DOWN);
                break;
            default:
                break;
        }
    }


    private void chooseSprite() {
        if (!isKilled()) {
            if (dir.equalTo(UP)||dir.equalTo(LEFT)) {
                sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2,Sprite.balloom_left3, animate, 20);
            } else {
                sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2,Sprite.balloom_right3, animate, 20);
            }
        } else {
            sprite = Sprite.bombExplodeSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate);
        }

        img = sprite.getFxImage();
    }


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

    public boolean isKilled() {
        return killed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }
}
