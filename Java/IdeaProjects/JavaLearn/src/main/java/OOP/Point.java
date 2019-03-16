package OOP;

public class Point implements MyComparable<Point>{
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance() {
        return Math.sqrt(x*x + y*y);
    }

    @Override
    public int compareTo(Point otherPoint) {
//        if (!(other instanceof Point))
//            throw new IllegalArgumentException();

//        Point otherPoint = (Point)other;
        double delta = distance() - otherPoint.distance();

        if (delta < 0) return -1;
        else if (delta == 0) return 0;
        else return 1;
    }

    public String toString() {
        return "(" + x + ", " + y +")";
    }
}
