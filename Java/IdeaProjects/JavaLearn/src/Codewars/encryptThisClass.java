package Codewars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// variable string: StringBuilder; char list(no, ugly toString)
// iterate over String: only index

public class encryptThisClass {
    public static void main(String[] args) {
        System.out.println(encryptThis("A bc hello   world  "));

        // ugly char list toString
        List<Character> list = Arrays.asList('a', 'b', 'c', 'd');
        System.out.println(list);
    }

    public static String encryptThis(String text) {
        StringBuilder result = new StringBuilder();

        for (String str : text.split(" ")) {

//            System.out.println(str);

//            if (str.length() == 0) {
//                result.append(" ");
//                continue;
//            }
//
////            String ascii = Integer.toString((int)str.charAt(0));
////            for (int i = 0; i < ascii.length(); i++)
////                result.append(ascii.charAt(i));
//
//            result.append((byte)str.charAt(0));
//
////            int len = str.length();
////            for (int i = 1; i < len; i++) {
////                if (i == 1) result.append(str.charAt(len-1));
////                else if (i == len-1) result.append(str.charAt(1));
////                else result.append(str.charAt(i));
////            }
//
//            result.append(str.replaceFirst("(.)(.)", "$2$1"));
//
//            result.append(" ");

            result.append((str.length() == 0) ? "" : (byte)str.charAt(0) + str.replaceFirst(".(?)(.*)(?)", "$3$2$1"))
                    .append(" ");
        }

//        result.deleteCharAt(result.length()-1);

        return result.toString().replaceFirst("\\s++$", "");
    }
}
