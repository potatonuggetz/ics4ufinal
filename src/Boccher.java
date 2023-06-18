import javax.imageio.*;
import java.io.*;

public class Boccher extends Enemy {

    public Boccher(int path) {
        super(0, 0);
        try {
            this.image = ImageIO.read(new File("poop.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
