package com.item.test;


public class Test {
    public static void main(String[] args) {
        Item apple = new Apple();
        apple.accept(new Visitor() {
            @Override
            public void visit(Item item) {

            }

            @Override
            public void visit(Apple apple) {
                System.out.println(apple);
                apple.eat();
            }
        });
    }
}
