package edu.study.apiStudy;

public class StringLearn5 {
    public static void main(String[] args) {
        String str = (" 191 5525 5036");
        String newStr = str.replaceAll("^+?[\\d\\s]{3,}$", "***");
        System.out.println(newStr);

        String str1 = "ALiceLoveLongHairLongAgo.";
        String[] words = str1.split("L");
        System.out.println(words.length);
        for (String word : words) {
            System.out.print(word + " ");
        }


    }
}
