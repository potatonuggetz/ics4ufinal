package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class HitoriGotoh extends Enemy {

    public HitoriGotoh(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/gotoh.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Hitori Gotoh";
        health = 160;
        armor = 30;
        magicResist = 30;
        speed = 170;
        sizeX = 28;
        sizeY = 48;
    }
}
