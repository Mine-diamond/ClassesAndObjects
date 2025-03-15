package edu.study.error;

import java.util.Scanner;

public class StudentTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean ifValid = true;
        System.out.print("请输入姓名：");
        String name = null;
        name = sc.nextLine();
        int age = 0;

        while (true) {try {
            System.out.print("请输入年龄：");
            age = Integer.parseInt(sc.nextLine());
            break;
        } catch (Exception e) {
            System.out.println("请输入有效年龄");
        }}

        sc.close();

        Student student = null;
        student = new Student(name, age);

        System.out.println(student);

    }
}
