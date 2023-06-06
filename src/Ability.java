

public class Ability {
    private String name;
    private String description;
    private double cooldown;
    private double currentCooldown;


    public Ability(String n, String desc){
        name=n;
        description=desc;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public double getCooldown(){
        return cooldown;
    }

    public double getCurrentCooldown(){
        return currentCooldown;
    }
}
