package edu.study.set;

import java.util.*;
import java.util.function.BiConsumer;

public class ML4 {
    public static void main(String[] args) {
        LinkedHashMap<Integer, ArrayList<String>> map = new LinkedHashMap<>();
        ArrayList<String> list1 = new ArrayList<>();
        Collections.addAll(list1,"a","b","c","d");
        ArrayList<String> list2 = new ArrayList<>();
        Collections.addAll(list2,"e","f","g","h");
        ArrayList<String> list3 = new ArrayList<>();
        Collections.addAll(list3,"i","j","k","l");
        ArrayList<String> list4 = new ArrayList<>();
        Collections.addAll(list4,"m","n","o","p");
        map.put(1, list1);
        map.put(2, list2);
        map.put(3, list3);
        map.put(4, list4);
        Set<Map.Entry<Integer, ArrayList<String>>> entries = map.entrySet();
        for (Map.Entry<Integer, ArrayList<String>> entry : entries) {
            int key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            System.out.print(key + ":");
            for (int i = 0; i < value.size() - 1; i++) {
                System.out.print(value.get(i) + ",");
            }
            System.out.println(value.get(value.size() - 1));
        }

        System.out.println();

        map.forEach((key, value) -> {
            System.out.print(key + ":");
            for (int i = 0; i < value.size() - 1; i++) {
                System.out.print(value.get(i) + ",");
            }
            System.out.println(value.get(value.size() - 1));
        });
    }
}
