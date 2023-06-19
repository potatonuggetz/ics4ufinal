package game.Towers.Ashe;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.*;

public class Ashe extends Tower{
    public Ashe(int x,int y){
        super(x,y);
        name="Ashe";
        description="The Frost Archer";
        attackDamage=new double[]{0,10,20,30,40,50};
        abilityPower=new double[]{0,0,0,0,0,0};
        attackSpeed=new double[]{0,0.6,0.8,1.3,1.9,3};
        projectileSpeed=125;
        range=150;
        currentAttackDamage=attackDamage[level];
        currentAbilityPower=abilityPower[level];
        currentAttackSpeed=attackSpeed[level];
        sizeX=44;
        sizeY=50;
        towerAbilities.add(new AsheW(this));
        try {
            this.image = ImageIO.read(new File("img/tower/ashe.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
