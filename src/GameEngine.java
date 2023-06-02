import java.util.ArrayList;

public class GameEngine {

    public ArrayList<Tower> placedTowers;
    public ArrayList<Enemy> shownEnemies;
    public Level level;
    public GameRenderer renderer;

    public GameEngine(Level level) {
        this.level = level;
    }

    public void tick() {
        for (Enemy e : shownEnemies) {
            e.posX += e.speed;
        }

        renderer.tick();
    }
}
