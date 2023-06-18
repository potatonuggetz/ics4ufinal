package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Bocchisaur extends Enemy {

    public Bocchisaur(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/boccher.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Bocchisaur";
        health = 200;
        armor = 40;
        magicResist = 0;
        speed = 150;
        sizeX = 43;
        sizeY = 51;
    }

}
