package edu.study.apiStudy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringProcessClass {
    String str;

    public void TransformationAndPresentationByToCharArray(){
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            System.out.println(chars[i]);
        }
    }


    public void TransformationAndPresentationByCharAt(){
        for (int i = 0; i < str.length(); i += 2) {
            System.out.println(str.charAt(i));
        }
    }

}
