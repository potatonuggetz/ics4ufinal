package game.Towers.Caitlyn;
import game.*;
public class CaitlynQ extends Ability{
    public CaitlynQ(Tower t){
        super(t);
        name="Piltover Peacemaker";
        description="Caitlyn fires a piercing shot in the target direction that deals physical damage to all enemies it passes through";
        cooldown=new double[]{1000,10,9,8,7,6};
    }
    
    public void use(){
        timeLastUsed=GameEngine.getCurrentFrame();

    }
}
