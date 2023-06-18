package game.Towers.Caitlyn;
import game.*;

public class Caitlyn extends Tower{
    public Caitlyn(int x,int y){
        super(x,y);
        name="Caitlyn";
        description="The Sheriff of Piltover";
        attackDamage=new double[]{0,10,20,40,70,100};
        abilityPower=new double[]{0,0,0,0,0,0};
        attackSpeed=new double[]{0,0.6,0.7,0.9,1.2,1.5};
        projectileSpeed=75;
        range=300;
    }
}
