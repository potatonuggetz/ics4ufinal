package game;
public class Line {
    //basically this class just makes a vector between 2 points
    //calculates direction vector and magnitude
    //its just mcv4u
    private final Pair<Integer, Integer> start;
    private final Pair<Integer, Integer> end;
    private final Pair<Integer, Integer> signs;
    private final Pair<Double, Double> direction;
    private double distance;

    public Line(Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
        this.start = start;
        this.end = end;

        // get unit vector for direction
        Pair<Double, Double> d = new Pair<>(end.first - start.first + 0.0, end.second - start.second + 0.0);
        distance = Math.sqrt ( ( (end.first - start.first + 0.0) * (end.first - start.first + 0.0) ) + ( (end.second - start.second + 0.0) * (end.second - start.second + 0.0) ) );
        this.direction = new Pair<>(d.first / distance, d.second / distance);

        // get signs for direction
        this.signs = new Pair<>((int)(d.first / Math.abs(end.first - start.first + 0.0)), (int)(d.second / Math.abs(end.second - start.second + 0.0)));
    }

    public Pair<Integer, Integer> getStart() { return this.start; }
    public Pair<Integer, Integer> getEnd() { return this.end; }
    public Pair<Integer, Integer> getSigns() { return this.signs; }
    public Pair<Double, Double> getDirection() { return this.direction; }
    public double getDistance() { return this.distance; }
}
