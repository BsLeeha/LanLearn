public class StringOp {
    public static void main(String[] args) {
        // Substrings
        String s1 = "Hello";
        String s2 = s1.substring(0, 3); // [0, 3), 0 inclusive, 3 exclusive; length: 3-0=3
        System.out.println(s2);

        // String Concatenation
        String s3 = "Movie" + ": " +  "PG" + 13;        // every java object can be converted to a string
        String s4 = String.join("/", "S", "M", "L", "XL");  // concatenate with delimiter
        StringBuilder builder = new StringBuilder();    // use + makes new String object, it's inefficient
        builder.append("PG");
        builder.append(':');
        builder.append(13);
        String s5 = builder.toString();

        System.out.println(s3);
        System.out.println(s4);
        System.out.println(s5);

        // String == char * with automatic garbage collection, immutable, copy sharing
        String greeting = "Hello";
        greeting = greeting.substring(0, 3) + "p!"; // new tmp, strncpy
        System.out.println(greeting);               // Help!
        greeting = "Hello";                         // automatic garbage collection

        // String Equality
        // think of String as char *, so do not use
        System.out.println("Hello".equals(greeting));
        System.out.println("Hello".equalsIgnoreCase("hello"));
        System.out.println("Hello".compareTo("Hello") == 0);        // like strcmp in C

        System.out.println(greeting.length());
        System.out.println(greeting.codePointCount(0, greeting.length()));



    }
}
