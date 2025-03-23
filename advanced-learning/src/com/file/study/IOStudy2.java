package com.file.study;

import java.io.FileOutputStream;
import java.io.IOException;

public class IOStudy2 {
    public static void main(String[] args) {

        try (FileOutputStream fos = new FileOutputStream("F:\\software play in F\\2024\\putIn2.txt");){
            fos.write("I am eating an apple".getBytes());

        } catch(IOException e){
            e.printStackTrace();
        }


    }
}
