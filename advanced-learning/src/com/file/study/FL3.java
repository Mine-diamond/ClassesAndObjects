package com.file.study;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FL3 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        File files;
        System.out.println("请输入操作对象\n1.文件\n2.文件夹");
        int choice;
        while (true) {
            choice = Integer.parseInt(sc.nextLine());
            if (!(choice == 2 || choice == 1)) {
                System.out.println("输入有误，请重新输入");
            }else {
                break;
            }
        }

        String x = "";

        switch (choice) {
            case 1:
                System.out.println("请输入文件路径: ");
                x = sc.nextLine();
                break;
            case 2:
                System.out.println("请输入文件夹路径: ");
                x = sc.nextLine();
                break;
        }


        files = new File("F:\\software play in F",x);

        System.out.println("完整路径: " + files.getAbsolutePath());
        System.out.println("路径是否存在: " + files.getParentFile().exists());


        System.out.println("请输入操作\n1.创建\n2.删除");
        int choice2;

        while (true) {
            choice2 = sc.nextInt();
            if (!(choice2 == 2 || choice2 == 1)) {
                System.out.println("输入有误，请重新输入");
            }else {
                break;
            }
        }

        if(choice == 1){
            if(choice2 == 1){
                if(files.getParentFile().exists() == true){
                    files.createNewFile();
                }else {
                    File pf = files.getParentFile();
                    pf.mkdirs();
                    files.createNewFile();
                }

            }else {
                files.delete();
            }
        }else {
            if(choice2 == 1){
                files.mkdirs();
            }else {
                files.delete();
            }
        }

    }
}
