package edu.study.classandobject;

public class Student {
    String name;
    int age;

    public Student(String name, int age) {
        System.out.println("edu.study.classandobject.Student Constructor");
        this.name = name;
        this.age = age;
        showInfo();
    }

    public void showInfo() {
        System.out.println("The information of the student is: ");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }

    public void sayHello(String name) {
        System.out.println(this.name);
    }

    public void method() {
        System.out.println(age);
        sayHello(name);
    }
}
