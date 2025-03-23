package com.file.study;

import java.io.*;

public class IOStudy6 {
    public static void main(String[] args) {

        Long startTime = System.currentTimeMillis();
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream("F:\\software play in F\\2024\\影视飓风码率.mp4")); BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("F:\\software play in F\\2024\\play\\影视飓风码率.mp4"))){
            int len;
            byte[] bts = new byte[8192];
            while ((len = bis.read(bts)) != -1) {
                bos.write(bts,0, len);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        Long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
