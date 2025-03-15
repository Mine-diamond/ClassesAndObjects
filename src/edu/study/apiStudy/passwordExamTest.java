package edu.study.apiStudy;

import java.util.Scanner;

public class passwordExamTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your password: ");
        String password = sc.nextLine();
        PasswordExam passwordExam = new PasswordExam(password);
        passwordExam.ifPasswordUsable();

        
    }
}
