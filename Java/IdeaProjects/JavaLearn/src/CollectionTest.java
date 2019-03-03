import java.lang.reflect.Array;
import java.util.*;

/*
 * Array: size fixed
 * String: immutable
 * Collection.List: LinkedList, ArrayList
 * Collection.Map: HashMap
 */

/*
 * lambda: anonymous function
 * closure(not support): javascript
 */

public class CollectionTest {
    public static void main(String[] args) {
        /* String */
        StringBuilder stringBuilder = new StringBuilder("Hello");
        stringBuilder.append(" World");
        String s = stringBuilder.toString();
        System.out.println(s);

        String a = "abcd";
        String b = "abcd";
        String c = new String("abcd");
        System.out.println(a == b);         // true, a and b point to the same string literals in the String pool
        System.out.println(a == c);         // false, c point to new string outside the String pool
        System.out.println(a.equals(c));    // true, compare contents


        System.out.println();

        /* List Interface */
        List<String> list = Arrays.asList("Leo", "Sue", "jack");    // fixed-size

        List<String> list1 = new ArrayList<>(list);
//        list1.add("Leo");
//        list1.add("Sue");
//        list1.add("jack");
//        list1.addAll(Arrays.asList("Leo", "Sue", "jack"));

        list.sort(null);
//        list.sort(((s1, s2) -> s1.compareToIgnoreCase(s2)));    // lambda
        list1.sort(String::compareToIgnoreCase);                // method reference

//        list.removeIf(s -> s.toLowerCase().contains("e"));       // UnsupportedOperationException: remove
        list1.removeIf(str -> str.toLowerCase().contains("e"));


        list.forEach(System.out::println);                      // method reference, instance::instance method/class::method(all)
//        list.forEach(s-> System.out.println(s));                // lambda
        System.out.println();
        list1.forEach(System.out::println);

        System.out.println();

        /* Map Interface */

        Map<String, String> map = new HashMap<>();

        map.put("RedMi", "XiaoMi");
        map.put("Honor", "Huawei");
        map.put("Galaxy", "SamSung");

        map.forEach(((k, v) -> System.out.printf("%s\t%s%n", k, v)));

        System.out.println();

        String[] keyArray = map.keySet().toArray(new String[map.keySet().size()]);
        for (String str : keyArray)
            System.out.println(str);

        System.out.println();

        LinkedList<String> keyList = new LinkedList<>(map.values());
        keyList.forEach(System.out::println);
    }
}
