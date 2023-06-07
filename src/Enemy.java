import java.util.ArrayList;
import java.awt.*;

abstract public class Enemy {
    protected String name;
    protected double speed;
    protected double health;
    protected double armor;
    protected double magicResist;
    private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();

    protected int posX;
    protected int posY;
    protected int path;
    protected Image image;

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
