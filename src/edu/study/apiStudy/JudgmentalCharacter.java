package edu.study.apiStudy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JudgmentalCharacter {
    String str;

    public void Judge(){
        char[] chars = str.toCharArray();

        int NumberOfCapitalLetters = 0;
        int NumberOfLowercaseLetters = 0;
        int NumberOfNumbers = 0;
        int NumberOfOtherCharacters = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'a' && chars[i] <= 'z') {
                NumberOfLowercaseLetters++;
            }else if (chars[i] >= 'A' && chars[i] <= 'Z') {
                NumberOfCapitalLetters++;
            }else if (chars[i] >= '0' && chars[i] <= '9') {
                NumberOfNumbers++;
            }else {
                NumberOfOtherCharacters++;
            }
        }

        System.out.println("大写字母共"+NumberOfCapitalLetters+"个");
        System.out.println("小写字母共"+NumberOfLowercaseLetters+"个");
        System.out.println("数字共"+NumberOfNumbers+"个");
        System.out.println("其他字符共"+NumberOfOtherCharacters+"个");
    }
}
