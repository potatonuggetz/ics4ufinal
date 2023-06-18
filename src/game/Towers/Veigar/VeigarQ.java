package game.Towers.Veigar;
import game.*;
public class VeigarQ extends Ability{
    public VeigarQ(Tower t){
        super(t);
        name="Baleful Strike";
        description="Veigar blasts a dark bolt in the target direction that deals magic damage to the first two enemies hit.";
        cooldown=new double[]{1000,6,5.5,5,4.5,4};
        projSizeX=30;
        projSizeY=30;
        range=150;
        magicDamage=true;
        scalingAD=0;
        scalingAP=1.5;
        pierce=2;
    }
    
    public void use(int x,int y,GameEngine g){
        timeLastUsed=GameEngine.getCurrentFrame();
        g.addProjectile(new Projectile(tower, this, new Pair<Integer,Integer>(x,y)));
    }
}
