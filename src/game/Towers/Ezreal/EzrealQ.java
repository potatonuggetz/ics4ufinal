package game.Towers.Ezreal;
import game.*;
public class EzrealQ extends Ability{
    public EzrealQ(Tower t){
        super(t);
        name="Mystic Shot";
        description="Ezreal fires a bolt of energy in the target direction that deals physical damage to the first enemy hit.";
        cooldown=new double[]{1000,4,3.5,3,2,1};
        projSizeX=17;
        projSizeY=17;
        range=200;
        magicDamage=false;
        scalingAD=3;
        scalingAP=0;
        pierce=1;
    }
    
    public void use(int x,int y,GameEngine g){
        timeLastUsed=GameEngine.getCurrentFrame();
        g.addProjectile(new Projectile(tower, this, new Pair<Integer,Integer>(x,y)));
    }
}
