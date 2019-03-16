package OOP;

public class Pair<U, V> {
    private U first;
    private V second;

    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first +", " + second + ")";
    }

    public static void main(String[] args) {
        Pair<String, String> pair = new Pair<>("bs", "lee");
        System.out.println(pair);

        Pair<Integer, Integer> pair1 = new Pair<>(1, 2);
        System.out.println(pair1);
    }
}
