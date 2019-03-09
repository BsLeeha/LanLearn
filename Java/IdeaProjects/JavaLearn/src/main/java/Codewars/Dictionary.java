package Codewars;

import java.util.HashMap;
import java.util.Map;

/*
 * 实现一个类似的 map 结构，包含一些定制的 put, get 方法
 * 1. 存储 map 对象，对其操作
 * 2. 继承 hashMap，override 方法
 */

//public class Dictionary {
//    Map<String, String> map = new HashMap<>();
//
//    public Dictionary() {}
//
//    public void newEntry(String key, String value) {
//        map.put(key, value);
//    }
//
//    public String look(String key) {
////        if (!map.containsKey(key))
////            return "Cant find entry for " + key;
////        else
////            return map.get(key);
//        return map.getOrDefault(key, "Cant find entry for " + key);
//    }
//}

public class Dictionary extends HashMap<String, String> {
    public Dictionary() { super(); }

    // 不必指定 this，继承的方法算是在同一个类中，那不管是调用类方法，实例方法，类字段，实例字段，都不必指定 this
    // 隐式调用 this
    // 只有再传参同名时才有必要用 this
    // 而静态方法没有 this，所以只能先 new 才能调用其它实例方法
    public void newEntry(String key, String value) { this.put(key, value); }

    public String look(String key) { return this.getOrDefault(key, "Cant find entry for " + key); }
}
