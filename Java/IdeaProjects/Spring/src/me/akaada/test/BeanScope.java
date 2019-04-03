package me.akaada.test;

import me.akaada.service.IUserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanScope {

    public static void main(String[] args) {
        // 1. create Spring container with ApplicationContext
        ApplicationContext context = new ClassPathXmlApplicationContext("beans2.xml");

        // 2. get bean object from the container
        IUserService userService = (IUserService) context.getBean("userService");
        IUserService userService1 = (IUserService) context.getBean("userService");

        System.out.println(userService);
        System.out.println(userService1);

    }

}
