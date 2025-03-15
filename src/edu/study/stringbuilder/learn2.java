package edu.study.stringbuilder;

import java.util.Scanner;

public class learn2 {
    public static void main(String[] args) {
        StringBuilder s1 = new StringBuilder();
        s1.append("Alice").append("\s").append("Bob").reverse();
        System.out.println(s1); //输出boB ecilA

        StringBuilder s2 = new StringBuilder();
        int a = s2.append("StringBuilder 是一个可变的字符串对象").length();
        System.out.println(a);

        StringBuilder s3 = new StringBuilder();
        s3.append("红色").append("绿色").append("黄色");
        String[] sArr = s3.toString().split("色");
        for (int i = 0; i < sArr.length; i++) {
            System.out.print(sArr[i]);
        } //输出红绿黄
    }
}
