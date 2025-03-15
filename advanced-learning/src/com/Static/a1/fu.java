package com.Static.a1;

public class fu {
        int num = 10;

      public void show1(){
          System.out.println("num");
      }

      public int show2(){
          System.out.println("num");
          return 120;
      }

      public void show3(){
          System.out.println("show...fu");
      }
}

class zi extends fu {

    int num = 20;

    public void printNum(){
        int num = 30;

        System.out.println(num);//输出为30
        System.out.println(this.num);//输出为20
        System.out.println(super.num);//输出为10
    }

    @Override
    public void show1(){
        System.out.println("num1");
    }

    @Override
    public int show2() {
        return super.show2();
    }

    public void show3(){
        System.out.println("show...zi");
    }
}

class sun extends fu {
    public void show3(){
        System.out.println("show...sun");
    }
}

class launch{
    public static void main(String[] args) {
        sun sun = new sun();
        sun.show3();
    }
}
