package com.file.study;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOStudy1 {
    public static void main(String[] args) throws IOException {
        File files = new File("F:\\software play in F\\2024\\putIn.txt");
        System.out.println(files.exists());
        FileOutputStream fos = new FileOutputStream(files, true);

        byte[] toWrite = {60,61,62,63};

        fos.write(toWrite);
        fos.write("\n".getBytes());
        fos.write("This is a sentence\n".getBytes(),2,10);

        fos.close();
    }
}
