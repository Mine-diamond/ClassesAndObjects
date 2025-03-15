package com.jicheng.shape;

/*
创建一个主类 `ShapeManager`，包含一个 `Shape` 类型的数组 `shapes`，用来存储不同类型的图形对象（`Circle` 和 `Rectangle`）。
   - 在 `ShapeManager` 中定义方法 `addShape(Shape shape)` 用于添加图形对象。
   - 定义方法 `displayShapes()` 用于遍历 `shapes` 数组，调用每个图形的 `draw()` 方法，并输出其面积（使用 `getArea()`）。
*/

import java.awt.*;
import java.util.ArrayList;

public class ShapeManager {

    public static void main(String[] args) {
        Circle c1 = new Circle(5);
        Circle c2 = new Circle(7);
        Rectangle r1 = new Rectangle(5, 7);
        addShape(c1);
        addShape(c2);
        addShape(r1);
        displayShape();
    }



    public static ArrayList<shape> shapes =  new ArrayList<>();

    public static void addShape(shape shape) {
        shapes.add(shape);
    }

    public static void displayShape() {
        for (shape shape : shapes) {

            if (shape instanceof Rectangle) {
                System.out.println("Rectangle");
            }else if (shape instanceof Circle) {
                System.out.println("Circle");
            }else {
                System.out.println("Wrong shape");
            }

            shape.draw();
            System.out.println(shape.getArea());

        }
    }

}
