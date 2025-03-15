package edu.study.set;

import edu.study.error.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public class L2 {
    public static void main(String[] args) {
        Collection<Student> students = new ArrayList();
        students.add(new Student("张三",12));
        students.add(new Student("李四",13));
        students.add(new Student("王五",14));

        Iterator<Student> it = students.iterator();

        while (it.hasNext()) {
            Student stu = it.next();
            System.out.println(stu);
        }

        System.out.println(students);

        for(Student stu : students){
            System.out.println(stu);
        }

        students.forEach(stu -> System.out.println(stu));
        it.next();
        students.remove(new Student("李四",13));



    }
}
