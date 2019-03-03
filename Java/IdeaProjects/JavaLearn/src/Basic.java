
/* java compile */
// .cpp -> (compile, link) .exe -> execute(OS)
// .java -> (compile).class(java byte code) -> interpret and execute(interpreter) -> OS

/* enum test */
// enum can be inside a class, or define alone(act as a class?, all should be class), method, var, constructor
// can be defined inside
class FreshJuice {
    enum FreshJuiceSize{SMALL, MEDIUM, LARGE}
    FreshJuiceSize size;
}

public class Basic {
    public static void main(String[] args) {
        FreshJuice juice = new FreshJuice();
        juice.size = FreshJuice.FreshJuiceSize.MEDIUM;

        float a = 10.1f;
    }
}
