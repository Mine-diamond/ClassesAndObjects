package com.file.study;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IOStudy3 {
    public static void main(String[] args) {

        try(FileInputStream fis = new FileInputStream("F:\\software play in F\\2024\\putIn2.txt")){
            int i = 0;
//            while(fis.available()>0){
//                System.out.print((char)fis.read());
//            }

            while (!((i = fis.read()) == -1)){
                System.out.print((char)i);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
