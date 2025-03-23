package com.file.study;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOStudy7 {
    public static void main(String[] args) {
        //method();

        try(FileWriter fw = new FileWriter("F:\\software play in F\\2024\\putIn2.txt")){
            fw.write("This is a test\n怎么改变光标位置？\n");
            fw.flush();
            fw.write("This is a test\n怎么改变光标位置？\n");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void method() {
        try(FileReader fr = new FileReader("F:\\software play in F\\2024\\putIn2.txt")){
            char[] read = new char[1024];
            fr.read(read);
            System.out.println(new String(read).trim());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
