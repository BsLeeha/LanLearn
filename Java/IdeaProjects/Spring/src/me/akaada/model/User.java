package me.akaada.model;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

public class User implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean {
    private String userName;
    private String password;

    public User() {
        System.out.println("1. Initialize...");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        System.out.println("2. property populating ...");
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("3. set Bean Name..." + s);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        // put bean object into factory(container)
        System.out.println("4. set bean factory" + beanFactory);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("6. after properties set...");
    }

    public void myInit() {
        System.out.println("7. my init...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("9. bean destroy...");
    }

    public void myDestroy() {
        System.out.println("10. my bean destroy...");
    }
}
