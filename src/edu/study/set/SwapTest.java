package edu.study.set;


import java.util.Arrays;

public class SwapTest{
    public static void main(String[] args) {
        Integer[] a = {1,2,3,4};
        Swap(a,2,3);
        System.out.println(Arrays.toString(a));
    }

    public static<T> void Swap(T[] arr,int i,int j){
        if(i > arr.length-1 || j > arr.length-1 || i < 0 || j < 0){
            throw new IllegalArgumentException("索引越界");
        }

        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
