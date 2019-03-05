package Codewars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 *  Arrays.asList returns a new List<>
 */

//public class Ghost {
//    private Random random;
//    private List<String> colors;
//
//    public Ghost() {
//        random = new Random();
//        // no need to copy again
////        colors = new ArrayList<String>(Arrays.asList("white", "yellow", "purple", "red"));
//        colors = Arrays.asList("white", "yellow", "purple", "red");
//    }
//
//    public String getColor() {
//        int idx = random.nextInt(colors.size());
//        return colors.get(idx);
//    }
//}

public class Ghost {
    private final String[] colors = new String[]{"white", "yellow", "purple", "red"};
    private String color;

    public Ghost() {
        color = colors[new Random().nextInt(colors.length)];
    }

    public String getColor() {
        return color;
    }

}


