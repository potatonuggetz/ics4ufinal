package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SadBoccher extends Enemy {

    public SadBoccher(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/cryingboccher.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Sad Bocchi";
        health = 50;
        armor = 0;
        magicResist = 30;
        speed = 150;
        sizeX = 44;
        sizeY = 26;
    }
}
