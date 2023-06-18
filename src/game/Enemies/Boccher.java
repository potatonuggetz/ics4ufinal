package game.Enemies;

import javax.imageio.*;

import game.Enemy;

import java.io.*;

public class Boccher extends Enemy {

    public Boccher(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("boccher.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Boccher";
        health = 1000;
        armor = 10;
        magicResist = 10;
        speed = 400;
        sizeX = 44;
        sizeY = 26;
    }

}
