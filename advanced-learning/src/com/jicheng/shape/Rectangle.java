package com.jicheng.shape;

/*
Rectangle：
添加属性 private double length（长）和 private double width（宽）。
创建构造函数 Rectangle(double length, double width)，初始化长和宽，并计算面积 area = length * width。
重写 draw() 方法，输出 "Drawing a rectangle"。
* */


import java.awt.*;

public class Rectangle extends shape {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        super(length * width);
    }

    public void draw() {
        System.out.println("Drawing a rectangle");
    }
}
