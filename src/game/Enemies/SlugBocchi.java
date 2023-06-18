package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SlugBocchi extends Enemy {

    public SlugBocchi(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/snailboccher.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Slug Bocchi";
        health = 400;
        armor = 50;
        magicResist = 150;
        speed = 60;
        sizeX = 68;
        sizeY = 30;
    }
}
