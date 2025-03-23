package com.file.study;

import java.io.File;

public class T1 {
    public static void main(String[] args) {
        File target = new File("F:\\新建文件夹");
        File file = new File("F:\\software play in F\\2024\\play\\game\\az.java");
        File source = new File("F:\\software play in F\\2024\\play\\");
        File targetFile = new File(target, (file.getAbsolutePath().substring(source.getAbsolutePath().length() + 1)));
        System.out.println(source.getAbsolutePath());
        System.out.println(targetFile.getAbsolutePath());
    }
}
