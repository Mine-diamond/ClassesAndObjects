package edu.study.set;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;
import java.util.TreeSet;

public class TreeSetPrac {
    public static void main(String[] args) {
        TreeSet set = new TreeSet();
        set.add(new Student("爱",12));
        set.add(new Student("比",12));
        set.add(new Student("Alice",13));
        set.add(new Student("Alice",17));
        set.add(new Student("Bob",13));
        System.out.println(set);
    }
}

@Data
@AllArgsConstructor
class Student implements Comparable<Student> {
    String name;
    int age;


    @Override
    public int compareTo(Student o) {
        if(this.age == o.age && this.name == o.name) {
            return 0;
        }else if(this.age == o.age){
            return this.name.compareTo(o.name);
        }

        return this.age >= o.age ? 1 : -1;
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
