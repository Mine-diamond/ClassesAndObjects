package edu.study.apiStudy;



import java.util.Scanner;


public class Block {
    String mobileTelephoneNumber;
    char[] mobileTelephoneNumberChars;

    public Block(String mobileTelephoneNumber) {
        this.mobileTelephoneNumber = mobileTelephoneNumber;
        mobileTelephoneNumberChars = mobileTelephoneNumber.toCharArray();

        boolean isNumber = false;
        boolean RightLength = false;
        int numberlength = 0;
        if (mobileTelephoneNumberChars.length == 11) {
            RightLength = true;
        }
        for (int i = 0; i < mobileTelephoneNumberChars.length; i++) {
            if (mobileTelephoneNumberChars[i] >= '0' && mobileTelephoneNumberChars[i] <= '9') {
                numberlength++;
            }
        }
        if (numberlength == 11) {
            isNumber = true;
        }

        if (isNumber && RightLength) {
            System.out.println("注册成功！");
        }else {

        }
    }
}
