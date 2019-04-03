package Reflection;

import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Class personClass = Person.class;

        Person person = new Person("lee", 23);

        // get the value of the specified public field of an object
        int high = (int) personClass.getField("high").get(new Person("lee", 23));
        System.out.println(high);

        // set the value of the specified public field of an object
        personClass.getField("high").set(person, 22);
        System.out.println(person);

        // get private field
        Field ageFiled = personClass.getDeclaredField("age");

        ageFiled.setAccessible(true);

        int age = (int) ageFiled.get(person);
        System.out.println(age);
    }
}
