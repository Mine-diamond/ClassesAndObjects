package edu.study.classandobject;

import java.util.Scanner;

public class SellTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Sell sell1 = new Sell();
        sell1.show();
        sell1.foodAdd(sc);
        System.out.println(sell1.foodName[0]);
    }
}
