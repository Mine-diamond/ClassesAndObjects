package edu.study.set;

public class CC {
    public static void main(String[] args) {
        showResult(2,3,5,7);
    }

    public static void showResult(int b, int ... a){
        int result = 0;
        switch(b){
            case 1:
                for(int i=0; i<a.length; i++){
                    result += a[i];
                }
                System.out.println(result);
                break;
            case 2:
                result = a[0];
                for(int i=1; i<a.length; i++){
                    result -= a[i];
                }
                System.out.println(result);
                break;
        }
    }
}
