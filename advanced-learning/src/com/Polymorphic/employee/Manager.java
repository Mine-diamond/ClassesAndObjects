package com.Polymorphic.employee;

public class Manager extends Employee{

    double bouns;

    public Manager(String name, double salary, double bouns) {
        super(name, salary);
        this.bouns = bouns;
    }

    public void displayInfo(){
        System.out.println("Name: " + name);
        System.out.println("Salary: " + salary);
        System.out.println("Bouns: " + bouns);
        System.out.println();
    }
}
