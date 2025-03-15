package edu.study.apiStudy;

public class StringLearn2 {
    public static void main(String[] args) {
        String str1 = "Alice";
        String str2 = "Alice";
        String str3 = "alice";
        System.out.println(str1.equals(str2));
        System.out.println(str1.equals(str3));
        System.out.println(str1.compareTo(str2));
        System.out.println(str1.compareTo(str3));
        System.out.println(str1.compareToIgnoreCase(str2));
        System.out.println(str1.compareToIgnoreCase(str3));
        System.out.println(str1.equalsIgnoreCase(str3));
    }
}
