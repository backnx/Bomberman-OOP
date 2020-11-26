package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public double speed = 1.0;
    public int level;

    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    private Bomber bomberman;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }
    public static void delay(long delayMs, Runnable toRun){
        Thread t = new Thread(() ->{
            try { Thread.sleep(delayMs); }catch(InterruptedException ignored){}
            Platform.runLater(toRun);
        });
        t.setDaemon(true);
        t.start();
    }
    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * Map.WIDTH, Sprite.SCALED_SIZE * Map.HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                update();
                render();
            }
        };
        timer.start();

        bomberman = new Bomber(new Coordinate(1,1), Sprite.player_right.getFxImage());
        entities.addAll(Map.getMapObjects());

        //entities.add(bomberman);
        scene.setOnKeyReleased(this::handleEvent);
        scene.setOnKeyPressed(this::handleEvent);

    }





    public void update() {
        entities.forEach(Entity::update);
        bomberman.update();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        entities.forEach(g -> g.render(gc));
        bomberman.render(gc);
    }

    public void handleEvent(Event event){
        bomberman.handleEvent((KeyEvent) event);
    }
}