package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class NijikaIjichi extends Enemy {

    public NijikaIjichi(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/ijichi.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Nijika Ijichi";
        health = 180;
        armor = 20;
        magicResist = 70;
        speed = 300;
        sizeX = 36;
        sizeY = 51;
    }
}
