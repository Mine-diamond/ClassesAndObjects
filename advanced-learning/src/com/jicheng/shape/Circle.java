package com.jicheng.shape;

import java.awt.*;

public class Circle extends shape {
    double radius;

    public Circle(double radius) {
        super(Math.PI * radius * radius);
        this.radius = radius;
    }

    public void draw() {
        System.out.println("Drawing a circle");

    }

}
