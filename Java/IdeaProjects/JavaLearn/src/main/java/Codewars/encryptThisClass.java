package Codewars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// variable string: StringBuilder; char list(no, ugly toString)
// iterate over String: only index
// "A": .subString(1, .length-1) xxx -> .subString(1)
// 'a' -> "97": (byte)'a' + ""/ int + string: string int!!!
// string unmodified: regex + substring + stringbuilder

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

//            StringBuilder tmpStr = new StringBuilder(str);
//            int lastIdx = tmpStr.length() - 1;
//            if (lastIdx > 1) {
//                char c = tmpStr.charAt(lastIdx);
//                tmpStr.setCharAt(lastIdx, tmpStr.charAt(1));
//                tmpStr.setCharAt(1, tmpStr.charAt(lastIdx));
//            }

            result.append((str.length() == 0) ? "" : (byte)str.charAt(0) +
                (str.length() < 3 ? str.substring(1)
                        : str.replaceFirst(".(.)(.*)(.)", "$3$2$1")))       // string char swap
                    .append(" ");
    }

//        result.deleteCharAt(result.length()-1);

        return result.toString().replaceFirst("\\s++$", "");
    }
}
