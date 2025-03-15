package com.jicheng.zoo;

public class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    public void meow(){
        System.out.println("Cat is meowing");
    }

    public void eat(){
        System.out.println("Cat is eating fish");
    }
}
