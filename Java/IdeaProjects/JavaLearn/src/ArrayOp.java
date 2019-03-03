import java.util.Arrays;
import java.util.Scanner;

public class ArrayOp {


    public static void main(String[] args) {
        int n;
        System.out.println("Specify the length of the array: ");
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();

        int[] arr = new int[n];

        for (int i = 0; i < arr.length; ++i)
            arr[i] = i;

        for (int elem : arr)
            System.out.print(elem + " ");
        System.out.println();

        System.out.println(Arrays.toString(arr));

        int arr1[] = {3, 2, 1, 4, 5, 6};
        System.out.println(Arrays.toString(arr1));

        //arr1 = {6, 5, 3, 2}; // wrong
        arr1 = new int[]{6, 5, 3, 2};
        System.out.println(Arrays.toString(arr1));

        int arr2[] = arr1;      // same array
        System.out.println(Arrays.toString(arr2));

        int arr3[] = Arrays.copyOf(arr1, arr1.length - 3);
        System.out.println(Arrays.toString(arr3));

        // command-line array
        if (args.length == 0 || args[0].equals("-h"))
            System.out.println("Hello,");
        else if (args[0].equals("-g"))
            System.out.println("Goodbye");
        for (int i = 1; i < args.length; ++i)
            System.out.println(args[i]);

        System.out.println((int) (Math.random() * 30));

        int[][] multiArr = {
                {1, 2, 3,},
                {4, 5, 6}
        };

        for (int[] row : multiArr) {
            for (int val : row)
                System.out.print(val + " ");
            System.out.println();
        }

        System.out.println(Arrays.deepToString(multiArr));
    }
}
