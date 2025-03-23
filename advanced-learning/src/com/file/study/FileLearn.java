package com.file.study;

import java.io.File;
import java.io.IOException;

public class FileLearn {
    public static void main(String[] args) throws IOException {
        File file1 = new File("A.txt");
        File file2 = new File("F:\\software play in F");
        File file3 = new File("F:\\software play in F","A.txt");

        System.out.println(file1.exists());
        System.out.println(file2.exists());
        System.out.println(file3.exists());

        file1.createNewFile();
        file3.createNewFile();

        /*
        * # File类的常用方法

        `public boolean isDirectory()` 判断此路径名表示的File是否为文件夹
        `public boolean isFile()` 判断此路径名表示的File是否为文件
        `public boolean exists()` 判断此路径名表示的File是否存在
        `public long length()` 返回文件的大小（字节数量）
        `public String getAbsolutePath()` 返回文件的绝对路径
        `public String getPath()` 返回定义文件时使用的路径
        `public String getName()` 返回文件的名称，带后缀
        `public long lastModified()` 返回文件的最后修改时间（时间毫秒值）

        * */

        System.out.println("File1是否为文件："+file1.isFile());
        System.out.println("File1是否为目录："+file2.isDirectory());
        System.out.println("File1是否为目录："+file3.isDirectory());

        System.out.println("------------------------------------------");

        System.out.println("File1的大小是"+file1.length());
        System.out.println("File2的大小是"+file2.length());
        System.out.println("File3的大小是"+file3.length());

        System.out.println("------------------------------------------");

        System.out.println("File1的定义路径是"+ file1.getPath()+"\n\t 绝对路径是"+file1.getAbsolutePath());
        System.out.println("File2的定义路径是"+ file2.getPath()+"\n\t 绝对路径是"+file2.getAbsolutePath());
        System.out.println("File3的定义路径是"+ file3.getPath()+"\n\t 绝对路径是"+file3.getAbsolutePath());

        System.out.println("------------------------------------------");
        System.out.println("File1的名称是"+file1.getName()+"\n\t 上次修改时间是"+file1.lastModified());
        System.out.println("File2的名称是"+file2.getName()+"\n\t 上次修改时间是"+file2.lastModified());
        System.out.println("File3的名称是"+file3.getName()+"\n\t 上次修改时间是"+file3.lastModified());
    }


}
