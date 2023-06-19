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
import java.util.Queue;

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
    Tower affectedTower;
    boolean targetingAbility;
    Ability selectedAbility;
    private Level level;
    private Queue<Enemy> nextWave;

    private static final double FPS = 60.0;
    private static int currentFrame = 0;
    long startTime;
    long nextEnemyTime;
    private boolean gamePaused;

    int gold;
    int health;
    int wave;
    boolean wavePaused;

    Font gamerfont, namefont, infofont, boldfont, descfont, descfonttiny;

    int mouseX;
    int mouseY;

    // images
    Image towerframe, towerframeSelected;
    Image ashe,caitlyn,ezreal,lulu,veigar,vex;
    Image goldDisplay, hpDisplay, waveStart, waveProgress, waveStartHover;
    Image waveIcon, heartIcon, goldIcon;
    Image[] deathAnimations;
    Image backbutton, backbuttonhover, quitbutton, quitbuttonhover;
    Image abilitycooldown, abilityready, abilityreadyhover;

    public GameEngine(Level level) {
        this.level = level;
        nextEnemyTime = -1;
        wave = 0;
        health = 15;
        gold = 100;
        gamePaused = false;

        // initialize a bunch of stuff
        placedTowers = new ArrayList<>();
        shownEnemies = new ArrayList<>();
        dyingEnemies = new ArrayList<>();
        availableTowers = new ArrayList<>();
        activeProjectiles = new ArrayList<>();
        tempEvents=new ArrayList<>();
        eventsToRemove=new HashMap<>();
        nextWave = new LinkedList<>();

        // fonts
        gamerfont = new Font("Consolas", Font.PLAIN,40);
        namefont = new Font("Calibri", Font.BOLD, 30);
        boldfont = new Font("Calibri", Font.BOLD, 23);
        infofont = new Font("Calibri", Font.PLAIN, 17);
        descfont = new Font("Calibri", Font.ITALIC, 17);
        descfonttiny = new Font("Calibri", Font.ITALIC, 12);

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
            backbutton = ImageIO.read(new File("img/ui/game_menu_backbutton.png"));
            backbuttonhover = ImageIO.read(new File("img/ui/game_menu_backbuttonhover.png"));
            quitbutton = ImageIO.read(new File("img/ui/game_menu_quitbutton.png"));
            quitbuttonhover = ImageIO.read(new File("img/ui/game_menu_quitbuttonhover.png"));
            abilitycooldown = ImageIO.read(new File("img/ui/game_menu_abilitycooldown.png"));
            abilityready = ImageIO.read(new File("img/ui/game_menu_abilityready.png"));
            abilityreadyhover = ImageIO.read(new File("img/ui/game_menu_abilityreadyhover.png"));

            deathAnimations = new Image[44];
            for (int i = 0; i < 44; i++) {
                deathAnimations[i] = ImageIO.read(new File("img/ui/boom/death" + (i+1) + ".png"));
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
        if (gamePaused) return;

        // for first time run
        if (lg > 2147483647L) lg = 16;
        int lag = (int)lg;

        // check if wave paused
        wavePaused = nextEnemyTime == -1 && shownEnemies.isEmpty() && nextWave.isEmpty();
        if (wavePaused) {
            return;
        }

        currentFrame++;

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
                            if(p.ability.pierce<=0) {
                                i.remove();
                                break;
                            }
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
                        if(l.getDistance()<=500) t.xp+=10;
                        if(t.xp>=100&&t.level<5) {
                            t.levelUp();
                        }
                    }
                    gold+=5;


                    dyingEnemies.add(e);

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
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(this.level.background, 0, 0, null);
        drawGameLogic(g);
        drawGameLayout(menu, g);
        if(placingTower) drawHoveredTower(g);
        if (selectedTower != null) {
            drawSelectedTower(menu, g);
        }
        if(targetingAbility) drawAbilityTarget(g);
        if (!gamePaused) drawEnemyDeath(g);

        if (gamePaused) {
            g.setColor(new Color(255, 255, 255, 127));
            g.fillRect(0, 0, 1280, 720);

            menu.drawButton(g2d, backbutton, backbuttonhover, 240, 210, 300, 300);
            menu.drawButton(g2d, quitbutton, quitbuttonhover, 740, 210, 300, 300);
        }
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

        g.setColor(new Color(255, 255, 0));
        g.setFont(new Font("Calibri",1,18));
        menu.drawButton(g2d, towerframe, towerframeSelected, 256*2, 600, 128, 120);
        g.drawImage(ashe, 256*2, 600, 128, 120,null);
        g.drawString("$100",256*2+20,630);
        menu.drawButton(g2d, towerframe, towerframeSelected, 256*2+128, 600, 128, 120);
        g.drawImage(caitlyn, 256*2+128, 600, 128, 120,null);
        g.drawString("$100",256*2+128+20,630);
        menu.drawButton(g2d, towerframe, towerframeSelected, 256*3, 600, 128, 120);
        g.drawImage(ezreal, 256*3, 600, 128, 120,null);
        g.drawString("$150",256*3+20,630);
        menu.drawButton(g2d, towerframe, towerframeSelected, 256*3+128, 600, 128, 120);
        g.drawImage(lulu, 256*3+128, 600, 128, 120,null);
        g.drawString("$200",256*3+128+20,630);
        menu.drawButton(g2d, towerframe, towerframeSelected, 256*4, 600, 128, 120);
        g.drawImage(veigar, 256*4, 600, 128, 120,null);
        g.drawString("$200",256*4+20,630);
        menu.drawButton(g2d, towerframe, towerframeSelected, 256*4+128, 600, 128, 120);
        g.drawImage(vex, 256*4+128, 600, 128, 120,null);
        g.drawString("$250",256*4+128+20,630);
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
    public void drawSelectedTower(Menu menu, Graphics g) {
        g.setColor(new Color(153, 204, 255,127));
        g.fillOval(selectedTower.posX-selectedTower.range,selectedTower.posY-selectedTower.range,selectedTower.range*2,selectedTower.range*2);

        g.setColor(new Color(0, 0, 0, 165));
        g.fillRect(896, 150, 128*3, 450);
        g.setColor(new Color(255,255,255));
        g.setFont(namefont);
        g.drawString(selectedTower.getName(), 906, 180);
        g.setFont(descfont);
        g.drawString(selectedTower.getDescription(), 906, 207);

        g.setFont(boldfont);
        g.drawString("Level: " + selectedTower.level, 906, 260);
        g.setFont(infofont);
        g.drawString("XP: " + selectedTower.xp + "/100", 906, 287);

        g.drawString("Damage: " + selectedTower.currentAttackDamage, 906, 340);
        g.drawString("Ability Power: " + selectedTower.currentAbilityPower, 906, 367);
        g.drawString("Attack Speed: " + selectedTower.currentAttackSpeed, 906, 394);

        g.setFont(boldfont);
        g.drawString("Ability: " + selectedTower.towerAbilities.get(0).name, 906, 447);
        g.setFont(descfonttiny);
        g.drawString(selectedTower.towerAbilities.get(0).description, 906, 466);
        g.drawString(selectedTower.towerAbilities.get(0).description2, 906, 485);

        g.fillRect(896, 500, 128*3, 100);

        if (selectedTower.towerAbilities.get(0).getCurrentCooldown() == 0) {
            menu.drawButton((Graphics2D) g, abilityready, abilityreadyhover, 896, 500, 128*3, 100);
            if(!targetingAbility) menu.drawCenteredString(g, "Activate", new Rectangle(896, 500, 128*3, 100), gamerfont, 0, 6);
            else menu.drawCenteredString(g, "Targeting Ability", new Rectangle(896, 500, 128*3, 100), gamerfont, 0, 6);
        } else {
            g.drawImage(abilitycooldown, 896, 500, null);
            menu.drawCenteredString(g, String.valueOf((int)selectedTower.towerAbilities.get(0).getCurrentCooldown()), new Rectangle(896, 500, 128*3, 100), gamerfont, 0, 6);
        }
    }

    public void drawHoveredTower(Graphics g){
        g.drawImage(hoveredTower.image,mouseX-hoveredTower.sizeX,mouseY-hoveredTower.sizeY,null);
        if(gold>=hoveredTower.gold) g.setColor(new Color(0,0,0,127));
        else g.setColor(new Color(255,0,0,127));
        g.fillOval(mouseX-hoveredTower.range,mouseY-hoveredTower.range,hoveredTower.range*2,hoveredTower.range*2);
    }

    public void drawAbilityTarget(Graphics g){
        g.setColor(new Color(255, 255, 255));
        g.drawOval(selectedTower.posX-selectedAbility.range, selectedTower.posY-selectedAbility.range, selectedAbility.range*2, selectedAbility.range*2);
    }

    // cycle through death animation
    public void drawEnemyDeath(Graphics g) {
        ListIterator<Enemy> i = dyingEnemies.listIterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (e.deathAnimationCount >= 44) {
                i.remove();
                continue;
            }
            g.drawImage(e.image, e.posX-e.sizeX, e.posY-e.sizeY, null);
            g.drawImage(deathAnimations[e.deathAnimationCount], e.posX-e.sizeX, e.posY-e.sizeY, null);
            e.deathAnimationCount++;
        }
    }

    public void pause() {
        gamePaused = !gamePaused;
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
        }else if(inRectangle(e, 0, 10, 700, 720)){
            gold+=100;
        }
        else if(inRectangle(e, 896, 896+128*3, 500, 600)&&selectedTower!=null&&selectedTower.towerAbilities.get(0).getCurrentCooldown()==0){
            selectedAbility=selectedTower.towerAbilities.get(0);
            targetingAbility=true;
        }else if(inRectangle(e, 0, 1280, 0, 600)&&targetingAbility){
            if(selectedAbility.name!="Whimsy"){
                selectedAbility.use(e.getX(),e.getY(),this);
            }
            else{
                Double d=Double.MAX_VALUE;
                affectedTower=null;
                for(Tower t:placedTowers){
                Line l=new Line(new Pair<Integer,Integer>(e.getX(), e.getY()), new Pair<Integer,Integer>(t.posX, t.posY));
                if(l.getDistance()<d&&inRectangle(e, t.posX-t.sizeX, t.posX+t.sizeX, t.posY-t.sizeY, t.posY+t.sizeY)){
                    d=l.getDistance();
                    affectedTower=t;
                }
                if(affectedTower!=null){
                    selectedAbility.timeLastUsed=GameEngine.getCurrentFrame();
                    addBuff(new TemporaryEvent(affectedTower, selectedAbility));
                }
                }
            }
            targetingAbility=false;
        }
        else if(inRectangle(e, 0, 1280, 0, 600)){
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
                if(hoveredTower.name.equals("Ashe")&&gold>=hoveredTower.gold) {
                    placedTowers.add(new Ashe(e.getX(),e.getY()));
                    gold-=hoveredTower.gold;
                }
                if(hoveredTower.name.equals("Caitlyn")&&gold>=hoveredTower.gold) {
                    placedTowers.add(new Caitlyn(e.getX(),e.getY()));
                    gold-=hoveredTower.gold;
                }
                if(hoveredTower.name.equals("Ezreal")&&gold>=hoveredTower.gold) {
                    placedTowers.add(new Ezreal(e.getX(),e.getY()));
                    gold-=hoveredTower.gold;
                }
                if(hoveredTower.name.equals("Lulu")&&gold>=hoveredTower.gold) {
                    placedTowers.add(new Lulu(e.getX(),e.getY()));
                    gold-=hoveredTower.gold;
                }
                if(hoveredTower.name.equals("Veigar")&&gold>=hoveredTower.gold) {
                    placedTowers.add(new Veigar(e.getX(),e.getY()));
                    gold-=hoveredTower.gold;
                }
                if(hoveredTower.name.equals("Vex")&&gold>=hoveredTower.gold) {
                    placedTowers.add(new Vex(e.getX(),e.getY()));
                    gold-=hoveredTower.gold;
                }
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

    public boolean isGamePaused() {
        return gamePaused;
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
