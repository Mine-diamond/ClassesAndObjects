package edu.study.set;

import java.util.ArrayList;
import java.util.List;

public class T1 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(sum(list));
        addNumbers(list);
        System.out.println(sum(list));
    }

    public static double sum(List<? extends Number> list){
        int sum = 0;
        for(Number n : list){
            sum += n.doubleValue();
        }
        return sum;
    }

    public static void addNumbers(List<? super Integer> list){
        list.add(4);
        list.add(5);
        list.add(6);
    }
}
