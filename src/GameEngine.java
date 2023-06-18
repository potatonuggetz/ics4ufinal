import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GameEngine {

    private ArrayList<Tower> placedTowers;
    private ArrayList<Enemy> shownEnemies;
    private ArrayList<Enemy> dyingEnemies;
    private ArrayList<Projectile> activeProjectiles;
    public ArrayList<Tower> availableTowers;
    Tower selectedTower;
    private Level level;
    private Queue<Enemy> nextWave;

    private static final double FPS = 60.0;
    private static int currentFrame = 0;
    long startTime;
    long nextEnemyTime;

    int gold;
    int health;
    int wave;

    public GameEngine(Level level) {
        this.level = level;
        nextEnemyTime = -1;
        wave = 0;

        placedTowers = new ArrayList<>();
        shownEnemies = new ArrayList<>();
        dyingEnemies = new ArrayList<>();
        availableTowers = new ArrayList<>();
        activeProjectiles = new ArrayList<>();
    }

    public void startWave() {
        wave++;
        nextWave = this.level.enemies.remove();
        if (!nextWave.isEmpty()) nextEnemyTime = System.currentTimeMillis() + nextWave.peek().spawnTime;
    }

    public void update(long lg) {
        currentFrame++;

        if (lg > 2147483647L) lg = 16;
        int lag = (int)lg;

        // wave paused
        if (nextEnemyTime == -1) {
            return;
        }

        if (System.currentTimeMillis() >= nextEnemyTime) {
            if (!nextWave.isEmpty()) {
                shownEnemies.add(nextWave.remove());
                if (!nextWave.isEmpty()) nextEnemyTime = System.currentTimeMillis() + nextWave.peek().spawnTime;
            }
        }

        // move enemy
        {
            ListIterator<Enemy> i = shownEnemies.listIterator();
            while (i.hasNext()) {
                Enemy e = i.next();
                Line leg;
                leg = this.level.paths.get(e.path).get(e.leg);

                e.absPosX += (e.speed * (lag * 0.001)) * leg.getDirection().first;
                e.absPosY += (e.speed * (lag * 0.001)) * leg.getDirection().second;

                if ((e.absPosX * leg.getSigns().first >= leg.getEnd().first * leg.getSigns().first) && (e.absPosY * leg.getSigns().second >= leg.getEnd().second * leg.getSigns().second)) {
                    e.leg++;

                    if (e.leg >= this.level.paths.get(e.path).size()) {
                        health--;
                        e.posX = leg.getEnd().first;
                        e.posY = leg.getEnd().second;
                        e.deathAnimationCount = 0;
                        //dyingEnemies.add(e);
                        i.remove();
                        continue;
                    }

                    e.absPosX = leg.getEnd().first;
                    e.absPosY = leg.getEnd().second;
                }

                e.posX = (int) Math.round(e.absPosX);
                e.posY = (int) Math.round(e.absPosY);
            }
        }

        // towers attack
        for (Tower t : this.placedTowers) {
            
        }

        // projectile movement & collision detection
        {
            ListIterator<Projectile> i = activeProjectiles.listIterator();
            while (i.hasNext()) {
                Projectile p = i.next();
                p.move(lag);

                if (p.isAuto()) {
                    if (rectangleCollision(p.getAbsPosX(), p.getAbsPosY(), p.getProjSizeX(), p.getProjSizeY(), p.target.absPosX, p.target.absPosY, p.target.sizeX, p.target.sizeY)) {
                        p.target.health -= p.tower.attackDamage[p.tower.level] - p.target.armor;
                        i.remove();
                    }
                }
            }
        }
    }

    public boolean rectangleCollision(double Ax, double Ay, int Aw, int Ah, double Bx, double By, int Bw, int Bh) {
        return  (Ax - Aw) < (Bx + Bw) &&
                (Ax + Aw) > (Bx - Bw) &&
                (Ay - Ah) < (By + Bh) &&
                (Ay + Ah) > (By - Bh);
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
            g.drawImage(t.image, t.posX-t.sizeX, t.posY-t.sizeY, null);
        }
        for (Projectile p : this.activeProjectiles) {
            g.drawImage(p.projectile, p.posX-p.projSizeX, p.posY-p.projSizeY, null);
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
            if (e.deathAnimationCount >= e.deathAnimations.length) {
                i.remove();
            }
            g.drawImage(e.deathAnimations[e.deathAnimationCount], e.posX-e.sizeX, e.posY-e.sizeY, null);
            e.deathAnimationCount++;
        }
    }

    //getters setters
    public static int getCurrentFrame(){
        return currentFrame;
    }

    public static double getFPS(){
        return FPS;
    }

    public ArrayList<Tower> getPlacedTowers(){
        return placedTowers;
    }
}
