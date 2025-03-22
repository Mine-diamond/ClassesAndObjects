package edu.study.set;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class ML3 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入:");
        String needAnalysis = sc.nextLine();
        System.out.println(needAnalysis);
        char[] chars = needAnalysis.toCharArray();
        LinkedHashMap<Character, Integer> map = new LinkedHashMap<>();
        int a = 0;
        for (int i = 0; i < chars.length; i++) {

            if(map.containsKey(chars[i])) {
                a = map.get(chars[i]);
            }
            map.put(chars[i], a + 1);
        }
        map.forEach((k, v) -> System.out.println(k + " : " + v));

    }
}
