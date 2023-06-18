package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TracksuitGotoh extends Enemy {

    public TracksuitGotoh(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/gotohtracksuit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Tracksuit Gotoh";
        health = 130;
        armor = 15;
        magicResist = 15;
        speed = 200;
        sizeX = 28;
        sizeY = 45;
    }
}
