package com.impl.test;

public class FoodEffect extends Effect{
    int Saturation = 10;

    public FoodEffect() {}

    public FoodEffect(int Saturation) {
        this.Saturation = Saturation;
    }

    public void eat(){
        System.out.println("恢复了"+Saturation+"点饱食度");
    }

    public void onUse(){
        eat();
    }

}
