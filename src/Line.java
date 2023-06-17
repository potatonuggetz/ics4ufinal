public class Line {
    private final Pair<Integer, Integer> start;
    private final Pair<Integer, Integer> end;
    private final Pair<Double, Double> direction;

    public Line(Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
        this.start = start;
        this.end = end;

        // get unit vector for direction
        Pair<Double, Double> d = new Pair<>(end.first - start.first + 0.0, end.second - start.second + 0.0);
        Double m = Math.sqrt ( ( (end.first - start.first + 0.0) * (end.first - start.first + 0.0) ) + ( (end.second - start.second + 0.0) * (end.first - start.first + 0.0) ) );
        this.direction = new Pair<>(d.first / m, d.second / m);
    }

    public Pair<Integer, Integer> getStart() { return this.start; }
    public Pair<Integer, Integer> getEnd() { return this.end; }
    public Pair<Double, Double> getDirection() { return this.direction; }
}
