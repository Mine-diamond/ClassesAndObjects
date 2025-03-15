package com.packagingType.a1;

public class Learn2 {
    public static void main(String[] args) {
        String a = "10,20,30,40,50";
        String[] as = a.split(",");
        int[] b = new int[as.length];
        for (int i = 0; i < as.length; i++) {
            b[i] = Integer.parseInt(as[i]);
        }

        for (int s = 0; s < as.length; s++) {
            System.out.print(b[s] + " ");
        }
    }
}
