package Reflection;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream in = classLoader.getResourceAsStream("pro.properties");
        properties.load(in);

        String className = properties.getProperty("className");
        String methodName = properties.getProperty("methodName");

        Class aClass = Class.forName(className);
        Object obj = aClass.newInstance();
        Method method = aClass.getMethod(methodName);
        method.invoke(obj);
    }
}
