package com.jicheng.zoo;

public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    public void bark(){
        System.out.println("Dog is barking");
    }

    public void eat(){
        System.out.println("Dog is eating bones");
    }
}
