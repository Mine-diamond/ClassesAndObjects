package edu.study.classandobject;

public class Student2 {
    String id;
    String name;
    int age;
    private int mathScore;
    private int chineseScore;

    public Student2(String id, String name, int age, int mathScore, int chineseScore) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mathScore = mathScore;
        this.chineseScore = chineseScore;
    }

    public int getTotalScore() {
        int totalScore = mathScore + chineseScore;
        return totalScore;
    }

    public void showInfo() {
        System.out.println("The information of the student is: ");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Math Score: " + mathScore);
        System.out.println("Chinese Score : " + chineseScore + "\n");
    }

    public void setMathScore(int mathScore) {
        if (mathScore > 0 && mathScore <= 100) {
            this.mathScore = mathScore;
        }else {
            this.mathScore = 0;
            System.out.println("The math score is incorrect.");
        }
    }

    public int getMathScore() {
        return mathScore;
    }

}
