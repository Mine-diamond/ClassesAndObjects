package com.file.study;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class PracCopy {
    public static void main(String[] args) {
        try {
            copyDirectory(new File("F:\\software play in F\\2024\\play\\"),new File("F:\\新建文件夹2"));
        } catch (IOException e) {
            System.out.println("要复制的文件夹不存在！");
        }
    }

    public static void copyDirectory(File source,File target) throws IOException {

        if(!source.exists()) {
            throw new FileNotFoundException(source.getAbsolutePath());
        }

        if(!target.exists()){
            target.mkdirs();
        }

        File[] files = source.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                Path relativePath = source.toPath().relativize(file.toPath());
                File targetFile = new File(target, relativePath.toString());

                copyFile(file, targetFile);
            }else{
                Path relativePath = source.toPath().relativize(file.toPath());
                File targetFile = new File(target, relativePath.toString());
                targetFile.mkdirs();
                copyDirectory(file, targetFile);
            }
        }
    }

    public static void copyFile(File source,File target){
        System.out.println("正在复制文件："+source.getAbsolutePath());
        ArrayList<Integer> list = new ArrayList<>();

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(source));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(target))) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
