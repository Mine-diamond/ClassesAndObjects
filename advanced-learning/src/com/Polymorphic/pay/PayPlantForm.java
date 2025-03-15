package com.Polymorphic.pay;

import java.util.Scanner;

public class PayPlantForm implements Pay{

    @Override
    public void payment() {
        Scanner sc = new Scanner(System.in);

        System.out.print("请输入支付金额：");
        double payAmount = sc.nextDouble();
        System.out.println("通过支付平台支付了"+payAmount+"元");
    }
}
