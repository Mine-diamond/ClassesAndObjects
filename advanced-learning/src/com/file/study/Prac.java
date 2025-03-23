package com.file.study;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Prac {
    public static void main(String[] args) {
        EncryptionDecryption();
    }

    public static void EncryptionDecryption(){
        ArrayList<Integer> list = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("F:\\software play in F\\2024\\飘飘女王.jpg")){
            int a;
            while ((a = fis.read()) != -1){
                list.add(a);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        try (FileOutputStream fos = new FileOutputStream("F:\\software play in F\\2024\\飘飘女王.jpg")){
            for (Integer i : list) {
                fos.write(i ^ 2);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
