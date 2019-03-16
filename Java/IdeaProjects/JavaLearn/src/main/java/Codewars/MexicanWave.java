package Codewars;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO: stream version

public class MexicanWave {

    public static String[] wave(String str) {
//        List<String> result = new ArrayList<>();
//
//        for (int i = 0; i < str.length(); i++) {
//            char ch = Character.toUpperCase(str.charAt(i));
//            if (ch == ' ') continue;
//            result.add(str.substring(0, i) + ch + str.substring(i+1, str.length()));
//        }
//
//        return result.toArray(new String[0]);

        return IntStream.range(0, str.length())
                .mapToObj(i -> new StringBuilder(str).replace(i, i+1, ""+Character.toUpperCase(str.charAt(i))).toString())
                .filter(x -> !x.equals(str))
                .toArray(String[]::new);
    }
}