package com.impl.test;

public class Apple extends Item<Apple> {
    String name = "苹果";
    String description = "恢复八点饱食度";
    FoodEffect foodEffect = new FoodEffect(8);

    @Override
    public Apple getThis() {
        return new Apple();
    }

    public void print() {
        System.out.println(name);
        System.out.println(description);
        System.out.println(foodEffect);
    }

    public static void main(String[] args) {
        Item<?> apple = new Apple();
        Apple trueApple = apple.as();
    }
}
