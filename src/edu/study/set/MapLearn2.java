package edu.study.set;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapLearn2 {
    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        map.put("d",4);
        map.put("e",5);
        map.put("f",6);
        map.put("g",7);

        map.forEach((a,b) -> {System.out.println(a+"="+b);});

        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }


    }
}
