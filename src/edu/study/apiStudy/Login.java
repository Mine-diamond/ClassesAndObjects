package edu.study.apiStudy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Scanner;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Login {
    String account;
    String password;

    public void login() {
        Scanner sc = new Scanner(System.in);
        boolean infoRight = false;
        for (int i = 1; i <= 3; i++) {
            System.out.print("Enter your account: ");
            String myAccount = sc.nextLine();
            System.out.print("Enter your password: ");
            String myPassword = sc.nextLine();
            infoRight = account.equals(myAccount) && password.equals(myPassword);
            if (infoRight) {
                break;
            }
            if (i < 3) {
                System.out.println("Wrong password or account, please try again,You have " + (3-i) +" more chances.");
            }
        }
        if (infoRight) {
            System.out.println("You have successfully logged in!");
        }else {
            System.out.println("You have not successfully logged in， please try after five minutes.");
        }
    }
}
