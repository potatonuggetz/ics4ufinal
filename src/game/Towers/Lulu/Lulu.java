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
        projectileSpeed=350;
        range=110;
        currentAttackDamage=attackDamage[level];
        currentAbilityPower=abilityPower[level];
        currentAttackSpeed=attackSpeed[level];
        sizeX=31;
        sizeY=50;
        gold=200;
        targeting=TARGETING_LAST;
        towerAbilities.add(new LuluW(this));
        try {
            this.image = ImageIO.read(new File("img/tower/lulu.png"));
            this.projectileAuto=ImageIO.read(new File("img/tower/luluAuto.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
