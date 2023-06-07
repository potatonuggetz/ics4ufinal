import java.util.ArrayList;

public class GameEngine {

    public ArrayList<Tower> placedTowers;
    public ArrayList<Enemy> shownEnemies;
    public Level level;
    public GameRenderer renderer;

    int gold;
    int health;


    //boolean menuToggle;

    public GameEngine(Level level) {
        this.level = level;
    }

    public void tick() {
        for (Enemy e : shownEnemies) {
            e.posX += e.speed;
        }
    }
}
