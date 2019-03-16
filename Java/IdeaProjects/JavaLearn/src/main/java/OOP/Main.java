package OOP;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Point[] points = {new Point(2, 3), new Point(3, 4), new Point(1, 2)};
        System.out.println("max: " + CompUtil.max(points));
        CompUtil.sort(points);
        System.out.println("sort: " + Arrays.toString(points));
    }
}
