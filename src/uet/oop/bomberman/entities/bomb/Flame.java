package uet.oop.bomberman.entities.bomb;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {

    private int explosionCountDown = 120;
    private String position;
    private boolean done = false;

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public int getExplosionCountDown() {
        return explosionCountDown;
    }

    public void setExplosionCountDown(int explosionCountDown) {
        this.explosionCountDown = explosionCountDown;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public Flame(Coordinate pos, Image img) {
        super(pos, img);
        rtg = new Rectangle(pos.getX(),pos.getY(), 0.99, 0.99);
    }

    public Flame(Coordinate pos, Image img, String position) {
        super(pos, img);
        rtg = new Rectangle(pos.getX(),pos.getY(), 0.99, 0.99);
        this.position = position;
    }



    public void explodingImg() {
        if (explosionCountDown == 0 || explosionCountDown >= 30) {
            this.img = null;
            if (explosionCountDown >= 30) {
                explosionCountDown--;
            } else {
                setDone(true);
            }
        } else {
            // System.out.println(pos);
            switch(position) {
                case "left":
                case "right": {
                    // System.out.println(pos);
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1,
                                    Sprite.explosion_horizontal2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "down":
                case "top": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1,
                                    Sprite.explosion_vertical2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "left_most": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_horizontal_left_last,
                                    Sprite.explosion_horizontal_left_last1,
                                    Sprite.explosion_horizontal_left_last2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "down_most": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_vertical_down_last,
                                    Sprite.explosion_vertical_down_last1,
                                    Sprite.explosion_vertical_down_last2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "right_most": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_horizontal_right_last,
                                    Sprite.explosion_horizontal_right_last1,
                                    Sprite.explosion_horizontal_right_last2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "top_most": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_vertical_top_last,
                                    Sprite.explosion_vertical_top_last1,
                                    Sprite.explosion_vertical_top_last2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "center": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.bomb_exploded,
                                    Sprite.bomb_exploded1,
                                    Sprite.bomb_exploded2, explosionCountDown)
                            .getFxImage();
                    break;
                }
            }
            explosionCountDown--;
        }
    }
    @Override
    public void update() {
        explodingImg();
    }

    @Override
    public void handleEvent(KeyEvent event) {

    }
}
