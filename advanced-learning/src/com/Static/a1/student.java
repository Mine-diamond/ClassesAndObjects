package com.Static.a1;

import jdk.jfr.DataAmount;
import lombok.Data;

@Data
public class student {
    private String name;
    private int age;
    private int grade;
    int score;
    private static String school = "上海电力大学";

    public static String getSchool() {
        return school;
    }
}

class maleStudent extends student {
    public static String getSchool(){
        System.out.println("The male student's school is " + student.getSchool());
        return student.getSchool();
    }
}



class femaleStudent extends student {

}