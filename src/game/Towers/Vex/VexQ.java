package game.Towers.Vex;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.*;
public class VexQ extends Ability{
    public VexQ(Tower t){
        super(t);
        name="Mistral Bolt";
        description="Vex launches a wave of mist in the target direction that stuns and deals";
        description2="magic damage to enemies hit.";
        cooldown=new double[]{1000,8,7,6,5,4};
        projSizeX=50;
        projSizeY=50;
        range=200;
        magicDamage=true;
        scalingAD=0;
        scalingAP=2;
        pierce=727;
        duration=1.5;
        slowFactor=0.25;
        try {
            projectile = ImageIO.read(new File("img/tower/vexQ.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void use(int x,int y,GameEngine g){
        timeLastUsed=GameEngine.getCurrentFrame();
        g.addProjectile(new Projectile(tower, this, new Pair<Integer,Integer>(x,y)));
    }
}
