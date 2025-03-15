package edu.study.stringbuilder;

public class learn1 {
    public static void main(String[] args) {
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder("ABCDE");
        s1.append("hello");
        StringBuilder s4 = s1.append(123.5);
        System.out.println(s4);
        s1.append(true);
        StringBuilder s3 = s1.append('A');
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
    }
}
