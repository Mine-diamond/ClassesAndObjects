package edu.study.set;

import java.util.ArrayList;
import java.util.Collection;

public class l1 {
    public static void main(String[] args) {
        Collection<String> c = new ArrayList<>();
        c.add("a");
        c.add("b");
        c.add("c");
        boolean n = c.contains("d");
        System.out.println(n);
        System.out.println();
        c.remove("a");
        System.out.println(c);
    }
}
