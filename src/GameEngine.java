import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameEngine {

    private  ArrayList<Tower> placedTowers;
    public ArrayList<Enemy> shownEnemies;
    public Level level;

    private final double FPS = 60.0;
    private int currentFrame=0;

    public ArrayList<Tower> availableTowers;

    int gold;
    int health;

    Tower selectedTower;

    //boolean menuToggle;

    public GameEngine(Level level) {
        this.level = level;
    }

    public void update() {
        currentFrame++;
        // move enemy
        for (Enemy e : this.shownEnemies) {
            e.posX += (e.speed / FPS) * this.level.paths.get(e.path).get(e.leg).getDirection().first;
            e.posY += (e.speed / FPS) * this.level.paths.get(e.path).get(e.leg).getDirection().second;

        }

        // towers attack


    }

    public void draw(JPanel panel, Graphics g) {
        g.drawImage(this.level.background, 0, 0, null);


    }

    // menu at bottom, gold, health, background
    public void drawGameLayout(Graphics g) {

    }

    // towers, enemies, "bullets"
    public void drawGameLogic(Graphics g) {
        for (Enemy e : this.shownEnemies) {
            g.drawImage(e.image, e.posX-10, e.posY-10, null);
        }
        for (Tower t : this.placedTowers) {
            g.drawImage(t.image, t.posX, t.posY, null);
        }
    }

    // tower highlight, tower info, ability buttons
    public void drawSelectedTower(Graphics g) {

    }

    //getters setters
    public int getCurrentFrame(){
        return currentFrame;
    }

    public double getFPS(){
        return FPS;
    }

    public ArrayList<Tower> getPlacedTowers(){
        return placedTowers;
    }
}
