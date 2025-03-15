package com.packagingType.a1;

public class Learn1 {
    public static void main(String[] args) {
        Integer a = 10;
        int bN = 20;
        Integer b;
        b = bN;
        System.out.println(b);
        Integer c = Integer.valueOf(30);
        int cN = c;
        String d = "12345";
        int dN = Integer.valueOf(d);
        System.out.println(d);
        C1 c1 = new C1();
        c1.foo = c;
        System.out.println("结果："+c.compareTo(30));
        String binary = Integer.toBinaryString(30);
        System.out.println(binary);
    }
}
