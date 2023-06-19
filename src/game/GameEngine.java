package game;

import javax.imageio.ImageIO;

import game.Towers.Ashe.*;
import game.Towers.Caitlyn.*;
import game.Towers.Ezreal.*;
import game.Towers.Lulu.*;
import game.Towers.Veigar.*;
import game.Towers.Vex.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameEngine {

    private ArrayList<Tower> placedTowers;
    private ArrayList<Enemy> shownEnemies;
    private ArrayList<Enemy> dyingEnemies;
    private ArrayList<Projectile> activeProjectiles;
    private ArrayList<TemporaryEvent> tempEvents;
    private HashMap<Integer,TemporaryEvent> eventsToRemove;
    public ArrayList<Tower> availableTowers;
    Tower selectedTower;
    boolean placingTower;
    Tower hoveredTower;
    private Level level;
    private Queue<Enemy> nextWave;

    private static final double FPS = 60.0;
    private static int currentFrame = 0;
    long startTime;
    long nextEnemyTime;

    int gold;
    int health;
    int wave;
    boolean wavePaused;

    Font gamerfont;

    int mouseX;
    int mouseY;

    // images
    Image towerframe, towerframeSelected;
    Image ashe,caitlyn,ezreal,lulu,veigar,vex;
    Image goldDisplay, hpDisplay, waveStart, waveProgress, waveStartHover;
    Image waveIcon, heartIcon, goldIcon;
    Image[] deathAnimations;

    public GameEngine(Level level) {
        this.level = level;
        nextEnemyTime = -1;
        wave = 0;
        health = 15;
        gold = 100;

        // initialize a bunch of stuff
        placedTowers = new ArrayList<>();
        shownEnemies = new ArrayList<>();
        dyingEnemies = new ArrayList<>();
        availableTowers = new ArrayList<>();
        activeProjectiles = new ArrayList<>();
        tempEvents=new ArrayList<>();
        eventsToRemove=new HashMap<>();
        nextWave = new LinkedList<>();

        // font for gold, health, wave display
        gamerfont = new Font("Consolas", Font.PLAIN,40);

        // add images
        try {
            towerframe = ImageIO.read(new File("img/ui/game_menu_tower_frame.png"));
            towerframeSelected = ImageIO.read(new File("img/ui/game_menu_tower_selected.png"));
            goldDisplay = ImageIO.read(new File("img/ui/game_menu_golddisplay.png"));
            hpDisplay = ImageIO.read(new File("img/ui/game_menu_hpdisplay.png"));
            waveStart = ImageIO.read(new File("img/ui/game_menu_wavestart.png"));
            waveStartHover = ImageIO.read(new File("img/ui/game_menu_wavestarthover.png"));
            waveProgress = ImageIO.read(new File("img/ui/game_menu_waveprogress.png"));
            waveIcon = ImageIO.read(new File("img/ui/game_menu_waveicon.png"));
            heartIcon = ImageIO.read(new File("img/ui/game_menu_hearticon.png"));
            goldIcon = ImageIO.read(new File("img/ui/game_menu_goldicon.png"));
            ashe = ImageIO.read(new File("img/tower/ashe.png"));
            caitlyn = ImageIO.read(new File("img/tower/caitlyn.png"));
            ezreal = ImageIO.read(new File("img/tower/ezreal.png"));
            lulu = ImageIO.read(new File("img/tower/lulu.png"));
            veigar = ImageIO.read(new File("img/tower/veigar.png"));
            vex = ImageIO.read(new File("img/tower/vex.png"));
           

           deathAnimations = new Image[20];
           for (int i = 0; i < 20; i++) {
               deathAnimations[i] = ImageIO.read(new File("img/ui/boom/death" + i + ".png"));
           }
        } catch (IOException e) {

        }
    }

    /*
    Description: starts new wave
    Parameters/Return: none
     */
    public void startWave() {
        wave++;
        nextWave = this.level.enemies.remove();
        if (!nextWave.isEmpty()) nextEnemyTime = System.currentTimeMillis() + nextWave.peek().spawnTime;
    }

    /*
    Description: game loop, updates everything based on how much time since last call
    Parameters: long - lag: (approx) time since last call
    Return: none
     */
    public void update(long lg) {
        currentFrame++;

        // for first time run
        if (lg > 2147483647L) lg = 16;
        int lag = (int)lg;

        // check if wave paused
        wavePaused = nextEnemyTime == -1 && shownEnemies.isEmpty() && nextWave.isEmpty();
        if (wavePaused) {
            return;
        }

        //remove all buffs to be removed
        {
            if(eventsToRemove.containsKey(currentFrame)){
                TemporaryEvent e=eventsToRemove.get(currentFrame);
                if(e.type==TemporaryEvent.TARGET_ENEMY){
                    e.targetEnemy.currentSpeed/=e.ability.slowFactor;    
                }else if(e.type==TemporaryEvent.TARGET_TOWER){
                    e.targetTower.currentAttackDamage/=e.ability.attackDamageFactor;
                    e.targetTower.currentAbilityPower/=e.ability.abilityPowerFactor;
                    e.targetTower.currentAttackSpeed/=e.ability.attackSpeedFactor;
                }
                eventsToRemove.remove(currentFrame);
            }
        }

        //process all buffs
        {
            ListIterator<TemporaryEvent> i=tempEvents.listIterator();
            while(i.hasNext()){
                TemporaryEvent e=i.next();
                if(e.type==TemporaryEvent.TARGET_ENEMY){
                    e.targetEnemy.currentSpeed*=e.ability.slowFactor;
                    //if there are somehow 2 buffs expiring on the same frame then just offset it by 1
                    int j=0;
                    while(eventsToRemove.containsKey(currentFrame+(int)(e.ability.duration*FPS)+j)){
                        j++;
                    }
                    eventsToRemove.put(currentFrame+(int)(e.ability.duration*FPS)+j,e);
                }
                else if(e.type==TemporaryEvent.TARGET_TOWER){
                    e.targetTower.currentAttackDamage*=e.ability.attackDamageFactor;
                    e.targetTower.currentAbilityPower*=e.ability.abilityPowerFactor;
                    e.targetTower.currentAttackSpeed*=e.ability.attackSpeedFactor;
                    //if there are somehow 2 buffs expiring on the same frame then just offset it by 1
                    int j=0;
                    while(eventsToRemove.containsKey(currentFrame+(int)(e.ability.duration*FPS)+j)){
                        j++;
                    }
                    eventsToRemove.put(currentFrame+(int)(e.ability.duration*FPS)+j,e);
                }
                i.remove();
            }
        }

        // add next enemy
        if (System.currentTimeMillis() >= nextEnemyTime && nextEnemyTime != -1) {
            shownEnemies.add(nextWave.remove());
            if (!nextWave.isEmpty()) {
                nextEnemyTime = System.currentTimeMillis() + nextWave.peek().spawnTime;
            } else {
                nextEnemyTime = -1;
            }
        }

        // move enemy
        {
            ListIterator<Enemy> i = shownEnemies.listIterator();
            while (i.hasNext()) {
                Enemy e = i.next();
                Line leg;
                leg = this.level.paths.get(e.path).get(e.leg);

                e.absPosX += (e.currentSpeed * (lag * 0.001)) * leg.getDirection().first;
                e.absPosY += (e.currentSpeed * (lag * 0.001)) * leg.getDirection().second;

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
                        Projectile p=new Projectile(t, e);
                        if(t.name.equals("Ashe")) p.hasSlow=true;
                        activeProjectiles.add(p);
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
                        p.target.health -= p.tower.currentAttackDamage - p.target.armor;
                        if(p.hasSlow){
                            //only ashe has slow passive
                            tempEvents.add(new TemporaryEvent(p.target,new AsheP(p.tower)));
                        }
                        i.remove();
                    }
                }else{
                    ArrayList<Enemy> hittableEnemies = new ArrayList<>(shownEnemies);
                    ListIterator<Enemy> j=hittableEnemies.listIterator();
                    while(j.hasNext()){
                        Enemy e=j.next();
                        if (rectangleCollision(p.getAbsPosX(), p.getAbsPosY(), p.getProjSizeX(), p.getProjSizeY(), e.absPosX, e.absPosY, e.sizeX, e.sizeY)) {
                            if(p.ability.magicDamage){
                                e.health-=(((p.tower.currentAttackDamage*p.ability.scalingAD)+(p.tower.currentAbilityPower*p.ability.scalingAP))*(e.magicResist/100));
                            } else{
                                e.health-=(((p.tower.currentAttackDamage*p.ability.scalingAD)+(p.tower.currentAbilityPower*p.ability.scalingAP))-(e.armor));
                            }
                            tempEvents.add(new TemporaryEvent(e,p.ability));
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
                            t.levelUp();
                        }
                    }
                    i.remove();
                }
            }
        }
    }

    /*
    Description: checks if two rectangles collide
    Parameters: rectangle width and heights (to calculate sides)
    Return: boolean - do the rectangles collide?
     */
    public boolean rectangleCollision(double Ax, double Ay, int Aw, int Ah, double Bx, double By, int Bw, int Bh) {
        return  (Ax - Aw) < (Bx + Bw) &&
                (Ax + Aw) > (Bx - Bw) &&
                (Ay - Ah) < (By + Bh) &&
                (Ay + Ah) > (By - Bh);
    }

    /*
    Description: drawing loop
    Parameters: Menu, Graphics (to draw)
    Return: none
     */
    public void draw(Menu menu, Graphics g) {
        g.drawImage(this.level.background, 0, 0, null);
        drawGameLogic(g);
        drawGameLayout(menu, g);
        if(placingTower) drawHoveredTower(g);
        if (selectedTower != null) {
            drawSelectedTower(g);
        }
        drawEnemyDeath(g);
    }

    /*
    Description: draws menu at bottom, gold, health
    Parameters: Menu, Graphics (to draw)
    Return: none
     */
    public void drawGameLayout(Menu menu, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        FontMetrics metrics = g.getFontMetrics(gamerfont);
        g.setFont(gamerfont);
        g.drawImage(hpDisplay, 0, 600, null);
        g.drawImage(heartIcon, (171 - (10+heartIcon.getWidth(null)+metrics.stringWidth(String.valueOf(health)))) / 2, 644, null);
        g.drawString(String.valueOf(health), ((171 - (10+heartIcon.getWidth(null)+metrics.stringWidth(String.valueOf(health)))) / 2) + heartIcon.getWidth(null)+5, 644 + metrics.getAscent());
        g.drawImage(goldDisplay, 171, 600, null);
        g.drawImage(goldIcon, 171 + ((171 - (6+goldIcon.getWidth(null)+metrics.stringWidth(String.valueOf(gold)))) / 2), 644, null);
        g.drawString(String.valueOf(gold), (171 + ((171 - (10+goldIcon.getWidth(null)+metrics.stringWidth(String.valueOf(gold)))) / 2)) + goldIcon.getWidth(null)+3, 644 + metrics.getAscent());

        if (wavePaused) {
            menu.drawButton(g2d, waveStart, waveStartHover, 342, 600, 170, 120);
        } else {
            g.drawImage(waveProgress, 342, 600, null);
        }
        g.drawImage(waveIcon, 342 + ((170 - (10+waveIcon.getWidth(null)+metrics.stringWidth(String.valueOf(wave)))) / 2), 644, null);
        g.drawString(String.valueOf(wave), (342 + ((170 - (10+waveIcon.getWidth(null)+metrics.stringWidth(String.valueOf(wave)))) / 2)) + waveIcon.getWidth(null)+5, 644 + metrics.getAscent());

        menu.drawButton(g2d, towerframe, towerframeSelected, 256*2, 600, 128, 120);
        g.drawImage(ashe, 256*2, 600, 128, 120,null);
        menu.drawButton(g2d, towerframe, towerframeSelected, 256*2+128, 600, 128, 120);
        g.drawImage(caitlyn, 256*2+128, 600, 128, 120,null);
        menu.drawButton(g2d, towerframe, towerframeSelected, 256*3, 600, 128, 120);
        g.drawImage(ezreal, 256*3, 600, 128, 120,null);
        menu.drawButton(g2d, towerframe, towerframeSelected, 256*3+128, 600, 128, 120);
        g.drawImage(lulu, 256*3+128, 600, 128, 120,null);
        menu.drawButton(g2d, towerframe, towerframeSelected, 256*4, 600, 128, 120);
        g.drawImage(veigar, 256*4, 600, 128, 120,null);
        menu.drawButton(g2d, towerframe, towerframeSelected, 256*4+128, 600, 128, 120);
        g.drawImage(vex, 256*4+128, 600, 128, 120,null);
    }

    /*
    Description: draws towers, enemies, "bullets"
    Parameters: Graphics (to draw)
    Return: none
     */
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

    /*
    Description: draws tower highlight, tower info, ability buttons
    Parameters: Graphics (to draw)
    Return: none
     */
    public void drawSelectedTower(Graphics g) {
        g.setColor(new Color(0,0,0,127));
        g.fillOval(selectedTower.posX-selectedTower.range,selectedTower.posY-selectedTower.range,selectedTower.range*2,selectedTower.range*2);
    }

    public void drawHoveredTower(Graphics g){
        g.drawImage(hoveredTower.image,mouseX-hoveredTower.sizeX,mouseY-hoveredTower.sizeY,null);
        g.setColor(new Color(0,0,0,127));
        g.fillOval(mouseX-hoveredTower.range,mouseY-hoveredTower.range,hoveredTower.range*2,hoveredTower.range*2);
    }

    // cycle through death animation
    public void drawEnemyDeath(Graphics g) {
        ListIterator<Enemy> i = dyingEnemies.listIterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (e.deathAnimationCount > 20) {
                i.remove();
            }
            g.drawImage(deathAnimations[e.deathAnimationCount], e.posX-e.sizeX, e.posY-e.sizeY, null);
            e.deathAnimationCount++;
        }
    }

    private Point myGetMousePosition() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    private boolean inRectangle(int x, int y, int left, int right, int up, int down) {
        return x > left && x < right && y > up && y < down;
    }

    private boolean inRectangle(MouseEvent e, int left, int right, int up, int down) {
        return e.getX() > left && e.getX() < right && e.getY() > up && e.getY() < down;
    }

    public void mouseMoved(MouseEvent e){
        mouseX=e.getX();
        mouseY=e.getY();
    }

    public void mouseDragged(MouseEvent e){
        mouseX=e.getX();
        mouseY=e.getY();
    }

    public void mouseClicked(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        if (inRectangle(e, 384, 512, 600, 720) && wavePaused) {
            startWave();
        }else if(inRectangle(e, 0, 1280, 0, 600)){
            Double d=Double.MAX_VALUE;
            selectedTower=null;
            for(Tower t:placedTowers){
                Line l=new Line(new Pair<Integer,Integer>(e.getX(), e.getY()), new Pair<Integer,Integer>(t.posX, t.posY));
                if(l.getDistance()<d&&inRectangle(e, t.posX-t.sizeX, t.posX+t.sizeX, t.posY-t.sizeY, t.posY+t.sizeY)){
                    d=l.getDistance();
                    selectedTower=t;
                }
            }
        }
        else if(inRectangle(e,256*2,256*2+128,600,720)){
            placingTower=true;
            hoveredTower=new Ashe(0,0);
        }else if(inRectangle(e,256*2+128,256*3,600,720)){
            placingTower=true;
            hoveredTower=new Caitlyn(0,0);
        }else if(inRectangle(e,256*3,256*3+128,600,720)){
            placingTower=true;
            hoveredTower=new Ezreal(0,0);
        }else if(inRectangle(e,256*3+128,256*4,600,720)){
            placingTower=true;
            hoveredTower=new Lulu(0,0);
        }else if(inRectangle(e,256*4,256*4+128,600,720)){
            placingTower=true;
            hoveredTower=new Veigar(0,0);
        }else if(inRectangle(e,256*4+128,256*5,600,720)){
            placingTower=true;
            hoveredTower=new Vex(0,0);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(placingTower){
            if(inRectangle(e, 0, 1280, 0, 600)){
                if(hoveredTower.name.equals("Ashe")) placedTowers.add(new Ashe(e.getX(),e.getY()));
                if(hoveredTower.name.equals("Caitlyn")) placedTowers.add(new Caitlyn(e.getX(),e.getY()));
                if(hoveredTower.name.equals("Ezreal")) placedTowers.add(new Ezreal(e.getX(),e.getY()));
                if(hoveredTower.name.equals("Lulu")) placedTowers.add(new Lulu(e.getX(),e.getY()));
                if(hoveredTower.name.equals("Veigar")) placedTowers.add(new Veigar(e.getX(),e.getY()));
                if(hoveredTower.name.equals("Vex")) placedTowers.add(new Vex(e.getX(),e.getY()));
            }
            placingTower=false;
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

    public void addBuff(TemporaryEvent e){
        tempEvents.add(e);
    }
}
