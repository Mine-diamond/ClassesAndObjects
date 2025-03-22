package edu.study.streamAndFile;

import java.util.ArrayList;
import java.util.List;

public class SL1 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("张良");
        list.add("王二麻子");
        list.add("谢广坤");
        list.add("张三丰");
        list.add("张翠山");

        List<String> list1 = new ArrayList<>();

        for (String s : list) {
            if(s.charAt(0) == '张'){
                list1.add(s);
            }
        }

        System.out.println(list1);

        List<String> list2 = new ArrayList<>();
        for (String s : list1) {
            if(s.length() == 3){
                list2.add(s);
            }
        }

        System.out.println(list2);
    }
}
