package game;
import java.util.ArrayList;
import java.awt.*;

abstract public class Enemy implements Comparable<Enemy>{
    protected String name;
    protected double health;
    protected double armor;
    protected double magicResist;
    private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();

    // visual
    protected int posX;
    protected int posY;
    protected int sizeX;
    protected int sizeY;
    protected Image image;
    protected Image[] deathAnimations;
    protected int deathAnimationCount;

    // movement
    protected double absPosX;
    protected double absPosY;
    protected double speed;
    protected int path;
    protected int leg;
    protected int distanceFromEnd;

    //TODO: crowd control

    public Enemy(){
        
        enemyList.add(this); 
    }

    //default sort is which enemy is further along the track
    public int compareTo(Enemy e){
        return e.distanceFromEnd-this.distanceFromEnd;
    }

    public String getName(){
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public double getHealth(){
        return health;
    }

    public double getArmor(){
        return armor;
    }

    public double getMagicResist(){
        return magicResist;
    }

    public ArrayList<Enemy> getEnemyList(){
        return enemyList;
    }
}
