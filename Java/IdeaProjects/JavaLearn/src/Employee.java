import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;
import java.util.stream.StreamSupport;

public class Employee implements Comparable{
    private final String name;      // final: must be initialized, must not change
    private final StringBuilder evaluations;    // final on mutable object, the reference cannot change, but the content can change
    private double salary;
    private LocalDate hireDay;      // immutable filed, while Date mutable

    private static int nextId = 1;
    private int id = assignId();

    static {
        Random generator = new Random();
        nextId = generator.nextInt(10000);
    }

    private static int assignId() {
        int tmp = nextId;
        nextId++;
        return tmp;
    }

    // constructor always called with new, all java Objects are constructed on the heap
    public Employee(String n, double s, int year, int month, int day) {
        name = n;
        evaluations = new StringBuilder();
        salary = s;
        hireDay = LocalDate.of(year, month, day);
    }

    // String immutable, return reference ok
    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    // when return reference, return its copy or use immutable substitution
    // LocalDate immutable, return reference ok
    public LocalDate getHireDay() {
        return hireDay;

        // for mutable Date, return clone other than the original reference
        // return (Date)hireDay.clone();
    }

    public void raiseSalary(double byPercent) {
        salary += salary * byPercent / 100;
    }

//    public boolean equals(Employee other) {
//        return name.equals(other.name);     // A method can access the private features of any object of its class, as in C++
//    }

    public void giveGoldStar() {
        evaluations.append(LocalDate.now() + ": Gold Star\n");
    }

    public void setId() {
        id = nextId;
        nextId++;
    }

    public int getId() {
        return id;
    }

    public static int getNextId() {
        return nextId;
    }




    // Employee implicitly extends Object, so cannot overload, must override
    // override: rewrite parent method
    // overload: multiple in class methods with same names
    public boolean equals(Employee otherObject) {
        System.out.println("Choose child");

        // reflexive test: fanshen
        if (this == otherObject) return true;

        // null test
        if(otherObject == null) return false;

        // make sure the same actual type
        if(getClass() != otherObject.getClass()) return false;

        // do the detailed comparison
        Employee other = (Employee)otherObject;
//        return name.equals(other.name) &&
//                salary == other.salary &&
//                hireDay.equals(other.salary);

        // check name and hireDay objects null for object existence for invalid method call
        // primitive types are not objects, no method call, safe
        // both the fields will be auto initialized
        return Objects.equals(name, other.name) &&
                salary == other.salary &&
                Objects.equals(hireDay, other.hireDay);
    }

    // override
    public boolean equals(Object otherObject) {
        System.out.println("Choose parent");
        return super.equals(otherObject);
    }


    public int compareTo(Object otherObject) {
        return Double.compare(salary, ((Employee)otherObject).salary);
    }

}