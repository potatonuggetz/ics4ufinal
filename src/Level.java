import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

public class Level {
    protected Queue<Enemy> enemies;
    protected ArrayList<ArrayList<Line>> paths;
    protected Image background;

    public Level(String file) {
        enemies = new LinkedList<Enemy>();
        enemies.add(new Boccher());

        paths = new ArrayList<>();
        paths.add(new ArrayList<>());
        paths.get(0).add(new Line(new Pair<>(100, 500), new Pair<>(500, 500)));

        try {
            this.background = ImageIO.read(new File("shark.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
