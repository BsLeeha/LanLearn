package Reflection;

public class Person {
    private String name;
    private int    age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        System.out.println("get name!");
        return name;
    }

    public int getAge() {
        return age;
    }
}
