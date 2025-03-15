package edu.study.stringbuilder;

import java.util.Scanner;

public class learn3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder s = new StringBuilder();
        s.append(sc.nextLine());
        String str = s.toString();
        String str1 = s.reverse().toString();
        System.out.println(str1);
        if (str.equals(str1)) {
            System.out.println("该字符串对称");
        } else {
            System.out.println("该字符串不对称");
        }
    }
}
