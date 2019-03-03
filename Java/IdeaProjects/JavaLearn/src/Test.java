import java.util.Objects;

class A {
    public boolean equals(A a) {
        System.out.println("A");
        return false;
    }

    public boolean equals(Object o) {
        System.out.println("Object");
        return false;
    }
}

public class Test {
    public static void main(String[] args) {
        A a = new A();
        Object o = a;
        o.equals(o);
        a.equals(o);
        o.equals(a);
        a.equals(a);

    }
}
