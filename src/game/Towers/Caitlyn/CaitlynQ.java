package game.Towers.Caitlyn;
import game.*;
public class CaitlynQ extends Ability{
    public CaitlynQ(Tower t){
        super(t);
        name="Piltover Peacemaker";
        description="Caitlyn fires a piercing shot in the target direction that deals physical damage to all enemies it passes through";
        cooldown=new double[]{1000,10,9,8,7,6};
        projSizeX=25;
        projSizeY=25;
        range=500;
        magicDamage=false;
        scalingAD=3.5;
        scalingAP=0;
        pierce=727;
    }
    
    public void use(int x,int y,GameEngine g){
        timeLastUsed=GameEngine.getCurrentFrame();
        g.addProjectile(new Projectile(tower, this, new Pair<Integer,Integer>(x,y)));
    }
}
