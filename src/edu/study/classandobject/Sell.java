package edu.study.classandobject;

import java.util.Scanner;

public class Sell {

    public String[] foodName = new String[10];
    int[] foodPrice = new int[10];
    int totalFoodSort = 0;

    public Sell(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the following names:");
        for (int i = 0;i <10; i++) {
            foodAdd(sc);
        }

    }

        /*
        * 
        * */
     void foodAdd(Scanner sc) {
        if (totalFoodSort < 10) {
            System.out.print("请输入食物名称: ");
            String thisFoodName = sc.nextLine();
            System.out.print("请输入食物价格: ");
            int thisFoodPrice = sc.nextInt();
            sc.nextLine();
            foodName[totalFoodSort] = thisFoodName;
            foodPrice[totalFoodSort] = thisFoodPrice;
            totalFoodSort++;
        }else {
            System.out.println("最多添加十个商品。");
        }
    }

    public void show(){
        System.out.println("全部商品: ");
        if(totalFoodSort == 0) {
            System.out.println("暂无商品");
        }else{
            for (int i = 0; i < totalFoodSort; i++) {
                System.out.println("名称:" + foodName[i] + "\t价格 " + foodPrice[i] + " 元/个");
            }
        }


    }
}
