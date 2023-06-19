package game.Towers.Veigar;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.*;

public class Veigar extends Tower{
    public Veigar(int x,int y){
        super(x,y);
        name="Veigar";
        description="The Tiny Master of Evil";
        attackDamage=new double[]{0,2,4,6,8,10};
        abilityPower=new double[]{0,25,50,100,200,400};
        attackSpeed=new double[]{0,0.3,0.35,0.4,0.45,0.5};
        projectileSpeed=20;
        range=100;
        currentAttackDamage=attackDamage[level];
        currentAbilityPower=abilityPower[level];
        currentAttackSpeed=attackSpeed[level];
        sizeX=29;
        sizeY=50;
        towerAbilities.add(new VeigarQ(this));
        try {
            this.image = ImageIO.read(new File("img/tower/veigar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
