package Container;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/*
 * Use interfaces instead of an actual implementation:
 * 1. ArrayList<Object> list = new ArrayList<Object>(); // ArrayList -> LinkedList, hard to change when ArrayList's specific methods used
 * 2. List<Object> list1 = new ArrayList<Object>();     // ArrayList -> Linkedlist, easy to change
 * 3. void foo(List<Object> list);                      // placeholder
 */

class ArrayListTest {

    static void printCollection(List<Object> c) {
        for (Object object : c)
            System.out.println(object);
    }

    public static void run() {
        List<Object> list = new Stack<Object>();                    // implicit upcast
        list.add("abcd");
        list.add("abcd");
        list.add("abcd");
        System.out.println(list);

        Iterator iter = list.iterator();
//        iter.remove();                      // iter does not point to the first elem as C++
        iter.next();
        iter.remove();

        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            String str = (String)iterator.next();       // explicitly downcast: Object -> String
            System.out.println(str);
        }

        printCollection(list);


//        List<Integer> list = IntStream.range(0, 10)
//                .boxed()
//                .collect(Collectors.toList());
//
//        ListIterator<Integer> it = list.listIterator(list.size());
//
//        List<Integer> result = new ArrayList<>(list.size());
//
//        while (it.hasPrevious())
//            result.add(it.previous());
//
//        list.forEach(x -> System.out.printf("%d ", x));
//        System.out.println();
//
//        result.forEach(x -> System.out.printf("%d ", x));
//        System.out.println();
//
//        List<String> list1 = LongStream.range(0, 16)
//                .boxed()
//                .map(Long::toHexString)
//                .collect(Collectors.toCollection(ArrayList::new));

    }
}

public class ListTest {
    public static void main(String[] args) {
        ArrayListTest.run();
    }
}
