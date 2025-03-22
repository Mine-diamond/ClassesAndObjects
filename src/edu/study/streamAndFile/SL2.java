package edu.study.streamAndFile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SL2 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("张良");
        list.add("王二麻子");
        list.add("谢广坤");
        list.add("张三丰");
        list.add("张翠山");

        list.stream().forEach(System.out::println);
        System.out.println();
        list.stream().filter(name -> name.charAt(0) == '张').filter(name -> name.length() == 3).forEach(System.out::println);
    }
}
