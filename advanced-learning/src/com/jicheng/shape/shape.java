package com.jicheng.shape;

public class shape {
    protected double area;

    public shape(double area) {
        this.area = area;
    }

    public void draw(){
        System.out.println("Drawing a shape");
    }

    public double getArea() {
        return area;
    }
}
