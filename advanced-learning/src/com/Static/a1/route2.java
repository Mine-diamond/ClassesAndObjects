package com.Static.a1;

public class route2 {

    static int counter = 0;

    public static void tr2(){
        System.out.print("tr2 ");
        counter += 2;
        System.out.println(counter);
        //if(counter >=10)return;
        route1.tr1();
    }
}
