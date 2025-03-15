package com.jiekou.learn1;

interface shape {
    int defaultLong = 1;
    double getArea();
    double getPerimeter();
    public abstract void draw();
}
class circle implements shape {
    double radius;
    public circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return radius * radius * Math.PI;
    }

    @Override
    public double getPerimeter() {
        return radius * 2 * Math.PI;
    }

    @Override
    public void draw() {
        System.out.println("circle");
    }
}

class Rectangle implements shape {
    double height;
    double width;

    public Rectangle(double width, double height) {
        this.height = height;
        this.width = width;
    }

    @Override
    public double getArea() {
        return height * width;
    }

    @Override
    public double getPerimeter() {
        return 2 * (height + width);
    }

    @Override
    public void draw() {
        System.out.println("Rectangle");
    }
}

class ff{
    public static void ffDraw(shape shape){
        shape.draw();
        System.out.println(shape.getArea());
    }
}

class main{
    public static void main(String[] args) {
        ff.ffDraw(new circle(5));
    }
}

interface pick extends shape{

}