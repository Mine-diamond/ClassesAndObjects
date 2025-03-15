package edu.study.error;

import java.util.Scanner;

public class ManyCatchPractice {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] a = {1,2,3,4,5,6,7,8};
        System.out.println("请输入你要访问的引索：");
        try {
            int n = Integer.parseInt(sc.nextLine());
            System.out.println("该引索对应的值为"+a[n]);
        }catch (NumberFormatException e) {
            System.out.println("输入的不是有效的整数！");
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("索引超出数组范围！");
        }
    }
}
