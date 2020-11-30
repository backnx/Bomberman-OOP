package uet.oop.bomberman.entities.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

public abstract class Enemy extends Entity {
    public static final Coordinate UP = new Coordinate(0, -1);
    public static final Coordinate DOWN = new Coordinate(0, 1);
    public static final Coordinate LEFT = new Coordinate(-1, 0);
    public static final Coordinate RIGHT = new Coordinate(1, 0);

    private int input=1;
    private final Coordinate dir;
    protected double speed=0.025;
    protected int life=2;
    private boolean damaged=false;
    private boolean killed = false;
    protected final int MAX_DEAD_ANIMATE_LOOP = 5;
    
    private boolean throughWall=false;

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
        if (life!=0) {
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
        handleEvent();
    }

    public boolean checkMapMoveRight() {
        if (pos.x - Math.floor(pos.x) == 0) {
            if (!throughWall) {
                if (map[(int) pos.y][(int)pos.x+1] != '*' &&
                        map[(int) pos.y][(int)pos.x+1] != '#' &&
                        map[(int) pos.y][(int)pos.x+1] != 'w' &&
                        map[(int) pos.y][(int)pos.x+1] != 't') {
                    pos.x = (double) Math.round((pos.x + speed) * 1000) / 1000;
                    return true;
                }
                return false;
            } else {
                if (map[(int) pos.y][(int)pos.x+1] != '#') {
                    pos.x = (double) Math.round((pos.x + speed) * 1000) / 1000;
                    return true;
                }
                return false;
            }
        } else {
            pos.x = (double) Math.round((pos.x + speed) * 1000) / 1000;
            return true;
        }

    }

    public boolean checkMapMoveLeft() {
        if (pos.x - Math.floor(pos.x) == 0) {
            if (!throughWall) {
                if (map[(int) pos.y][(int) pos.x - 1] != '*' &&
                        map[(int) pos.y][(int) pos.x - 1] != '#' &&
                        map[(int) pos.y][(int) pos.x - 1] != 'w' &&
                        map[(int) pos.y][(int) pos.x - 1] != 't') {
                    pos.x = (double) Math.round((pos.x - speed) * 1000) / 1000;
                     
                    return true;
                }
                return false;
            } else {
                if (map[(int) pos.y][(int) pos.x - 1] != '#') {
                    pos.x = (double) Math.round((pos.x - speed) * 1000) / 1000;
                     
                    return true;
                }
                return false;
            }
        } else {
            pos.x = (double) Math.round((pos.x - speed) * 1000) / 1000;
             
            return true;
        }

    }

    public boolean checkMapMoveUp() {
        if (pos.y - Math.floor(pos.y) == 0) {
            if (!throughWall) {
                if (map[(int) pos.y - 1] [(int) pos.x] != '*' &&
                        map[(int) pos.y - 1] [(int) pos.x] != '#' &&
                        map[(int) pos.y - 1] [(int) pos.x] != 'w' &&
                        map[(int) pos.y - 1] [(int) pos.x] != 't') {
                    pos.y = (double) Math.round((pos.y - speed) * 1000) / 1000;
                     
                    return true;
                }
                return false;
            } else {
                if (map[(int) pos.y - 1] [(int) pos.x] != '#') {
                    pos.y = (double) Math.round((pos.y - speed) * 1000) / 1000;
                     
                    return true;
                }
                return false;
            }
        } else {
            pos.y = (double) Math.round((pos.y - speed) * 1000) / 1000;
             
            return true;
        }
    }


    public boolean checkMapMoveDown() {
        if (pos.y - Math.floor(pos.y) == 0) {
            if (!throughWall) {
                if (map[(int) pos.y + 1] [(int) pos.x] != '*' &&
                        map[(int) pos.y + 1] [(int) pos.x] != '#' &&
                        map[(int) pos.y + 1] [(int) pos.x] != 'w' &&
                        map[(int) pos.y + 1] [(int) pos.x] != 't') {
                    pos.y = (double) Math.round((pos.y + speed) * 1000) / 1000;
                     
                    return true;
                }
                return false;
            } else {
                if (map[(int) pos.y + 1] [(int) pos.x] != '#') {
                    pos.y = (double) Math.round((pos.y + speed) * 1000) / 1000;
                     
                    return true;
                }
                return false;
            }
        } else {
            pos.y = (double) Math.round((pos.y + speed) * 1000) / 1000;
             
            return true;
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

    public void handleEvent() {
        if (input == 1) {

            if (!checkMapMoveLeft()) input = (int) (Math.random() * 4 + 1);

        }
        if (input == 2) {
            if (!checkMapMoveRight()) input = (int) (Math.random() * 4 + 1);
        }

        if (input == 3) {
            if (!checkMapMoveUp()) input = (int) (Math.random() * 4 + 1);
        }

        if (input == 4) {
            if (!checkMapMoveDown()) input = (int) (Math.random() * 4 + 1);
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
