import java.util.*;

public class SortEnemyLast implements Comparator<Enemy>{
    public int compare(Enemy e1, Enemy e2){
        return e1.distanceFromEnd-e2.distanceFromEnd;
    }
}
