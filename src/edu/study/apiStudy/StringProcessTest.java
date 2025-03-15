package edu.study.apiStudy;

public class StringProcessTest {
    public static void main(String[] args) {
        String str = "HelloWorld";

        StringProcessClass spc = new StringProcessClass(str);

        spc.TransformationAndPresentationByToCharArray();
        spc.TransformationAndPresentationByCharAt();
    }
}
