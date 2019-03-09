import java.lang.annotation.ElementType;
import java.time.LocalDate;
import java.util.Objects;

public class EmployeeTest {
    public static void main(String[] args) {
        Employee[] staff = new Employee[3];
        Manager boss1 = new Manager("john", 4000, 1987, 12, 10);
        boss1.setBonus(4000);
        Manager boss2 = new Manager("john", 4000, 1987, 12, 10);
        boss1.setBonus(5000);


        staff[0] = boss1;
        staff[1] = new Employee("Sue", 3000.1, 1988, 3, 2);
        staff[2] = new Employee("Bob", 3000.1, 1988, 4, 14);

        //staff[0].setBonus(4000);      // only when base and subclass have same methods, can polymorphism happen

        for (Employee e : staff)
            e.raiseSalary(5);

        for (Employee e : staff) {
            System.out.println("name: " + e.getName() + ", id: " + e.getId() + ", salary: " + e.getSalary() + ", hireDay: " + e.getHireDay());
        }

        /* the cosmic superclass: Object */
        // use Object to refer to objects of any type, like void * in C/C++, use in function call? as generic? just placeholder
        Object obj ;
        obj = new Employee("lee", 3000, 1999, 10, 1);
        // do a cast for specific usage
        Employee e = (Employee)obj;
        // all array types are class types that extend the Object class
        obj = new Employee[10];
        obj = new int[10];

//        Employee e1 = new Employee("lee", 3000, 1999, 10, 1);
//        Employee e2 = new Employee("lee", 3000, 1999, 10, 1);
//        Object obj1 = e2;
//        System.out.println(e1.equals(obj1));        // result: choose parent
//        System.out.println(obj1.equals(obj1));      // result: choose parent
//        System.out.println(obj1.equals(e1));        // result: choose parent
//        System.out.println(e1.equals(e1));          // result: choose child

        System.out.println(staff[1].compareTo(staff[2]));
    }
}
