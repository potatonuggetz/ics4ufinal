import javax.imageio.*;
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
        health = 100;
        armor = 0;
        magicResist = 0;
        speed = 400;
        sizeX = 44;
        sizeY = 26;
    }

}
