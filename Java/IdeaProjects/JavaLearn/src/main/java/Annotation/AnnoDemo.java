package Annotation;

/**
 * 注解 javadoc 演示
 * 注解的三种使用：
 *  1. javadoc 生成帮助文档
 *  2. 用反射进行代码分析
 *  3. 编译检查，如 override
 * 为避免乱码问题，加上参数 -encoding UTF-8
 *
 * @author BsLee
 * @version 1.0
 * @since 1.5
 */
public class AnnoDemo {
    /**
     * 计算两数的和
     * @param a 整数
     * @param b 整数
     * @return 两数的和
     */
    public int add(int a, int b) {
        return a + b;
    }
}
