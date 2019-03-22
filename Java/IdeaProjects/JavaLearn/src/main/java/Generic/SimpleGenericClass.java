package Generic;

import oracle.jrockit.jfr.StringConstantPool;

/*
 * 类型参数的缺点：类型不能为基本类型，必须用其包装类
 */

/* TODO 泛型类 */
class Pair<U extends Number, V> {
    private U first;
    private V second;

    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public U getFirst() { return first; }

    public V getSecond() { return second; }

    @Override
    public String toString() { return "(" + getFirst() + ", "  + getSecond() + ")"; }

    /* TODO 泛型方法 */
    // 2. 方法是在返回值前面定义限定参数，然后在后面用，编译期会检查是否 within bound: extends Number
    public static <M extends Number, N> Pair<M, N> makePair(M first, N second) {
        return new Pair<>(first, second);
    }
}

class NumberPair<U extends Number, V extends Number> extends Pair<U, V> {
    private U first;
    private V second;

    public NumberPair(U first, V second) {
        super(first, second);                   // TODO how ???
    }

    public double sum() {
        return getFirst().doubleValue() + getSecond().doubleValue();
    }

}

/* TODO 泛型类的继承 */
class Parent<T extends Number> {
    public Parent() {

    }
}

// 子类不再泛化
class Child extends Parent<Integer> {

}

// 保持子类泛化
// 1. 在类名后面限定参数 T，在后面用，传递给父类的 T 在编译器会检查是否 within bound: extends Number
class Child2<T extends Number> extends Parent<T> {

}


/* TODO 泛型的好处 */
/* TODO Java 泛型是通过类型擦出实现的，编译器将 T 转换成 Object，并加上 cast */
/* TODO 如果限定上限，则转换成上限类型 */
// 用泛型
class InGeneric <T> {
    private T data;

    public InGeneric(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public static void example() {
        // 编译器类型判断，确保类型安全
//        InGeneric<Integer> inGeneric = new InGeneric<>("12");

        // 类型安全带来的也即是免 cast
//        Integer genericData = inGeneric.getData();
    }
}

// 不用泛型
class InObject {
    private Object data;

    public InObject(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public static void example() {
        InObject inObject = new InObject("12");

        // 1. 取值需要做 cast
        // 2. 只有在运行期才能判断抛类型转换异常
        Integer objectData = (Integer) inObject.getData();
    }
}

class GenericUtil {
    // maxium of a generic array
    // T 必须实现 Comparable 接口，并且可以和同类型对象比较
    public static <T extends Comparable<T>> T max(T[] arr) {
        T max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(max) > 0)
                max = arr[i];
        }

        return max;
    }

    public static void test() {
        System.out.println(GenericUtil.max(new Integer[]{1, 3, 4, 2}));
    }
}

public class SimpleGenericClass {
    public static void main(String[] args) {
        System.out.println(Pair.makePair(143, 13));
        System.out.println(new NumberPair<Integer, Double>(10, 30.0).sum());
        GenericUtil.test();
    }
}
