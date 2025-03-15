package edu.study.apiStudy;

public class StringBuilderLearn {
    public static void main(String[] args) {
//        long startTime = System.currentTimeMillis();
//        String str = "";
//        for (int i = 0; i < 100000; i++) {
//            str += i;
//        }
//        System.out.println(str);
//        long endTime = System.currentTimeMillis();
//        System.out.println(endTime - startTime);

        long start=System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for(int i= 1;i<= 100000;i++) {
            sb.append(i);
        }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
    }
}
