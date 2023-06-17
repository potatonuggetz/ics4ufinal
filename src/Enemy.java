import java.util.ArrayList;
import java.awt.*;

abstract public class Enemy {
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

    //TODO: crowd control

    public Enemy(){
        
        enemyList.add(this); 
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
