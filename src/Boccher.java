import javax.imageio.*;
import java.io.*;

public class Boccher extends Enemy {

    public Boccher() {
        super();
        try {
            this.image = ImageIO.read(new File("poop.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        posX = 100;
        posY = 500;
        absPosX = 100;
        absPosY = 500;
        path = 0;
        leg = 0;
        speed = 100;
    }

}
