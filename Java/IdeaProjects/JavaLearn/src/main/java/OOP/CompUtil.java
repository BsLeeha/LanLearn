package OOP;

public class CompUtil {
    public static Object max(MyComparable[] objs) {
        if (objs == null || objs.length == 0)
            return null;

        MyComparable max = objs[0];

        for (int i = 1; i < objs.length; i++) {
            if (max.compareTo(objs[i]) < 0)
                max = objs[i];
        }

        return max;
    }

    public static void sort(MyComparable[] objects) {
        for (int i = 0; i < objects.length; i++) {
            int min = i;
            for (int j = i + 1; j < objects.length; j++) {
                if (objects[i].compareTo(objects[j]) > 0)
                    min = j;
            }

            if (min != i) {
                MyComparable temp = objects[i];
                objects[i] = objects[min];
                objects[min] = temp;
            }
        }
    }
}