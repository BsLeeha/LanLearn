import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.*;

class Person {
    String name;
    int age;
}

public class ReflectionTest {
    public static void main(String[] args) {
        Object person = new Person();

        Field[] fields = person.getClass().getDeclaredFields();

        List<String> fieldNames = new ArrayList<>();

        for (Field field : fields)
            fieldNames.add(field.toString());

        fieldNames.forEach(System.out::println);

        assert(Arrays.asList("name", "age").containsAll(fieldNames));

    }
}
