package game;
import java.awt.*;
import java.util.*;

abstract public class Tower {
    // info
    protected String name;
    protected String description;
    protected int level=1; //level of the tower, increases with respect to time, maxes at 5
    protected int xp; //xp, used to calculate level
    protected int gold;

    // attack
    protected double currentAttackDamage;
    protected double currentAbilityPower;
    protected double currentAttackSpeed;
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

    public void levelUp(){
        xp-=100;
        if(level<5) level++;
        if(currentAttackDamage==attackDamage[level-1]) currentAttackDamage=attackDamage[level];
        else currentAttackDamage=(currentAttackDamage/attackDamage[level-1])*attackDamage[level];
        if(currentAbilityPower==abilityPower[level-1]) currentAbilityPower=abilityPower[level];
        else currentAbilityPower=(currentAbilityPower/abilityPower[level-1])*abilityPower[level];
        if(currentAttackSpeed==attackSpeed[level-1]) currentAttackSpeed=attackSpeed[level];
        else currentAttackSpeed=(currentAttackSpeed/attackSpeed[level-1])*attackSpeed[level];
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
        return Math.max(0,(GameEngine.getFPS()/currentAttackSpeed)-(GameEngine.getCurrentFrame()-timeLastAttacked));
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
