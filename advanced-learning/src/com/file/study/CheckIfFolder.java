package com.file.study;

import java.io.File;
import java.util.Scanner;

public class CheckIfFolder {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Folder name: ");
        String folderName = sc.nextLine();

        File folder = new File(folderName);
        System.out.println(folder.isDirectory());
    }
}
