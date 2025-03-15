package edu.study.error;

import lombok.AllArgsConstructor;

import java.util.Objects;


public class Student {
    private String name;
    private int age;

    public Student(String name, int age){
        this.name = name;
        if(age < 0 || age > 100) {
            throw new RuntimeException("年龄不正确");
        }
        this.age = age;
    }

    public String toString(){
        return "姓名："+name+"，年龄："+age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
