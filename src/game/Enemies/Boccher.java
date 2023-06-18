package game.Enemies;

import game.Enemy;

import javax.imageio.*;
import java.io.*;

public class Boccher extends Enemy {

    public Boccher(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/boccher.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Boccher";
        health = 80;
        armor = 0;
        magicResist = 0;
        speed = 200;
        sizeX = 44;
        sizeY = 26;
    }

}
