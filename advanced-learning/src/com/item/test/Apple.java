package com.item.test;

public class Apple extends Item{
    String name = "苹果";
    String description = "恢复八点饱食度";
    public Apple() {}

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void eat(){
        System.out.println("恢复了八点饱食度");
    }


}
