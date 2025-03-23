package com.file.study;

import java.io.FileInputStream;

public class IOStudy4 {
    public static void main(String[] args) {

        byte[] bts = new byte[1024];

        try (FileInputStream fis = new FileInputStream("F:\\software play in F\\2024\\putIn2.txt");){
            fis.read(bts);

            System.out.println(new String(bts).trim());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
