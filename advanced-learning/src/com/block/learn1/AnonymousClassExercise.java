package com.block.learn1;

// 定义一个接口 Greeting
@FunctionalInterface
interface Greeting {
    void greet();  // 接口方法
}

public class AnonymousClassExercise {



    public static void main(String[] args) {
        // 通过匿名内部类实现 Greeting 接口并创建实例
        Greeting greeting = () -> {System.out.println("热烈欢迎！");};

    // 调用 greet() 方法
        greeting.greet();
}
}
