package edu.study.classandobject;

public class Student2Test {
    public static void main(String[] args) {
        Student2 stu1 = new Student2("01","张三",23,90,87);
        Student2 stu2 = new Student2("02","李四",24,69,91);

        int totalScore1 = stu1.getTotalScore();
        int totalScore2 = stu2.getTotalScore();
        System.out.println(stu1.name+"'s total score is " + totalScore1);
        System.out.println(stu2.name+"'s total score is " + totalScore2);

        stu1.showInfo();
        stu2.showInfo();

        stu1.setMathScore(91);
        int mathScore = stu1.getMathScore();
        System.out.println(mathScore);
    }

}
