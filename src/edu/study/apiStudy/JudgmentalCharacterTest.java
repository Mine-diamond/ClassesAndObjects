package edu.study.apiStudy;

import java.util.Scanner;

public class JudgmentalCharacterTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a character: ");
        String input = sc.nextLine();
        JudgmentalCharacter JuStr = new JudgmentalCharacter(input);
        JuStr.Judge();


    }
}
