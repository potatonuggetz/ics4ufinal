import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class GameEngine {

    private ArrayList<Tower> placedTowers;
    public ArrayList<Enemy> shownEnemies;
    public ArrayList<Enemy> dyingEnemies;
    public Level level;

    public static int frame;

    private final double FPS = 60.0;
    private int currentFrame=0;

    public ArrayList<Tower> availableTowers;

    int gold;
    int health;

    Tower selectedTower;

    //boolean menuToggle;

    public GameEngine(Level level) {
        this.level = level;

        placedTowers = new ArrayList<>();
        shownEnemies = new ArrayList<>();
        dyingEnemies = new ArrayList<>();
        availableTowers = new ArrayList<>();
    }

    public void update() {
        currentFrame++;
        // move enemy
        ListIterator<Enemy> i = shownEnemies.listIterator();
        while(i.hasNext()) {
            Enemy e = i.next();
            Line leg = this.level.paths.get(e.path).get(e.leg);

            e.absPosX += (e.speed / FPS) * leg.getDirection().first;
            e.absPosY += (e.speed / FPS) * leg.getDirection().second;

            if ((e.absPosX * leg.getSigns().first > leg.getEnd().first * leg.getSigns().first) && (e.absPosY * leg.getSigns().second > leg.getEnd().second * leg.getSigns().second)) {
                e.leg++;

                if (e.leg >= this.level.paths.get(e.path).size()) {
                    health--;
                    e.posX = leg.getEnd().first;
                    e.posY = leg.getEnd().second;
                    e.deathAnimationCount = 0;
                    dyingEnemies.add(e);
                    i.remove();
                    continue;
                }

                Double diff = Math.max((e.absPosX - leg.getEnd().first) / leg.getDirection().first, (e.absPosY - leg.getEnd().second) / leg.getDirection().second);
                leg = this.level.paths.get(e.path).get(e.leg);
                e.absPosX += diff * leg.getDirection().first;
                e.absPosY += diff * leg.getDirection().second;
            }

            e.posX = (int)Math.round(e.absPosX);
            e.posY = (int)Math.round(e.absPosY);
        }

        // towers attack

    }

    public void draw(JPanel panel, Graphics g) {
        drawGameLayout(g);
        drawGameLogic(g);
        if (selectedTower != null) {
            drawSelectedTower(g);
        }
        drawEnemyDeath(g);
    }

    // menu at bottom, gold, health, background
    public void drawGameLayout(Graphics g) {
        g.drawImage(this.level.background, 0, 0, null);
    }

    // towers, enemies, "bullets"
    public void drawGameLogic(Graphics g) {
        for (Enemy e : this.shownEnemies) {
            g.drawImage(e.image, e.posX-e.sizeX, e.posY-e.sizeY, null);
        }
        for (Tower t : this.placedTowers) {
            g.drawImage(t.image, t.posX, t.posY, null);
        }
    }

    // tower highlight, tower info, ability buttons
    public void drawSelectedTower(Graphics g) {

    }

    // cycle through death animation
    public void drawEnemyDeath(Graphics g) {
        ListIterator<Enemy> i = dyingEnemies.listIterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            g.drawImage(e.deathAnimations[e.deathAnimationCount], e.posX-e.sizeX, e.posY-e.sizeY, null);
            e.deathAnimationCount++;
            if (e.deathAnimationCount >= e.deathAnimations.length) {
                i.remove();
            }
        }
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
