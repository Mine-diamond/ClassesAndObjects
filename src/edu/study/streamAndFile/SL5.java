package edu.study.streamAndFile;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SL5 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.stream().filter(x -> x % 2 == 0).forEach(System.out::println);
        System.out.println(list);
        List<Integer> list2 = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        System.out.println(list2);


        ArrayList<String> list3 = new ArrayList<>();
        list3.add("Alice,23");
        list3.add("Bob,24");
        list3.add("Carl,25");
        list3.add("Dan,26");
        list3.add("Elise,27");

        Map<String, Integer> maps = list3.stream().filter(s -> Integer.parseInt(s.split(",")[1]) > 24).collect(Collectors.toMap(s -> s.split(",")[0], s -> Integer.parseInt(s.split(",")[1])));

        System.out.println(maps);
    }
}
