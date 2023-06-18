import javax.imageio.ImageIO;
package game;
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

        // i make the file format so no error checking needed 8)
        enemies = new LinkedList<>();
        paths = new ArrayList<>();
        String line;
        try {
            if (!br.readLine().equals(":Paths")) {
                System.out.println("invalid format");
                return;
            }
            Pair<Integer, Integer> lastPoint = null;
            ArrayList<Line> p = new ArrayList<>();
            while (!(line = br.readLine()).equals(":Enemies")) {
                if (line.startsWith("-")) {
                    if (p.size() != 0) {
                        paths.add(p);
                    }
                    lastPoint = null;
                    p = new ArrayList<>();
                    continue;
                }
                if (lastPoint == null) {
                    lastPoint = new Pair<>(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1]));
                } else {
                    Pair<Integer, Integer> newPoint = new Pair<>(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1]));
                    p.add(new Line(lastPoint, newPoint));
                    lastPoint = newPoint;
                }
            }
            Queue<Enemy> q = new LinkedList<>();;
            while (!(line = br.readLine()).equals(":Background")) {
                if (line.startsWith("-")) {
                    if (!q.isEmpty()) enemies.add(q);
                    q = new LinkedList<>();
                    continue;
                }
                StringTokenizer st = new StringTokenizer(line, ",");
                int spawnTime = Integer.parseInt(st.nextToken());
                String enemyName = st.nextToken();
                int path = Integer.parseInt(st.nextToken());
                q.add(matchEnemy(enemyName, spawnTime, path));
            }
            try {
                this.background = ImageIO.read(new File(br.readLine()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            br.close();
        } catch (IOException e) {

        }
    }

    private Enemy matchEnemy(String s, int spawnTime, int path) {
        if (s.equals("Boccher")) return new Boccher(spawnTime, path, paths.get(path).get(0).getStart().first, paths.get(path).get(0).getStart().second);
        return null;
    }
}