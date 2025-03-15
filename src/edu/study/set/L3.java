package edu.study.set;

import java.util.ArrayList;

public class L3 {
    public static void main(String[] args) {
        ArrayList<Coder> coders = new ArrayList<Coder>();
        ArrayList<Manager> managers = new ArrayList<Manager>();
        coders.add(new Coder());
        coders.add(new Coder());
        managers.add(new Manager());
        managers.add(new Manager());
    }
    
    public static void ListWork(ArrayList<? extends Employee> employees){
        employees.forEach(employee -> employee.work());
    }
}

interface Employee {
    public void work();
}

class Coder implements Employee {
    public void work(){
        System.out.println("coder work");
    }
}

class Manager implements Employee {
    public void work(){
        System.out.println("manager work");
    }
}