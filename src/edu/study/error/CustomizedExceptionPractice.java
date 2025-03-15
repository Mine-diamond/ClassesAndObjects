package edu.study.error;

import java.util.Scanner;

public class CustomizedExceptionPractice {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入年龄 ：");
        int a = 0;
        try {
            a = Integer.parseInt(sc.nextLine());
            if( a > 150 || a < 0 ){
                throw new AgeException("年龄不合法，必须在0到150之间");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

}

class AgeException extends RuntimeException{
    public AgeException(String message) {
        super(message);
    }
}