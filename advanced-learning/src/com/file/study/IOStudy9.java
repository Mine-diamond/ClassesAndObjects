package com.file.study;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOStudy9 {
    public static void main(String[] args) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("F:\\software_play_in_F\\2024\\stu.txt"))){

        } catch (Exception e){
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
    }
}
