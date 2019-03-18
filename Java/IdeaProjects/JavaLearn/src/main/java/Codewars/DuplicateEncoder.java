package Codewars;

import java.util.Arrays;
import java.util.stream.IntStream;

public class DuplicateEncoder {
    static String encode(String word){
        StringBuilder sb = new StringBuilder();

        String str = word.toLowerCase();

        str.chars().forEach(System.out::println);

        IntStream.range(0, word.length())
                 .forEach(i -> sb.append(str.lastIndexOf(str.charAt(i)) == str.indexOf(str.charAt(i)) ? '(' : ')' ));

//        for (int i = 0; i < word.length(); i++){
//            final int j = i;
//            sb.append(str.chars().filter(x -> x == str.charAt(j)).count() > 1 ? ')' : '(');
//        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(encode("Prespecialized"));
    }
}