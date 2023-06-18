package game;
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

                int distance1=0;
                for(int j=e.leg+1;j<level.paths.get(e.path).size();j++){
                    distance1+=level.paths.get(e.path).get(j).getDistance();
                }
                distance1+=new Line(new Pair<>(e.posX,e.posY),level.paths.get(e.path).get(e.leg).getEnd()).getDistance();
                e.distanceFromEnd=distance1;

            }
        }

        // towers attack
        for (Tower t : this.placedTowers) {
            if(t.getCurrentAttackCooldown()==0){
                if(t.targeting==Tower.TARGETING_FIRST) Collections.sort(shownEnemies);
                else if(t.targeting==Tower.TARGETING_LAST) Collections.sort(shownEnemies,new SortEnemyLast());
                else if(t.targeting==Tower.TARGETING_STRONG) Collections.sort(shownEnemies,new SortEnemyStrong());
                for(Enemy e:shownEnemies){
                    Line x=new Line(new Pair<>(t.posX,t.posY),new Pair<>(e.posX,e.posY));
                    if(x.getDistance()<=t.range){
                        activeProjectiles.add(new Projectile(t, e));
                        t.timeLastAttacked=currentFrame;
                        break;
                    }
                }
            }
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
                }else{
                    ArrayList<Enemy> hittableEnemies = new ArrayList<>(shownEnemies);
                    ListIterator<Enemy> j=hittableEnemies.listIterator();
                    while(j.hasNext()){
                        Enemy e=j.next();
                        if (rectangleCollision(p.getAbsPosX(), p.getAbsPosY(), p.getProjSizeX(), p.getProjSizeY(), e.absPosX, e.absPosY, e.sizeX, e.sizeY)) {
                            if(p.ability.magicDamage){
                                e.health-=(((p.tower.attackDamage[p.tower.level]*p.ability.scalingAD)+(p.tower.abilityPower[p.tower.level]*p.ability.scalingAP))*(e.magicResist/100));
                            } else{
                                e.health-=(((p.tower.attackDamage[p.tower.level]*p.ability.scalingAD)+(p.tower.abilityPower[p.tower.level]*p.ability.scalingAP))-(e.armor));
                            }
                            j.remove();
                            p.ability.pierce--;
                            if(p.ability.pierce<=0) i.remove();
                        }
                    }
                }
            }
        }

        //check for dead enemies
        {
            ListIterator<Enemy> i = shownEnemies.listIterator();
            while (i.hasNext()) {
                Enemy e=i.next();
                if(e.health<=0){
                    for(Tower t:placedTowers){
                        Line l=new Line(new Pair<>(e.posX,e.posY),new Pair<>(e.posX,e.posY));
                        if(l.getDistance()<=250) t.xp+=10;
                        if(t.xp>=100&&t.level<5) {
                            t.xp=0;
                            t.level++;
                        }
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
        g.drawImage(this.level.background, 0, 0, null);
        drawGameLogic(g);
        drawGameLayout(g);
        if (selectedTower != null) {
            drawSelectedTower(g);
        }
        drawEnemyDeath(g);
    }

    // menu at bottom, gold, health, background
    public void drawGameLayout(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRect(0, 600, 1280, 120);
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
    
    public void addProjectile(Projectile p){
        activeProjectiles.add(p);
    }
}
