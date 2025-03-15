package edu.study.apiStudy;

public class StringLearn1 {
    public static void main(String[] args) {
        String str1 = new String("hello");
        System.out.println(str1.toUpperCase());
        String str2 = new String("hello");
        String str3 = "hei";
        String str4 = "hei";
        System.out.println(str2 == str1);
        System.out.println(str3 == str4);

        char[] a = {'a', 'b', 'c', 'd', 'e', 'f'};
        String str5 = new String(a);
        System.out.println(a);
        boolean gu = str5.equals(str1);
        System.out.println(gu);
        String str6 = "a" + "b" + "c" + "d" + "e" + "f";
        System.out.println(str6);
    }
}
