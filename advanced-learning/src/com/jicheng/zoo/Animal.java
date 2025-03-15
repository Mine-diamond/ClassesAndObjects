package com.jicheng.zoo;


public class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void eat(){
        System.out.println("Animal is eating");
    }

    public void sleep(){
        System.out.println("Animal is sleeping");
    }
}
