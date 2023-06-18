package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class RyoYamada extends Enemy {

    public RyoYamada(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/yamada.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Ryo Yamada";
        health = 220;
        armor = 70;
        magicResist = 0;
        speed = 220;
        sizeX = 34;
        sizeY = 51;
    }
}
