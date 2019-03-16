package FunctionalProgramming;

import OOP.MyComparable;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

abstract class MyPredicate<T>{
    abstract boolean test(T t) ;
}

public class Main {

    public static <T> List<T> filteredList(List<T> list, MyPredicate<T> predicate) {
        List<T> newList = new ArrayList<>();
        for (T t : list) {
            if (predicate.test(t))
                newList.add(t);
        }

        return newList;
    }


    public static void main(String[] args) {
        File path = new File(".");
        FilenameFilter filenameFilter = (dir, name) -> name.endsWith(".html");
        FileFilter fileFilter = pathname -> pathname.getName().endsWith(".html");

        Comparator<File> comparator = (f1, f2) -> f2.getName().compareTo(f1.getName());

        String[] files = path.list(filenameFilter);         // baidu.html
        File[] files1 = path.listFiles(filenameFilter);     // ./baidu.html
        File[] files2 = path.listFiles(fileFilter);         // ./baidu.html

        Arrays.sort(files1, comparator);

        System.out.println(Arrays.toString(files));
        System.out.println(Arrays.toString(files1));
        System.out.println(Arrays.toString(files2));

        /* construct ArrayList:
         * From constructor:
         *   * empty and then add
         *   * from another collection
         * From array: Arrays.asList
         *
         * Arrays.asList(arr) Arrays.toString(arr)
         */
        List<Student> students = Arrays.asList(new Student("lee", 93),
                new Student("bs", 82),
                new Student("jhon", 92));

        /*
         * we need to pass a function, but there's no function pointer in java
         * so java choose interface that embodies the function as the option,
         * user just need to make a class that implements the interface, and pass the object
         *
         * but we just need a function -> lambda expression is another redesigned tech
         *
         * List may not produce the method that has functional interface para, so we need to create a method and
         * then user can pass their costumed lambda expressions
         */
        List<Student> filteredStudents = UtilHelper.filteredList(students, t -> t.getScore() > 90);
        System.out.println(Arrays.toString(filteredStudents.toArray()));

        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<Integer> newList = filteredList(list, new MyPredicate<Integer>() {
            @Override
            boolean test(Integer integer) {
                return integer > 2;
            }
        });

        System.out.println(Arrays.toString(newList.toArray(new Integer[0])));

        System.out.println(Arrays.toString(students.stream().filter(t -> t.getScore() > 90).toArray()));

        System.out.println(Arrays.toString(students.stream().map(Student::getName).toArray()));

        System.out.println(Arrays.toString(students.stream().filter(t -> t.getScore() > 90).map(Student::getName).toArray() ));
    }


}
