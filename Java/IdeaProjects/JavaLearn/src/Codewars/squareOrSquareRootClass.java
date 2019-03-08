package Codewars;

import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * [1] check if an integer has integer square root: check if the sqrt is integer
 * 1. Math.pow((int)Math.sqrt(x), 2) == x
 * 2. Math.sqrt(x) % 1 == 0
 * [2] iterate and modify array value
 * 1. for index read and write
 * 2. stream
 */

public class squareOrSquareRootClass {
    public static void main(String[] args) {

    }

    public static int[] squareOrSquareRoot(int[] array)
    {
//        for (int i = 0; i < array.length; i++) {
//            int squareRoot = (int)Math.sqrt(array[i]);
//            int pow = (int)Math.pow(array[i], 2);         // array[i] * array[i]
//
//            array[i] = (Math.pow(squareRoot, 2) == array[i]) ? squareRoot : pow;
//        }
//
//        return array;

        return Arrays.stream(array)
                .map(x -> Math.sqrt(x) % 1 == 0 ? (int)Math.sqrt(x) : x * x)
                .toArray();

    }
}
