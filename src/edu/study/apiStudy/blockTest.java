package edu.study.apiStudy;

import java.util.Scanner;

public class blockTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your mobile number: ");
        String mobileTelephoneNumber = sc.nextLine();

        String mobileTelephoneNumberFirstThree = mobileTelephoneNumber.substring(0, 3);
        String mobileTelephoneNumberLastFour = mobileTelephoneNumber.substring(7,11);
        String ProcessedMobileTelephoneNumber = mobileTelephoneNumberFirstThree + "****" + mobileTelephoneNumberLastFour;
        System.out.println("Mobile Telephone Number: " + ProcessedMobileTelephoneNumber);
    }
}
