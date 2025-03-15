package edu.study.apiStudy;

public class StringLearn4 {
    public static void main(String[] args) {
        
        String a = "abcde";
        char[] b = a.toCharArray();
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }
        for (int i = 0; i < a.length(); i++) {
            System.out.println(a.charAt(i));
        }

        char c = a.charAt(a.length()-1);
        System.out.println(c);
    }
}
