package game.Enemies;

import game.Enemy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class IkuyoKita extends Enemy {

    public IkuyoKita(int spawnTime, int path, int startX, int startY) {
        super(spawnTime, path, startX, startY);
        try {
            this.image = ImageIO.read(new File("img/enemy/ikuyo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Ikuyo Kita";
        health = 130;
        armor = 10;
        magicResist = 10;
        speed = 600;
        sizeX = 33;
        sizeY = 49;
    }
}
