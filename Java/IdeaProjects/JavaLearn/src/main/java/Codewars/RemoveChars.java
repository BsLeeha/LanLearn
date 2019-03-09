package Codewars;

public class RemoveChars {
    public static String remove(String str) {
        // your code here
//        return str.substring(1, str.length()-1);

        return str.replaceAll("^.|.$", "");     // Todo how does it work?

//        return str.replaceAll(".(.*).", "$2");        // no group 2: "ab"
    }
}
