package edu.study.apiStudy;

import java.util.Arrays;

public class PaiXu2 {
    public static void main(String[] args) {
        int[] a = {33,12,34,-8,1,22};
        for (int i = 0; i < a.length - 1; i++) {
            System.out.println(" ");
            for (int j = i; j < a.length - 1; j++) {
                if (a[i] > a[j]) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
                System.out.println("i = "+ i +",j = "+ j +Arrays.toString(a));
            }
        }
        System.out.println(Arrays.toString(a));
    }
}
