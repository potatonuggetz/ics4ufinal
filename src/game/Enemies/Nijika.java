package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Nijika extends Enemy {

    public Nijika(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/nijika.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Nijika";
        health = 85;
        armor = 0;
        magicResist = 20;
        speed = 300;
        sizeX = 46;
        sizeY = 34;
    }

}
