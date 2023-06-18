import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Level {
    protected Queue<Queue<Enemy>> enemies;
    protected ArrayList<ArrayList<Line>> paths;
    protected Image background;

    public Level(String file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("1.txt"));
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
            return;
        }

        //
        enemies = new LinkedList<Queue<Enemy>>();
        String line;
        try {
            if (!br.readLine().equals(":Enemies")) {
                System.out.println("invalid format");
                return;
            }
            while (!(line = br.readLine()).equals(":Paths")) {
                Queue<Enemy> q = null;
                if (line.startsWith("-")) {
                    if (q != null) enemies.add(q);
                    q = new LinkedList<>();
                    continue;
                }
                StringTokenizer st = new StringTokenizer(line, ",");
                int spawnTime = Integer.parseInt(st.nextToken());
                String enemyName = st.nextToken();
                int path = Integer.parseInt(st.nextToken());
            }
            br.close();
        } catch (IOException e) {

        }





        paths = new ArrayList<>();


        //enemies.add(new Boccher());


        paths.add(new ArrayList<>());
        paths.get(0).add(new Line(new Pair<>(100, 500), new Pair<>(500, 500)));

        try {
            this.background = ImageIO.read(new File("shark.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Enemy matchEnemy(String s) {
        return null;
    }
}
