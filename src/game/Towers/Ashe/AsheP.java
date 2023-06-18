package game.Towers.Ashe;
import game.*;
public class AsheP extends Ability{
    public AsheP(Tower t){
        super(t);
        name="Frost Shot";
        description="Ashe's basic attacks and ability hits apply Frost to enemies for 2 seconds, which slows them by 20% for the duration. This ability is always active.";
        cooldown=new double[]{727,727,727,727,727,727};
        projSizeX=5;
        projSizeY=5;
        range=200;
        magicDamage=false;
        scalingAD=0;
        scalingAP=0;
        pierce=727;
        slowFactor=0.8;
        duration=2;
    }
    
    public void use(int x,int y,GameEngine g){
        timeLastUsed=GameEngine.getCurrentFrame();
    }
}
