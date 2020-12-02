package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Coordinate;
import uet.oop.bomberman.entities.Enemy.*;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.BombsItem;
import uet.oop.bomberman.entities.Item.FlameItem;
import uet.oop.bomberman.entities.Item.Item;
import uet.oop.bomberman.entities.Item.SpeedItem;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BombermanGame extends Application {

    public static int WIDTH = 31;
    public static int HEIGHT = 13;

    public double speed = 1.0;
    public static int level = 1;

    public static int bomberScore = 0;
    public static int timeLeft = 180;
    public static int liveLeft = 3;
    public static boolean nextLevel = true;

    private boolean liveSub = false;
    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();// contains Grass and Walls
    public static List<Entity> destroyableObjects = new ArrayList<>(); // contains Items and Bricks
    public static List<Entity> damagedObjects = new ArrayList<>();
    public static List<Flame> flames = new ArrayList<>();
    public static Bomber bomberman;
    public static int enemyCnt;
    private Scene scene;
    public static char[][] map = new char[HEIGHT][WIDTH];

    private static Text score, _score, time, _time, live, _live;
    private int delay = 1000;
    private int period = 1000;

    Scanner scanner;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws InterruptedException {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        scene = new Scene(root, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 1), Color.BLACK);

        // Them scene vao stage
        stage.setTitle("Bomberman Gameee");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Timer timeCount = new Timer();
        timeCount.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                timeLeft--;
            }
        }, delay, period);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (nextLevel == true) {
                    try {
                        loadMap();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    createMap();
                    nextLevel = false;
                }
                update();
                try {
                    render();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (timeLeft == 0 || liveLeft == 0) {
                    String string = "YOU LOSE :<";
                    endGame(string);
                    stage.setScene(scene);
                }
            }
        };
        timer.start();

        score = new Text(30, 435, "Score: ");
        time = new Text(300, 435, "Time: ");
        live = new Text(570, 435, "Live: ");
        score.setFill(Color.WHITE);
        score.setFont(new Font(14));
        time.setFill(Color.WHITE);
        time.setFont(new Font(14));
        live.setFill(Color.WHITE);
        live.setFont(new Font(14));
        _score = new Text(90, 435, String.valueOf(bomberScore));
        _score.setFill(Color.WHITE);
        _score.setFont(new Font(14));
        _time = new Text(360, 435, String.valueOf(timeLeft));
        _time.setFill(Color.WHITE);
        _time.setFont(new Font(14));
        _live = new Text(630, 435, String.valueOf(liveLeft));
        _live.setFill(Color.WHITE);
        _live.setFont(new Font(14));
        root.getChildren().addAll(score, time, _score, _time, live, _live);

        bomberman = new Bomber(new Coordinate(1, 1), Sprite.player_right.getFxImage());
        entities.add(bomberman);

        scene.setOnKeyReleased(this::handleEvent);
        scene.setOnKeyPressed(this::handleEvent);
    }

    public void loadMap() throws FileNotFoundException {
        scanner = new Scanner(new File("res\\levels\\Level" + level + ".txt"));
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
        stillObjects.clear();
        destroyableObjects.clear();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object;
                Coordinate pos_ = new Coordinate(j, i);
                switch (map[i][j]) {
                    case '#': {
                        object = new Wall(pos_, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case '*': {
                        stillObjects.add(new Grass(pos_, Sprite.grass.getFxImage()));
                        object = new Brick(pos_, Sprite.brick.getFxImage());
                        destroyableObjects.add(object);
                        break;
                    }
                    case 'b': {
                        destroyableObjects.add(new Brick(pos_, Sprite.brick.getFxImage()
                                , new BombsItem(pos_, Sprite.powerup_bombs.getFxImage())));
                        map[i][j] = '*';
                        break;
                    }
                    case 's':
                        destroyableObjects.add(new Brick(pos_, Sprite.brick.getFxImage()
                                , new SpeedItem(pos_, Sprite.powerup_speed.getFxImage())));
                        map[i][j] = '*';
                        break;
                    case 'f': {
                        destroyableObjects.add(new Brick(pos_, Sprite.brick.getFxImage()
                                , new FlameItem(pos_, Sprite.powerup_flames.getFxImage())));
                        map[i][j] = '*';
                        break;
                    }
                    case '1': {
                        stillObjects.add(new Grass(pos_, Sprite.grass.getFxImage()));
                        Enemy enemy = new Balloon(pos_, Sprite.balloom_left1.getFxImage());
                        entities.add(enemy);
                        ++enemyCnt;
                        map[i][j] = ' ';
                        break;
                    }
                    case '2': {
                        stillObjects.add(new Grass(pos_, Sprite.grass.getFxImage()));
                        Entity enemy = new Oneal(pos_, Sprite.oneal_left1.getFxImage());
                        entities.add(enemy);
                        ++enemyCnt;
                        map[i][j] = ' ';
                        break;
                    }
                    case '3': {
                        stillObjects.add(new Grass(pos_, Sprite.grass.getFxImage()));
                        Entity enemy = new Doll(pos_, Sprite.doll_right1.getFxImage());
                        entities.add(enemy);
                        ++enemyCnt;
                        map[i][j] = ' ';
                        break;
                    }
                    case '4': {
                        stillObjects.add(new Grass(pos_, Sprite.grass.getFxImage()));
                        Entity enemy = new Kondoria(pos_, Sprite.kondoria_right1.getFxImage());
                        entities.add(enemy);
                        ++enemyCnt;
                        map[i][j] = ' ';
                        break;
                    }

                    default: {
                        object = new Grass(pos_, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                }
            }
            scanner.close();
        }
    }

    public void update() {
        updateDamagedObjects(); // enemies, bricks and flames
        entities.forEach(Entity::update);
        flames.forEach(Entity::update);
        updateItem();
        if (bomberman.isKilled() && !liveSub) {
            liveLeft--;
            liveSub = true;
        }
        if (timeLeft < 1) {
            timeLeft = 0;
        }
        _time.setText(String.valueOf(timeLeft));
        _score.setText(String.valueOf(bomberScore));
        _live.setText(String.valueOf(liveLeft));
    }

    public void render() throws InterruptedException {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> {
            g.render(gc);
        });
        destroyableObjects.forEach(g -> {
            g.render(gc);
        });
        entities.forEach(g -> {
            g.render(gc);
        });
        damagedObjects.forEach(g -> {
            g.render(gc);
        });
        flames.forEach(g -> {
            g.render(gc);
        });
        if (bomberman.deadRenderDone) {
            bomberman.setKilled(false);
            liveSub = false;
            bomberman.dead_animate_loop = bomberman.MAX_DEAD_ANIMATE_LOOP;
            bomberman.deadRenderDone = false;
        }

    }

    public void handleEvent(Event event) {
        bomberman.handleEvent((KeyEvent) event);
    }

    public void updateDamagedObjects() {
        try {

            // update bricks and enemies
            entities.forEach(o -> {
                checkForDamagedEntities(o);
                damagedObjects.forEach(this::updateDestroyableObjectsAndEnemies);

            });

            // get the explosion done (remove the flames)
            flames.forEach(o -> {
                if (o != null) {
                    if (o.isDone()) {
                        flames.remove(o);
                    }
                }
            });
        } catch (ConcurrentModificationException e) {
        }
        entities.forEach(o->{
            if (o instanceof Enemy){
                if (bomberman.checkCollision(bomberman,o)) bomberman.setKilled(true);
            }
        });

    }

    public void checkForDamagedEntities(Entity o) {
        // remove bomb from the entites
        if (o instanceof Bomb) {
            if (((Bomb) o).isExploded()) {

                // enable bomber go through the place used to be for the bomb
                map[(int) o.getY()][(int) o.getX()] = ' ';

                // check if the bomb damange any objects
                ((Bomb) o).handleFlameCollision();
                entities.remove(o);
            }
        }
    }

    public void updateDestroyableObjectsAndEnemies(Entity br) {
        if (br instanceof Brick) {
            if (((Brick) br).isDone()) {

                Brick brick = (Brick) br;

                // replace the tile with the grass
                damagedObjects.remove(br);
                destroyableObjects.remove(br);
                Entity entity = new Grass(br.getPos(), Sprite.grass.getFxImage());
                if (brick.getItem() != null) {

                    if (brick.getItem() instanceof BombsItem) {
                        entity = new BombsItem(br.getPos(), Sprite.powerup_bombs.getFxImage());
                    }
                    if (brick.getItem() instanceof SpeedItem) {
                        entity = new SpeedItem(br.getPos(), Sprite.powerup_speed.getFxImage());
                    }
                    if (brick.getItem() instanceof FlameItem) {
                        entity = new FlameItem(br.getPos(), Sprite.powerup_flames.getFxImage());
                    }
                    destroyableObjects.add(entity);
                } else {
                    stillObjects.add(entity);
                }

                // enable bomberman to go through the tile
                map[(int) br.getY()][(int) br.getX()] = ' ';

            } else {
                br.update();
            }
        } else if (br instanceof Enemy) {
            if (br.getImg() == null) {
                entities.remove(br);
            }
        }
    }

    public void updateItem() {
        int size = destroyableObjects.size();
        for (int i = 0; i < size; i++) {
            Entity o = destroyableObjects.get(i);
            if (o instanceof Item) {
                if (((Item) o).collision(bomberman)) {
                    if (o instanceof SpeedItem) {
                        bomberman.setSpeed(bomberman.getSpeed() + 0.02);
                    } else if (o instanceof FlameItem) {
                        bomberman.setBombRange(bomberman.getBombRange() + 1);
                    } else if (o instanceof BombsItem) {
                        bomberman.setBombLimit(bomberman.getBombLimit() + 1);
                    }
                    destroyableObjects.remove(i);
                    Entity grass = new Grass(o.getPos(), Sprite.grass.getFxImage());
                    stillObjects.add(grass);
                    i--;
                    size--;
                }
            }
        }
    }

    private void endGame(String string) {
        Group root = new Group();
        Text noti = new Text(220, 240, string);

        noti.setFill(Color.GHOSTWHITE);
        noti.setFont(Font.font("Arial", FontWeight.BOLD, 90));
        root.getChildren().add(noti);
        scene = new Scene(root, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 1), Color.BLACK);
    }
}
