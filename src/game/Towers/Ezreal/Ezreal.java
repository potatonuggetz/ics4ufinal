package game.Towers.Ezreal;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.*;

public class Ezreal extends Tower{
    public Ezreal(int x,int y){
        super(x,y);
        name="Ezreal";
        description="The Prodigal Explorer";
        attackDamage=new double[]{0,10,20,30,40,40};
        abilityPower=new double[]{0,0,0,0,0,0};
        attackSpeed=new double[]{0,0.8,0.9,1.3,1.7,2};
        projectileSpeed=75;
        range=150;
        try {
            this.image = ImageIO.read(new File("ezreal.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}