package com.object.student;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class Student {
    int age;
    String name;

    @Override
    public String toString(){
        return "Student [age=" + age + ", name=" + name + "]";
    }

//    public boolean equals(Object obj){
//        if(obj instanceof Student){
//            Student obj1 = (Student)obj;
//            return this.age == obj1.age && this.name.equals(obj1.name);
//        }else {
//            return false;
//        }
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }
}
