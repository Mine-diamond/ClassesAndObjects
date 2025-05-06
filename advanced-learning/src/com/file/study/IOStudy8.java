package com.file.study;

import java.io.*;

public class IOStudy8 {
    public static void main(String[] args) {
        try(InputStreamReader isr = new InputStreamReader(new FileInputStream("F:\\software_play_in_F\\2024\\putIn3.txt"),"GBK");OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("F:\\software_play_in_F\\2024\\putIn4.txt",true),"GBK")) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while ((i = isr.read()) != -1) {
                sb.append((char) i);
            }
            System.out.println(sb.toString());

            osw.write(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
