package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Kita extends Enemy {
    public Kita(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/kita.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Kita";
        health = 65;
        armor = 5;
        magicResist = 5;
        speed = 500;
        sizeX = 42;
        sizeY = 26;
    }
}
