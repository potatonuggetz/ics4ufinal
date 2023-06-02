

public class Ability {
    private String name;
    private String description;

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
}
