package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.map;

public class Bomb extends Entity {

    private int bombLevel = 3;
    private List<Entity> flames = new ArrayList<>();
    private boolean done = false;
    private boolean exploded = false;
    private int explosionCountDown = 15;
    private int tickingCountDown = 90;

    public List<Entity> getFlames() {
        return flames;
    }

    // set up flame cho phu hop


    public void setTickingCountDown(int tickingCountDown) {
        this.tickingCountDown = tickingCountDown;
    }

    public int getTickingCountDown() {
        return tickingCountDown;
    }

    public void setFlames() {
        String[] dir = {"left", "down", "right", "top", "left_most", "down_most", "right_most", "top_most", "center"};
        int[] iX = {-1, 0, 1, 0};
        int[] iY = {0, 1, 0, -1};

        // let not bomber cross the bomb
        char[] mapz;
        mapz = map[(int) getY()];
        map[(int) getY()][(int) getX()] = 't';

        flames.add(new Flame(pos, null, "center"));
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= bombLevel; j++) {
                char flag = map[(int) getY() + j * iY[i]][(int) getX() + j * iX[i]];
                if (flag == '#' || flag == '*') {
                    if (flag == '*') {
                        // feature for chain explosion
                        map[(int) getY() + j * iY[i]][(int) getX() + j * iX[i]] = 't';
                    }
                    break;
                }
                if (j == bombLevel) {
                    flames.add(new Flame(pos.add(new Coordinate(iX[i], iY[i]).multiple(bombLevel)), null, dir[i + 4]));
                } else {
                    flames.add(new Flame(pos.add(new Coordinate(iX[i], iY[i]).multiple(j)), null, dir[i]));
                }
            }
        }
    }

    public void setBombLevel(int bombLevel) {
        this.bombLevel = bombLevel;
    }

    public int getBombLevel() {
        return bombLevel;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public Bomb(Coordinate pos, Image img) {
        super(pos, img);
        rtg = new Rectangle(pos.getX(), pos.getY(), 0.99, 0.99);
        setFlames();
    }

    public Bomb(Coordinate pos, Image img, int bombLevel) {
        super(pos, img);
        this.bombLevel = bombLevel;
        rtg = new Rectangle(pos.getX(), pos.getY(), 0.99, 0.99);
        setFlames();
    }

    @Override
    public void update() {
        if (!isExploded()) {
            tickingImg();
        } else {
            char[] mapz = map[(int) getY()];
            System.out.println(mapz);
            map[(int) getY()] [(int) getX()]=' ';
            System.out.println(map[(int) getY()]);
            explodingImg();

        }
    }

    @Override
    public void handleEvent(KeyEvent event) {

    }

    public void tickingImg() {
        if (tickingCountDown == 0) {
            setExploded(true);
        } else {
            this.img = Sprite
                    .bombTickingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, tickingCountDown)
                    .getFxImage();
            tickingCountDown--;
        }
    }

    public void explodingImg() {
        if (explosionCountDown == 0) {
            setDone(true);
            this.img = null;
        } else {
            this.img = Sprite
                    .bombExplodeSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,
                            Sprite.bomb_exploded2, explosionCountDown)
                    .getFxImage();
            explosionCountDown--;
        }
    }

    public void handleFlameCollision(List<Entity> entities, List<Entity> staticObjects,
                                     List<Entity> damagedEntities) {

        // damage bricks
        for (Entity o : staticObjects) {
            if (o instanceof Brick) {
                flames.forEach(flame -> {
                    int oX = (int) o.getX(), fX = (int) flame.getX(), x = (int) this.getX();
                    int oY = (int) o.getY(), fY = (int) flame.getY(), y = (int) this.getY();
                    String pos = ((Flame) flame).getPosition();

                    if (!pos.equals("left_most") && !pos.equals("down_most") &&
                            !pos.equals("right_most") && !pos.equals("top_most")) {
                        if (oX == fX && x == oX && oY - fY == 1 ||
                                oX == fX && x == oX && oY - fY == -1 ||
                                oX - fX == -1 && oY == fY && y == oY ||
                                oX - fX == 1 && oY == fY && y == oY) {
                            ((Brick) o).setDamaged(true);
                            damagedEntities.add(o);
                        }
                    } else {
                        if (oX == fX && oY == fY) {
                            ((Brick) o).setDamaged(true);
                            damagedEntities.add(o);
                        }
                    }
                });
            }
        }

        // damage entities
        for (Entity x : entities) {
            if (x instanceof Bomber || x instanceof Bomb) {
                flames.forEach(flame -> {
                    if (flame.rtg.intersects(x.rtg.getLayoutBounds())) {
                        if (x instanceof Bomber) {
                            ((Bomber) x).setKilled(true);
                            damagedEntities.add(x);
                        }
                        // chain explosion
                        if (x instanceof Bomb) {
                            // if (((Bomb) x).getTickingCountDown() > 10)
                            ((Bomb) x).setTickingCountDown(0);
                            ((Bomb) x).getFlames().forEach(o -> {
                                ((Flame) o).setExplosionCountDown(15);
                            });
                        }
                    }
                });
            }
        }
        // damage entities
        for (Entity x : entities) {
            if (x instanceof Bomber || x instanceof Bomb) {
                flames.forEach(flame -> {
                    if (flame.rtg.intersects(x.rtg.getLayoutBounds())) {
                        if (x instanceof Bomber) {
                            ((Bomber) x).setKilled(true);
                            damagedEntities.add(x);
                        }
                        // chain explosion
                        if (x instanceof Bomb) {
                            // if (((Bomb) x).getTickingCountDown() > 10)
                            ((Bomb) x).setTickingCountDown(0);
                            ((Bomb) x).getFlames().forEach(o -> {
                                ((Flame) o).setExplosionCountDown(15);
                            });
                        }
                    }
                });
            }
        }
    }

}


