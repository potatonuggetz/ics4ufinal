package game.Towers.Vex;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.*;

public class Vex extends Tower{
    public Vex(int x,int y){
        super(x,y);
        name="Vex";
        description="The Gloomist";
        attackDamage=new double[]{0,10,15,20,25,30};
        abilityPower=new double[]{0,50,100,150,200,250};
        attackSpeed=new double[]{0,0.6,0.7,0.8,0.9,1.0};
        projectileSpeed=75;
        range=100;
        currentAttackDamage=attackDamage[level];
        currentAbilityPower=abilityPower[level];
        currentAttackSpeed=attackSpeed[level];
        sizeX=37;
        sizeY=50;
        towerAbilities.add(new VexQ(this));
        try {
            this.image = ImageIO.read(new File("img/tower/vex.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
