package edu.study.set;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapLearn {
    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        map.put("one",1);
        map.put("two",2);
        map.put("three",3);
        map.put("me",10);
        map.put("me",100);
        System.out.println(map);

        map.remove("one");
        map.remove("me");
        map.remove("t");
        System.out.println(map);
        System.out.println("two "+map.get("two"));
        System.out.println("me "+map.get("me"));
        System.out.println(map.size());


        boolean b1 = map.containsKey("one");
        System.out.println(b1);
        boolean b2 = map.containsKey("two");
        System.out.println(b2);
        System.out.println("2 "+map.containsValue(2));

        Set<String> strings = map.keySet();
        ArrayList<String> list = new ArrayList<>(strings);
        System.out.println(list.get(0));

        map.clear();
        System.out.println(map);
    }
}
