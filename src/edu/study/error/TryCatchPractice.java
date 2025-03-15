package edu.study.error;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TryCatchPractice {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number: ");
        try{
            int a = Integer.parseInt(sc.nextLine());
        }catch (NumberFormatException e){
            System.out.println(e);
            System.out.println(e.getMessage());
        }
    }
}
