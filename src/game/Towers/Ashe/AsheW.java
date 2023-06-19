package game.Towers.Ashe;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.*;
public class AsheW extends Ability{
    public AsheW(Tower t){
        super(t);
        name="Volley";
        description="Ashe shoots a volley of arrows in a cone in the target direction, each dealing";
        description2="physical damage to the first enemy hit. Ashe's attacks also slow enemies.";
        cooldown=new double[]{1000,8,7,6,5,4};
        projSizeX=2;
        projSizeY=2;
        range=200;
        magicDamage=false;
        scalingAD=3.5;
        scalingAP=0;
        pierce=1;
        slowFactor=0.6;
        duration=2;
        try {
            projectile = ImageIO.read(new File("img/tower/asheArrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void use(int x,int y,GameEngine g){
        timeLastUsed=GameEngine.getCurrentFrame();
        for(int i=-5;i<=5;i++){
            g.addProjectile(new Projectile(tower, this, new Pair<Integer,Integer>((int)(x*Math.cos(Math.toRadians(5*i))-y*Math.sin(Math.toRadians(5*i))),(int)(x*Math.sin(Math.toRadians(5*i))+y*Math.cos(Math.toRadians(5*i))))));
        }
        
    }
}
