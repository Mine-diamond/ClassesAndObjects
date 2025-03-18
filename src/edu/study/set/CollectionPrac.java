package edu.study.set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

public class CollectionPrac {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        TreeSet<String> set = new TreeSet<>();

        Collections.addAll(list,"a","b","c","d");
        System.out.println(list);
        Collections.addAll(set,"a","b","c","d");
        System.out.println(set);
        Collections.shuffle(list);
        System.out.println(list);

        Collections.replaceAll(list,"a","b");
        System.out.println(list);

        Collections.swap(list,0,1);
        System.out.println(list);

    }
}
