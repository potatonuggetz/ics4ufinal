

abstract class Ability {
    protected String name;
    protected String description;
    protected double[] cooldown;
    protected double timeLastUsed;
    protected Tower tower;


    public Ability(Tower t){
        tower=t;
    }

    abstract void use();//make sure cooldown is set to current time

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public double getCooldown(){
        return cooldown[tower.getLevel()];
    }

    public double getCurrentCooldown(){
        //returns either 0 or the cooldown length remaining
        return Math.max(0,cooldown[0]-((System.currentTimeMillis()-timeLastUsed)/1000));
    }
}
