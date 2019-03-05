package Codewars;

public class God {
    public static Human[] create(){
        //code
        // 返回一个数组对象，里面有两个数组对象，都 new
        return new Human[]{new Man(), new Woman()};
    }
}
// code
class Human {}

class Man extends Human {}

class Woman extends Human {}