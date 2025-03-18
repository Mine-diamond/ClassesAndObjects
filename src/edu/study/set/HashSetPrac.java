package edu.study.set;

import java.util.HashSet;

public class HashSetPrac {
    public static void main(String[] args) {
        HashSet<Student> set = new HashSet<>();
        set.add(new Student("Alice",14));
        set.add(new Student("Bob",14));
        set.add(new Student("Tom",11));
        set.add(new Student("Alice",14));
        set.add(new Student("Jerry",33));
        set.add(new Student("Jerry",14));
        System.out.println(set);

        HashSet<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);
        set1.add(4);
        set1.add(5);
        set1.add(6);
        set1.add(7);
        set1.add(8);
        set1.add(9);
        set1.add(10);
        set1.add(11);
        set1.add(12);
        set1.add(13);
        set1.add(14);
        System.out.println(set1);
    }
}
