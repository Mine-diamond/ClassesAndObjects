package com.item.another;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Test {
    public static void main(String[] args) {
        Item newItem = new Item();

        String word = Item.with(newItem,Item.class,new Pet(),(item,pet) ->{
            return "ABC";
        });
        System.out.println(word);
    }
}
