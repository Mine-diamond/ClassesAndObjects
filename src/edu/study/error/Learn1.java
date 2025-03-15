package edu.study.error;

public class Learn1 {
    public static void main(String[] args) {
        try{
            System.out.println(10/0);
        }catch(Exception e){
            System.out.println(e+"出错了");
        }




    }
}
