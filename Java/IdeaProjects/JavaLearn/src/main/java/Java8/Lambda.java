package Java8;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class InRunnable {
    public static void run() {
        // before using lambda...
        new Thread(new Runnable() {
            @Override
            public void run() {
                // task...
                System.out.println("Before using lambda in Runnable...");
            }
        }).start();

        // replace Runnable with lambda...
        new Thread(() -> {
            // task...
            System.out.println("Replace Runnable with lambda...");
        }).start();
    }
}

class InEventListen {
    public static void run() {
        JButton button = new JButton();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // event handling...
            }
        });

        button.addActionListener(e -> {
            // event handling
        });
    }
}

class InList {
    public static void run() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<String>  list1 = Arrays.asList("a", "ab", "abc", "abcd");

        // list map
        List<Integer> mappedList = list.stream().map(x -> x * 2).collect(Collectors.toList());

        // list iterate
        mappedList.forEach(System.out::println);

        // list reduce aggreate
        int sum = list.stream().reduce(Integer::sum).get();
        System.out.println(sum);

        // list multiple filter with Predicate
        filter(list, x -> ((int)x % 2 == 0));
        filter(list1, x -> ((String)x).length() > 2);
    }

    public static void filter(List list, Predicate condition) {
        list.forEach( x -> {
            if (condition.test(x)) {
                System.out.println(x);;
            }
        });
    }
}

public class Lambda {
    public static void main(String[] args) {
        InRunnable.run();
    }
}
