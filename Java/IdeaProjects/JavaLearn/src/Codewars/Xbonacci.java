package Codewars;

import java.util.Arrays;

public class Xbonacci {
    public static void main(String[] args) {

        // array is a reference type, is an Object
        // array empty: .length == 0
        // array copy:
        // 1. full array copy: Object.clone
        // 2. copy to existed array: System.arraycopy
        // 3. copy to new array: Arrays.copyOf/Arrays.copyOfRange
        // empty array: new int[]{}; new int[0];

        System.out.println(Arrays.toString(new Xbonacci().tribonacci(new double[]{1, 1, 1}, 10)));
        System.out.println(new Xbonacci().tribonacci(new double[]{1, 2, 3}, 0).length == 0);
    }

    public double[] tribonacci(double[] s, int n) {
//        if (n < 3) return Arrays.copyOf(s, n);
//
//        double[] result = new double[n];
//        System.arraycopy(s, 0, result, 0, s.length);
//
//        for (int i = 0, j = 1, k = 2; k < n-1; i++, j++, k++) {
//            result[k+1] = result[i] + result[j] + result[k];
//        }

        double[] result = Arrays.copyOf(s, n);

        for (int i = 3; i < n; i++)
            result[i] = result[i-1] + result[i-2] + result[i-3];

        return result;
    }
}
