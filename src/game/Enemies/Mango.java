package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Mango extends Enemy {

    public Mango(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/mango.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Mango Box";
        health = 300;
        armor = 100;
        magicResist = 20;
        speed = 90;
        sizeX = 45;
        sizeY = 45;
    }
}
