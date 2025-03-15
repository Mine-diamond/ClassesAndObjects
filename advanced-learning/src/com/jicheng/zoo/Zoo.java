package com.jicheng.zoo;

import java.util.ArrayList;

public class Zoo {
    public static ArrayList<Animal> animals = new ArrayList<>();

    public static void addAnimal() {
        Cat c1 = new Cat("n1");
        animals.add(c1);
        Dog d1 = new Dog("n2");
        animals.add(d1);
    }

    public static void showAnimals() {
        for (Animal a : animals) {
            a.eat();
            a.sleep();
            if (a instanceof Cat){
                ((Cat) a).meow();
            }else if (a instanceof Dog){
                ((Dog) a).bark();
            }else {
                System.out.println("Wrong animal type");
            }
        }
    }

    public static void main(String[] args) {
        addAnimal();
        showAnimals();
    }
}
