package com.Polymorphic.employee;

public class Developer extends Employee{
    String programmingLanguage;

    public Developer(String name, double salary, String programmingLanguage) {
        super(name, salary);
        this.programmingLanguage = programmingLanguage;
    }

    public void displayInfo() {
        System.out.println("name:" + name);
        System.out.println("salary:" + salary);
        System.out.println("programmingLanguage:" + programmingLanguage);
        System.out.println();
    }
}
