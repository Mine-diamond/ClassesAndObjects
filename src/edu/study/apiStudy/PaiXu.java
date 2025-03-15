package edu.study.apiStudy;


import java.lang.reflect.Array;
import java.util.Arrays;

public class PaiXu {
    public static void main(String[] args) {
        int[] a = {12,5,33,17,-6,30,-14,22,1};
        for(int i = 0;i < a.length - 2;i++){
            System.out.println("循环："+ (i + 1));
            for(int j = 0;j < a.length - i - 1;j++){
                if(a[j+1] < a[j]){
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }

                System.out.println("j = "+ j +" "+Arrays.toString(a));
            }

        }

        System.out.println("\n结果是"+Arrays.toString(a));
    }

}
