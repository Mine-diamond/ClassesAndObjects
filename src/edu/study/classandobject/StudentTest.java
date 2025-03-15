package edu.study.classandobject;

public class StudentTest {
    public static void main(String[] args) {
        Student s1 = new Student("Alice",12);
        System.out.println(s1.name);
        s1.name = "Jane";
        String myName = s1.name;
        s1.sayHello("Bob");
    }

}
