package Generic;

import java.util.Arrays;
import java.util.Random;

public class DynamicArray<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;

    private E[] elem;

    private E obj;

    public DynamicArray() {
        data = new Object[DEFAULT_CAPACITY];
//        elem = new E[10];

//        obj = new E();
        size = 0;
    }

    private void ensureCapacity(int minCapacity) {
        int oldCapacity = data.length, newCapacity = oldCapacity * 2;

        if (oldCapacity > minCapacity) return ;

        newCapacity = (minCapacity > newCapacity ? minCapacity : newCapacity);

        data = Arrays.copyOf(data, newCapacity);
    }

    public int size() {
        return size;
    }

    public void add(E e) {
        ensureCapacity(size + 1);
        data[size++] = e;
    }

    public E get(int i) {
        return (E)data[i];
    }

    public E set(int i, E elem) {
        E oldValue = get(i);
        data[i] = elem;
        return oldValue;
    }


    public static void main(String[] args) {
        DynamicArray<Double> array = new DynamicArray<>();
        Random random = new Random();
        int size = 1 + random.nextInt(30);
        for (int i = 0; i < size; i++)
            array.add(Math.random()*10);
        for (int i = 0; i < size; i++)
            System.out.printf("%f ", array.get(i));
        System.out.println();
    }
}
