package me.akaada.test;

import me.akaada.model.Student;
import me.akaada.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConstructorInject {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("constructorInject.xml");

        Student student = (Student) context.getBean("student");

        System.out.println(student);


    }
}
