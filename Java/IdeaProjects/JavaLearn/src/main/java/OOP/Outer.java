package OOP;

import java.util.Arrays;
import java.util.Comparator;

public class Outer {
    public static int shared = 100;

    public static class StaticInner {
        private static int a = 10;
//        public static test() {
//
//        }
        public void innerMethod() {
            System.out.println("inner " + shared);
        }
    }

    public class MemberInner {
//        private static int a = 10;
//        public static test() {
//
//        }
    }

    public void test() {
        class MethodInner {
//            private static int a = 10;
//            public static test() {
//
//            }
        }
    }

    public static void main(String[] args) {
        Arrays.sort(args, new Comparator<String>() {
//            private static int a = 10;
//            public static test() {
//
//            }

            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });
    }
}
