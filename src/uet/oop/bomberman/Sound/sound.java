package uet.oop.bomberman.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class sound {
    static Media media1 = new Media(new File("res/sound/enemyDie.wav").toURI().toString());
    static Media media2 = new Media(new File("res/sound/bombExplo.wav").toURI().toString());
    static Media media3 = new Media(new File("res/sound/bomSet.wav").toURI().toString());
    static Media media4 = new Media(new File("res/sound/soundtrack.wav").toURI().toString());
    static Media media5 = new Media(new File("res/sound/item.wav").toURI().toString());
    static Media media6 = new Media(new File("res/sound/nextLevel.wav").toURI().toString());
    static Media media7 = new Media(new File("res/sound/endGame.wav").toURI().toString());
    static Media media8 = new Media(new File("res/sound/moving.wav").toURI().toString());

    public static MediaPlayer enemyDie = new MediaPlayer(media1);
    public static MediaPlayer bomExpl = new MediaPlayer(media2);
    public static MediaPlayer setBomb = new MediaPlayer(media3);
    public static MediaPlayer soundtrack = new MediaPlayer(media4);
    public static MediaPlayer eatItem = new MediaPlayer(media5);
    public static MediaPlayer levelUp = new MediaPlayer(media6);
    public static MediaPlayer BomberDie = new MediaPlayer(media7);
    public static MediaPlayer moving = new MediaPlayer(media8);

}
