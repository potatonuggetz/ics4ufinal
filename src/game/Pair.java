package game;
public class Pair <U, V> {
    //implements pair class
    //literally just a pair
    public final U first;
    public final V second;

    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pair<?, ?> p = (Pair<?, ?>) o;
        return p.first.equals(this.first) && p.second.equals(this.second);
    }

    public String toString() {
        return this.first.toString() + "," + this.second.toString();
    }
}
