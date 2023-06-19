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
        projectileSpeed=500;
        range=150;
        currentAttackDamage=attackDamage[level];
        currentAbilityPower=abilityPower[level];
        currentAttackSpeed=attackSpeed[level];
        sizeX=25;
        sizeY=50;
        towerAbilities.add(new EzrealQ(this));
        try {
            this.image = ImageIO.read(new File("img/tower/ezreal.png"));
            this.projectileAuto=ImageIO.read(new File("img/tower/ezrealAuto.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
