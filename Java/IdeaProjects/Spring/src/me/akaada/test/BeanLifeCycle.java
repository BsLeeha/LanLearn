package me.akaada.test;

import me.akaada.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;

public class BeanLifeCycle {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ApplicationContext context = new ClassPathXmlApplicationContext("beanLC.xml");

        User user = (User) context.getBean("beanLC");

        // call context.close()
        context.getClass().getMethod("close").invoke(context);
    }
}
