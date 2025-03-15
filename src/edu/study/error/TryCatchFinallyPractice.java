package edu.study.error;

import java.util.Scanner;

public class TryCatchFinallyPractice {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a,b;

        a = readInput(sc,"请输入被除数:");
        b = readInput(sc,"请输入除数: ");

        try{
            System.out.println("商为"+a/b);
        }catch (ArithmeticException e) {
            System.out.println("除数不能为零");
        }finally {
            System.out.println("计算结束");
        }
    }

    public static int readInput(Scanner sc,String prompt){
        int num = 0;
        System.out.print(prompt);
        while(true){
            try{
                num = Integer.parseInt(sc.nextLine());
                break;
            }catch (NumberFormatException e) {
                System.out.println("输入有误，请重新输入");
            }
        }
        return num;
    }
}
