package game.Towers.Lulu;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.*;

public class Lulu extends Tower{
    public Lulu(int x,int y){
        super(x,y);
        name="Lulu";
        description="The Fae Sorceress";
        attackDamage=new double[]{0,1,2,3,4,5};
        abilityPower=new double[]{0,10,20,30,40,50};
        attackSpeed=new double[]{0,0.6,0.7,0.9,1.2,1.5};
        projectileSpeed=75;
        range=200;
        towerAbilities.add(new LuluW(this));
        try {
            this.image = ImageIO.read(new File("img/tower/lulu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
