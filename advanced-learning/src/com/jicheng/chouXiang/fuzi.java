package com.jicheng.chouXiang;

public class fuzi {
    public static void main(String[] args) {
        zi zi = new zi();
        int b = zi.a;
    }
}

abstract class fu {
    static int a = 0;
    abstract void abstractMethon();
}

class zi extends fu {
    void abstractMethon() {
        System.out.println("I rewrite it");
    }
}
