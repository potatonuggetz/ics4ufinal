package game;

public class TemporaryEvent {
    //for all events
    protected Ability ability; //the ability that creates the event, all info comes from it
    protected int type; //which type of event
    public static final int TARGET_LOCATION=0;
    public static final int TARGET_TOWER=1;
    public static final int TARGET_ENEMY=2;

    //affects the track/a location
    protected Pair<Integer,Integer> targetLocation;
    
    //affects a specific tower
    protected Tower targetTower;

    //affects a specific enemy
    protected Enemy targetEnemy;

    //location-targeted event
    public TemporaryEvent(Pair<Integer,Integer> t,Ability a){
        targetLocation=t;
        ability=a;
        type=TARGET_LOCATION;
    }

    //tower-targeted event
    public TemporaryEvent(Tower t,Ability a){
        targetTower=t;
        ability=a;
        type=TARGET_TOWER;
    }

    //enemy-targeted event
    public TemporaryEvent(Enemy e,Ability a){
        targetEnemy=e;
        ability=a;
        type=TARGET_ENEMY;
    }
}
