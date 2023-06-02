import java.lang.reflect.Array;
import java.util.*;

abstract public class Tower {
    protected String name;
    protected String description;
    protected double attackDamage;
    protected double abilityPower;
    protected ArrayList<Ability> towerAbilities = new ArrayList<Ability>();
    private static ArrayList<Tower> towerList = new ArrayList<Tower>();

    public Tower(){

        towerList.add(this);
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public double getAttackDamage(){
        return attackDamage;
    }

    public double getAbilityPower(){
        return abilityPower;
    }

    public ArrayList<Ability> getAbilities(){
        return towerAbilities;
    }

    public ArrayList<Tower> getTowers(){
        return towerList;
    }
}
