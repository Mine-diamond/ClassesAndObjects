package edu.study.apiStudy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringXN {
    String str;
    public void StringXN1() {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            System.out.print(chars[i]);
        }
    }

    public void StringXN2() {
        for (int i = 0; i < str.length(); i++) {
            System.out.print(str.charAt(i));
        }
    }

    public void StringXN3() {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            System.out.print(c);
        }
    }
}
