package edu.study.error;

public class DividePractice {
    public static void main(String[] args) {
        try {
            int reault = divide(5,0);
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
    }

    public static int divide(int a, int b) {
        int result = 0;
        try {
            result = a / b;
        }catch (ArithmeticException e){
            throw new ArithmeticException("Divide by zero");
        }
        return result;
    }
}
