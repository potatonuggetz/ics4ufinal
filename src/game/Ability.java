package game;
import java.awt.*;

abstract public class Ability {
    protected String name;
    protected String description;
    protected double[] cooldown; //measured in seconds
    protected double timeLastUsed; //frame that ability was last used
    protected Tower tower;

    // skillshot
    protected boolean magicDamage;
    protected double scalingAD;
    protected double scalingAP;
    protected int pierce;
    protected int range;

    protected int projSizeX;
    protected int projSizeY;
    protected Image projectile;

    //aoe spell
    protected int radius;
    protected double duration;

    //enemy-targeted event (only slow)
    protected double slowFactor=1;

    //tower-targeted event
    protected double attackDamageFactor=1;
    protected double attackSpeedFactor=1;


    public Ability(Tower t){
        tower=t;
    }

    protected abstract void use(int x,int y,GameEngine g);//make sure cooldown is set to current time

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public double getCooldown(){
        return cooldown[tower.getLevel()];
    }

    public double getCurrentCooldown(){
        //returns either 0 or the cooldown length remaining in frames
        return Math.max(0,(cooldown[tower.getLevel()]*GameEngine.getFPS())-(GameEngine.getCurrentFrame()-timeLastUsed));
    }
}
