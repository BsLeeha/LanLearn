import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayOp {

    public static void main(String[] args) {
        arraySort();
    }

    public static void box() {
        /*---------- array create -----------------*/
        // array in java is also object, reference type, so need to new
        // int[] arr = new int[]{1, 2, 3, 4};       // actually create two references(arr and anonymous)
        int[] arr = {1, 2, 3, 4};

        /*-------------- Todo why bother to convert int[] to Integer[] -------------------*/
        // To boxed array
        // array stream -> boxed array stream -> boxed array
        Integer[] brr = Arrays.stream(arr).boxed().toArray(Integer[]::new);
//        Integer[] crr = Arrays.stream(arr).boxed().toArray(new Integer[0]);   // Todo wrong
        // array stream -> boxed array stream -> boxed array
        Integer[] drr = IntStream.of(arr).boxed().toArray(Integer[]::new);

        // To boxed list
        List<Integer> lista = Arrays.stream(arr).boxed().collect(Collectors.toList());
        List<Integer> listb = IntStream.of(arr).boxed().collect(Collectors.toList());
    }

    public static void arraySort() {
        int[] arr = {1, 3, 4, 2, 4};

        /*----- full sum: use stream and call sum or reduce ------*/
        int sum = IntStream.of(arr).sum();
        int sum1 = Arrays.stream(arr).sum();
        // identity: initial value
        int sum2 = Arrays.stream(arr).reduce(0, (a, b) -> (a + b));
        System.out.println(sum + " " + (sum1 == sum2 && sum == sum1));

        int sum3 = IntStream.range(0, 3).sum();
        System.out.println(sum3);
    }
}
