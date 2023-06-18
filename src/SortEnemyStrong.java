import java.util.*;

public class SortEnemyStrong implements Comparator<Enemy>{
    public int compare(Enemy e1,Enemy e2){
        return (int)(e1.health-e2.health);
    }
}
