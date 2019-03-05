package Codewars;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/*
 * Enum equals override? array don't.
 */

public class MysteryColorAnalyzerImpl implements MysteryColorAnalyzer {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3};
        int[] brr = new int[]{1, 2, 3};
        int[] crr = arr;
        int[][] arrs = new int[][]{arr, brr, crr};
        // all equals by ==, array don't override
        System.out.println(Arrays.stream(arrs).distinct().count());         // 2
        System.out.println(Arrays.stream(arrs).filter(new int[]{1, 2, 3}::equals).count()); // 0
        System.out.println(Arrays.stream(arrs).filter(arr::equals).count());    // 2
    }

    @Override
    public int numberOfDistinctColors(List<Color> mysteryColors) {
//        return (int) mysteryColors.stream().distinct().count();
        return new HashSet<>(mysteryColors).size();
    }

    @Override
    public int colorOccurrence(List<Color> mysteryColors, Color color) {
//        return (int) mysteryColors.stream().filter(color::equals).count();
//        return (int) mysteryColors.stream().filter(c -> c == color).count();
//        return (int) mysteryColors.stream().filter(c -> c.equals(color)).count();
        return Collections.frequency(mysteryColors, color);
    }
}
