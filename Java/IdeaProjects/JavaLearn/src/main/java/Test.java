import java.util.Objects;

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }
}

class Boy extends Student {
    public Boy(String name) {
        super(name);
    }
}

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        Student student = new Boy("lee");
        Class aClass = student.getClass();
        Class bClass = Class.forName("Student");
        System.out.println(bClass.getName());
    }
}

class StaticTest {
    private int a = 10;

    public static void main(String[] args) {
        new StaticTest().run();
    }

    public void run() {
        new Inner().show();
    }

    protected class Inner {
        public void show() {
            System.out.println(a);
        }
    }
}
