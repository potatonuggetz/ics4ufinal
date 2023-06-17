import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameEngine {

    public ArrayList<Tower> placedTowers;
    public ArrayList<Enemy> shownEnemies;
    public Level level;

    int gold;
    int health;

    Tower selectedTower;

    //boolean menuToggle;

    public GameEngine(Level level) {
        this.level = level;
    }

    public void tick() {
        for (Enemy e : shownEnemies) {
            e.posX += e.speed;
        }
    }

    public void draw(JPanel panel, Graphics g) {
        g.drawImage(this.level.background, 0, 0, null);

        for (Enemy e : this.shownEnemies) {
            g.drawImage(e.image, e.posX-10, e.posY-10, null);
        }
        for (Tower t : this.placedTowers) {
            g.drawImage(t.image, t.posX, t.posY, null);
        }
    }

    // menu at bottom, gold, health, background
    public void drawGameLayout(Graphics g) {

    }

    // towers, enemies, "bullets"
    public void drawGameLogic(Graphics g) {

    }

    // tower highlight, tower info, ability buttons
    public void drawSelectedTower(Graphics g) {

    }
}
