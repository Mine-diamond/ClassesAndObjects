package com.object.student;

public class Test {
    public static void main(String[] args) {
        Student s1 = new Student(12,"asd");
        Student s2 = new Student(12,"asd");
        System.out.println(s1.toString());
        System.out.println(s2.toString());
        System.out.println(s1.equals(s2));
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
    }
}
