package game;
import java.awt.*;
import java.util.*;

abstract public class Tower {
    // info
    protected String name;
    protected String description;
    protected int level; //level of the tower, increases with respect to time, maxes at 5
    protected int xp; //xp, used to calculate level

    // attack
    protected double[] attackDamage;
    protected double[] attackSpeed; //attacks per second
    protected double projectileSpeed;
    protected double timeLastAttacked; //frame when last attacked
    public static final int TARGETING_FIRST=0;
    public static final int TARGETING_LAST=1;
    public static final int TARGETING_STRONG=2;
    protected int targeting;
    protected int range;

    // ability
    protected double[] abilityPower;
    protected ArrayList<Ability> towerAbilities = new ArrayList<Ability>();
    private static ArrayList<Tower> towerList = new ArrayList<Tower>();

    // visual
    protected int posX;
    protected int posY;
    protected int sizeX;
    protected int sizeY;
    protected Image image;

    protected int projSizeX;
    protected int projSizeY;
    protected Image projectileAuto;

    public Tower(int x,int y){
        posX=x;
        posY=y;
        towerList.add(this);
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public double getAttackDamage(){
        return attackDamage[level];
    }

    public double getAbilityPower(){
        return abilityPower[level];
    }

    public ArrayList<Ability> getAbilities(){
        return towerAbilities;
    }

    public ArrayList<Tower> getTowers(){
        return towerList;
    }

    public double getAttackSpeed(){
        return attackSpeed[level];
    }

    public double getCurrentAttackCooldown(){
        return Math.max(0,(GameEngine.getFPS()/attackSpeed[level])-(GameEngine.getCurrentFrame()-timeLastAttacked));
    }

    public int getLevel(){
        return level;
    }

    public int getXP(){
        return xp;
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }
}
