package com.Polymorphic.employee;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Manager("Bob",4500,2000));
        employees.add(new Manager("Alice",4500,2000));
        employees.add(new Developer("Peter",1200,"Java"));
        employees.add(new Developer("July",1500,"C++"));

        for (Employee employee : employees) {
            employee.displayInfo();
        }
    }
}
