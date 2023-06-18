package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TrashBocchi extends Enemy {

    public TrashBocchi(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/trashbocchi.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Trash Bocchi";
        health = 250;
        armor = 200;
        magicResist = 0;
        speed = 100;
        sizeX = 38;
        sizeY = 50;
    }
}
