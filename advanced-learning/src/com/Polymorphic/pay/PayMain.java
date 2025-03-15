package com.Polymorphic.pay;

import java.util.Scanner;

public class PayMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Pay payment = null;
        System.out.println("""
                请选择支付方式：
                1.支付平台支付
                2.银行卡网银支付
                3.信用卡快捷支付
                """);
        System.out.print("请输入你的支付方式：");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                payment = new PayPlantForm();
                payment.payment();
                break;
            case 2:
                payment = new PayCardSlow();
                payment.payment();
                break;

            case 3:
                payment = new PayCardQuick();
                payment.payment();
                break;
            default:
                System.out.println("输入有误！");
        }
    }
}
