package game;

public class TemporaryEvent {
    //affects the track/a location
    protected Pair<Integer,Integer> targetLocation;
    protected int radius;
    
    //affects a specific tower
    protected Tower targetTower;

    //affects a specific enemy
    protected Enemy targetEnemy;

    public TemporaryEvent(Pair<Integer,Integer> t,int r){

    }
}
