package Generic;

import java.util.Arrays;

public class DynamicArray<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] data;
    private int size;           // size 是实际使用的元素个数
    private int capacity;       // capacity 就是数组分配的空间大小，就是 length

    public DynamicArray() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
        capacity = DEFAULT_CAPACITY;
    }

    // 在每次 add 之前调用，实现动态扩容，其实就是数组复制到大数组，用类实现隐藏而已
    private void ensureCapacity(int minCapacity) {
        if (capacity < minCapacity) {
            // 扩两倍或者更多
            capacity = (minCapacity > 2*capacity ? minCapacity : 2*capacity);
            data = Arrays.copyOf(data, capacity);
        }
    }

    public void add(T elem) {
        ensureCapacity(size + 1);
        data[size++] = elem;
    }

    // 父类是不能强转为子类的，不然会抛类型转换的异常
    // 除非你本来就是这种类型，只不过通过多态封装了一下，这里就是这样
    // 类型擦除后，就是 Object data = new Integer(3);
    // Integer elem = (Integer)data.get (0);
    @SuppressWarnings("unchecked")
    public T get(int index) { return (T) data[index]; }

    public int getSize() { return size; }

    public int getCapacity() { return capacity; }

    public void addAll(DynamicArray<? extends T> arr) {
        for (int i = 0; i < arr.getSize(); i++) {
            add(arr.get(i));
        }
    }

    @Override
    public String toString() {
        return Arrays.asList(data).toString();
    }

    public static void main(String[] args) {
        DynamicArray<Integer> arr = new DynamicArray<>();
        for (int i = 0; i < 10; i++) arr.add(i);

        DynamicArray<Number> brr = new DynamicArray<>();
        brr.addAll(arr);
        System.out.println(brr);
    }
}
