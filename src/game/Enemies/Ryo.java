package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Ryo extends Enemy {

    public Ryo(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/ryo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Ryo";
        health = 90;
        armor = 20;
        magicResist = 0;
        speed = 100;
        sizeX = 38;
        sizeY = 26;
    }

}
