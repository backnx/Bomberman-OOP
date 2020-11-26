package uet.oop.bomberman;

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

public class Map{


    public static int WIDTH = 31;
    public static int HEIGHT = 13;

    public static char[][] map = new char[HEIGHT][WIDTH];
    public static List<Entity> getMapObjects(){
        try {
            loadMap();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return mapDecoder();
    }
    private static void loadMap() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("res\\levels\\Level1.txt"));
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
        scanner.close();
    }

    private static List<Entity> mapDecoder() {
        List<Entity> mapInitialObjects=new ArrayList<>();
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
                mapInitialObjects.add(object);
            }
        }
        return  mapInitialObjects;
    }
}