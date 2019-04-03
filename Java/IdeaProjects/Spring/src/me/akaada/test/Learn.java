package me.akaada.test;

import me.akaada.service.IUserService;
import me.akaada.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

public class Learn {

    @Test
    public void testUser() {
//        IUserService userService = new UserServiceImpl();
//        userService.add();

        /*
          first way to create Spring: loading xml bean definitions from class path resource
          ClassPath: classes path, src will be wrapped into classes, see tomcat webapp
         */
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        IUserService userService = (IUserService) context.getBean("userService");
        userService.add();

        IUserService userService1 = (IUserService) context.getBean("userService");
        userService1.add();

        // same object
        System.out.println(userService);
        System.out.println(userService1);

        /*
        second way to create Spring: Loading XML bean definitions from file
        right click the xml file and copy path
        */
        String path = "D:\\LanLearn\\Java\\IdeaProjects\\Spring\\src\\beans.xml";

        ApplicationContext context1 = new FileSystemXmlApplicationContext(path);

        IUserService userService2 = (IUserService) context1.getBean("userService");
        userService2.add();

        /*
            third way: use BeanFactory: deprecated
         */
        BeanFactory beanFactory = new XmlBeanFactory(new FileSystemResource(path));

        IUserService userService3 = (IUserService) beanFactory.getBean("userService");
        userService3.add();


        /*
         * Spirng 容器: BeanFactory 和 ApplicationContext
         * BF: 延迟加载，第一个调用 getBean 时才创建对象
         * AC: 即时加载，并对 BF 扩展了功能：
         *      国际化处理；
         *      事件传递；
         *      Bean 自动装配；
         *      各种不同应用层的 Context 实现；
        */

        IUserService userService4 = (IUserService) context.getBean("userService1");
        userService4.add();

    }
}
