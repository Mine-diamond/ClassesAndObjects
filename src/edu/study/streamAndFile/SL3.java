package edu.study.streamAndFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

public class SL3 {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1,"one");
        map.put(2,"two");
        map.put(3,"three");
        map.put(4,"four");
        map.put(5,"five");
        map.put(6,"six");
        map.put(7,"seven");
        map.put(8,"eight");
        map.put(9,"nine");
        map.put(10,"ten");
        map.entrySet().stream().forEach(System.out::println);

        int[] arr = {11,22,33};
        Arrays.stream(arr).forEach(System.out::println);

        Stream.of(12,23,"34").forEach(System.out::println);
    }
}
