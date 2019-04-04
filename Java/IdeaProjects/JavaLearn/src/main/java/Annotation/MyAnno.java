package Annotation;

/**
 * 注解的本质：继承自 Annotation 的接口
 * 对 MyAnno -> javac -> javap 反编译，有：
 * public interface Annotation.MyAnno extends java.lang.annotation.Annotation {}
 */
public @interface MyAnno {
    int age();

    String name();
}
