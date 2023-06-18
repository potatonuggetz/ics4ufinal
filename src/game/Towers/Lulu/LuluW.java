package game.Towers.Lulu;
import game.*;
public class LuluW extends Ability{
    public LuluW(Tower t){
        super(t);
        name="Whimsy";
        description="Lulu instantly casts erratic magic upon the target allied champion or herself, granting the target bonus attack speed and increased attack damage and ability power.";
        cooldown=new double[]{1000,18,17,16,15,13};
        projSizeX=15;
        projSizeY=15;
        range=200;
        magicDamage=true;
        scalingAD=0;
        scalingAP=1.5;
        pierce=2;
        duration=5;
        attackDamageFactor=1.5;
        attackSpeedFactor=2;
        abilityPowerFactor=1.5;
    }
    
    public void use(Tower t,GameEngine g){
        timeLastUsed=GameEngine.getCurrentFrame();
        g.addBuff(new TemporaryEvent(t, this));
    }

    //will never be used
	protected void use(int x, int y, GameEngine g) {
		System.out.println("somehow an error occurred");
	}
}
