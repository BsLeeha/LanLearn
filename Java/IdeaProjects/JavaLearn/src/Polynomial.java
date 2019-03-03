
class Animal {
    public void eat() {
        System.out.println("eat");
    }
}

class Dog extends Animal {
    public void bark() {
        System.out.println("bark");
    }
}


public class Polynomial {
    public static void main(String[] args) {
        /*
         * upcast: sub class casts to super class, always allowed, done automatically, sub is a super
         * downcast: super class casts to sub class, involve type check and can throw a ClassCastException
         * when to use upcast and downcast? -> to use their methods
         * To call superclass's method, we can use super.method() inside subclass or do upcast outside subclass
         * But subclass extends all super class's method
         */

        Dog dog = new Dog();

        Animal animal = dog;                // upacst, do implicitly
        Animal animal1 = (Animal)dog;       // upcast not necessary
        Animal animal2 = new Dog();         // useful upcast

        Dog dog1 = (Dog) animal;             // downcast, ok, runtime type of animal is Dog

        Animal animal3 = new Animal();
        try {
            Dog dog2 = (Dog) animal3;           // downcast fail, runtime type of animal1 is not Dog
        } catch (ClassCastException e) {
            System.out.println("downcast fail: " + e.getMessage());
        }
    }
}
