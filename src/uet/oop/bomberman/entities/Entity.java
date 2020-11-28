package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
        BufferedReader image = null;
        try {
            image = ImageIO.read(new File(entity.getImg()));
        } catch (IOException e) {
            System.out.println("Can't load file");
        }
        int a, pixel;

    }
    public abstract void update();
    public abstract void handleEvent(KeyEvent event);
}
