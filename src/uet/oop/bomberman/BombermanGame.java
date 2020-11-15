package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    public double speed = 1.0;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private Bomber bomberman;

    public char[][] map = new char[HEIGHT][WIDTH];

    Scanner scanner;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
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
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();
        try {
            loadMap();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        createMap();

        bomberman = new Bomber(new Coordinate(1,1), Sprite.player_right.getFxImage());
        entities.add(bomberman);

        scene.setOnKeyReleased(this::handleEvent);
        scene.setOnKeyPressed(this::handleEvent);

    }

    public void loadMap() throws FileNotFoundException {
        scanner = new Scanner(new File("E:\\bomberman\\Bomberman-OOP\\res\\levels\\Level1.txt"));
        int res;
        for (int i = 0; i < 3; i++) {
            res = scanner.nextInt();
        }
        String str;
        str = scanner.nextLine();
        for (int i = 0; i < HEIGHT; i++) {
            str = scanner.nextLine();
            for (int j = 0; j < WIDTH; j++) {
                map[i][j] = str.charAt(j);
            }
        }
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println("");
        }
    }
    public void createMap() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object = new Grass(new Coordinate(j,i), Sprite.grass.getFxImage());
                if (map[i][j] == '#') {
                    object = new Wall(new Coordinate(j,i), Sprite.wall.getFxImage());
                }
                if (map[i][j] == '*') {
                    object = new Brick(new Coordinate(j,i), Sprite.brick.getFxImage());
                }
                if (map[i][j] == 'x') {
                    object = new Brick(new Coordinate(j,i), Sprite.brick.getFxImage());
                }
                if (map[i][j] == 's') {
                    object = new Brick(new Coordinate(j,i), Sprite.brick.getFxImage());
                }
                if (map[i][j] == 'f') {
                    object = new Brick(new Coordinate(j,i), Sprite.brick.getFxImage());
                }
                if (map[i][j] == 'b') {
                    object = new Brick(new Coordinate(j,i), Sprite.brick.getFxImage());
                }
                stillObjects.add(object);
            }
        }
        scanner.close();
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
    public void handleEvent(Event event){
        bomberman.handleEvent((KeyEvent) event);
    }
}
