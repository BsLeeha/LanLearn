public class ExceptionOp {
    public static void main(String[] args) {
        int x = 10, y = 0, z;
//        try {
            System.out.println(z = x / y);      // who throws the exception object: JVM ? maybe, it defines the / operator
//        }
//        catch (ArithmeticException e) {         // don't remember the exception? try it's ancestor: Throwable
//            System.err.println("Error occurred: " + e.getMessage());
//        }
    }
}
