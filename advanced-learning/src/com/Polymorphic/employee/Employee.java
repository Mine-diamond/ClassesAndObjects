package com.Polymorphic.employee;

public class Employee {
    String name;
    double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public void displayInfo(){
        System.out.println("name:" + name);
        System.out.println("salary:" + salary);
        System.out.println();
    }
}
