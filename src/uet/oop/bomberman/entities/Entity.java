package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Entity {
    protected Coordinate pos=new Coordinate();

    protected Coordinate dir=new Coordinate();
    protected Image img;
    protected Image _img;
    protected Sprite sprite;
    protected Sprite bombSprite;
    public Rectangle rtg=new Rectangle();
    protected int animate;


    protected final int MAX_ANIMATE = 7500;
    protected void animate() {

            if (animate < MAX_ANIMATE) {
                animate++;
            } else {
                animate = 0;
            }
    }
    public Entity( Coordinate pos, Image img) {
        this.pos.setTo(pos);
        this.img = img;
    }

    public double getX() {
        return pos.x;
    }

    public void setX(double x) {
        this.pos.x = x;
    }

    public double getY() {
        return pos.y;
    }

    public void setY(double y) {
        this.pos.y = y;
    }

    public void setPos(Coordinate pos) {
        this.pos = pos;
    }
    public Coordinate getPos() {
        return pos;
    }

    protected void setDir(Coordinate dir) {
    }
    public Coordinate getDir(){
        return null;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void render(GraphicsContext gc) {
//        SnapshotParameters params = new SnapshotParameters();
//        params.setFill(Color.TRANSPARENT);
//
//        ImageView iv = new ImageView(img);
//        Image base = iv.snapshot(params, null);

        gc.drawImage(img, pos.x * Sprite.SCALED_SIZE, pos.y * Sprite.SCALED_SIZE);
    }

    public HashSet<String> getMask(Entity entity) {
        HashSet<String> mask = new HashSet<String>();
        Image image = entity.getImg();
        int a, pixel;
        PixelReader pixelReader = image.getPixelReader();
        for (int i = 0; i < (int) image.getWidth(); i++) {
            for (int j = 0; j < (int) image.getHeight(); j++) {
                pixel = pixelReader.getArgb(i,j);
                a = (pixel >> 24) & 0xff;
                if (a != 0) {
                    mask.add((int) (entity.pos.x * 32) + j + "," + ((int) (entity.pos.y * 32) - i));
                }
            }
        }
        return mask;
    }

    public boolean checkCollision (Entity a, Entity b) {
        double ax1 = a.getPos().x;
        double ay1 = a.getPos().y;
        double ax2 = ax1 + a.getImg().getWidth();
        double ay2 = ay1 + a.getImg().getHeight();
        double bx1 = b.getPos().x;
        double by1 = b.getPos().y;
        double bx2 = bx1 + b.getImg().getWidth();
        double by2 = by1 + b.getImg().getHeight();

        if (by2 < ay1 || ay2 < by1 || bx2 < ax1 || ax2 < bx1) {
            return false;
        }
        else {
            HashSet<String> maskPlayer1 = getMask(a);
            HashSet<String> maskPlayer2 = getMask(b);
            maskPlayer1.retainAll(maskPlayer2);
            return maskPlayer1.size() > 0;
        }
    }

    public abstract void update();
    public abstract void handleEvent(KeyEvent event);
}
