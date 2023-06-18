package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DyingBocchi extends Enemy {

    public DyingBocchi(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/dyingbocchi.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Dying Bocchi";
        health = 50;
        armor = 0;
        magicResist = 30;
        speed = 150;
        sizeX = 45;
        sizeY = 45;
    }
}
