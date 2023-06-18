package game.Towers.Ashe;
import game.*;
public class AsheW extends Ability{
    public AsheW(Tower t){
        super(t);
        name="Volley";
        description="Ashe shoots a volley of arrows in a cone in the target direction, each dealing physical damage to the first enemy hit.";
        cooldown=new double[]{1000,8,7,6,5,4};
        projSizeX=5;
        projSizeY=5;
        range=200;
        magicDamage=false;
        scalingAD=3.5;
        scalingAP=0;
        pierce=727;
    }
    
    public void use(int x,int y,GameEngine g){
        timeLastUsed=GameEngine.getCurrentFrame();
        for(int i=-5;i<=5;i++){
            g.addProjectile(new Projectile(tower, this, new Pair<Integer,Integer>((int)(x*Math.cos(Math.toRadians(5*i))-y*Math.sin(Math.toRadians(5*i))),(int)(x*Math.sin(Math.toRadians(5*i))+y*Math.cos(Math.toRadians(5*i))))));
        }
    }
}
