package com.Static.a1;

public class ArrayMain {

    public static void main(String[] args) {
        int[] array = {12,23,34,45};
        ArrayTool.findMaxNumber(array);
        ArrayTool.findMinNumber(array);
        ArrayTool.ArrayToString(array);

        long time = System.currentTimeMillis();
        System.out.println("Time to find max number: " + time);

    }


}
