package com.file.study;

import java.io.File;
import java.util.HashMap;


public class FL4 {
    public static void main(String[] args) {
        String path = "F:\\software play in F";
        File file = new File(path);

        HashMap<String,Integer> map = new HashMap<>();
        getFileNum(file,map);

        map.forEach((key, value) -> System.out.println(key+":"+value + "个"));

    }


    public static void getFileNum(File file,HashMap<String,Integer> map) {

        File[] files = file.listFiles();
        if (files == null) {
            return ;
        }
        for (File file1 : files) {
            if (file1.isDirectory()) {
                getFileNum(file1,map);
            }else {
                String[] split = file1.getName().split("\\.");
                //System.out.println(file1.getName());
                //System.out.println(split.length);
                String s = "无属性";
                if(split.length >= 2){
                    s = split[split.length-1];
                }
                int count = 0;
                try {
                    count = map.get(s);
                } catch (Exception e) {

                }
                map.put(s,count+1);
            }
        }
    }

    public static  void deleteSubFile(File file) {
        File[] files = file.listFiles();

        if (files == null) {
            return;
        }
        for (File file1 : files) {
            if (file1.isDirectory()) {
                deleteSubFile(file1);
            }else {
                file1.delete();
            }
        }
        file.delete();

    }

    public static void getJavaFile(File file) {
        File[] files = file.listFiles();

        if (files == null) {
            return;
        }

        for (File file1 : files) {
            if (file1.isDirectory()) {
                getJavaFile(file1);
            }else if(file1.isFile() && file1.getName().endsWith(".java")) {
                System.out.println(file1.getAbsoluteFile());
            }
        }
    }

}
