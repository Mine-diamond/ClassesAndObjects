package edu.study.sell;

import java.util.Scanner;

public class Sell2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean signed = false;
        boolean logined = false;
        String username = "O";
        String password = "0";
        double money = 30;
        int level = 0;
        boolean programmeBreak = false;
        int[] haveFood = {0,0,0,0,0};

        while (!programmeBreak) {
            if (!logined){//未登录时
                System.out.println("请选择(尚未登录):\n1.注册\n2.登录\n3.查看商品\n4.退出程序");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> {//注册
                        System.out.print("请输入用户名: ");
                        username = sc.nextLine();
                        System.out.print("请输入密码: ");
                        password = sc.nextLine();
                        signed = true;
                        System.out.println("已注册，请登录");
                    }
                    case 2 -> {//登录
                        if (signed) {
                            logined = login(username,password,sc);
                        }else {
                            System.out.println("请先注册！");
                        }
                    }
                    case 3 -> {//查看商品
                        showFood();
                        System.out.println("请登录后购买(按enter键继续)");
                        sc.nextLine();
                    }
                    case 4 -> {//退出程序
                        programmeBreak = true;
                    }
                }
            }
            if (logined) {
                System.out.println("请选择(用户名：" + username + ")" +
                        "\n1.查看我的余额" +
                        "\n2.查看我的仓库" +
                        "\n3.查看我的特权" +
                        "\n4.购买食品" +
                        "\n5.退出登录");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                        money = showMoney(money,sc);
                    }
                    case 2 -> {
                        haveFood = showMyFood(haveFood,sc);
                    }
                    case 3 -> {
                        double[] mix = buyVIP(level,money,sc);
                        level = (int) mix[0];
                        money = mix[1];
                    }
                    case 4 -> {
                        double[] mix = buyFood(money,haveFood,level,sc);
                        money = mix[4];
                        for (int i = 0;i <= 3; i++) {
                            haveFood[i] = (int)mix[i];
                        }
                    }
                    case 5 -> {
                        logined = false;
                    }
                }
            }
        }
    }

    //展示食物与价格
    public static void showFood() {
        System.out.println("""
                                1.香蕉\t4元/个
                                2.苹果\t6元/个
                                3.鸡蛋\t2元/个
                                4.牛奶\t4元/个
                                5.蜂蜜\t16元/个""");
    }

    //登录方法
    public static boolean login(String username, String password,Scanner sc) {
        System.out.print("请输入用户名: ");
        String myUsername = sc.nextLine();
        System.out.print("请输入密码: ");
        String myPassword = sc.nextLine();
        boolean logined = false;
        if (username.equals(myUsername) && password.equals(myPassword)) {
            logined = true;
            System.out.println("登录成功！");
            return logined;
        } else {
            System.out.println("密码或用户名不正确");
            return logined;
        }
    }

    //查看我的余额
    public static double showMoney(double money,Scanner sc) {
        boolean moneyCycled = true;
        while (moneyCycled) {
            System.out.println("您现在有" + money + "元");
            System.out.println("1.挣钱\n2.回到首页");
            int moneyChoice = sc.nextInt();
            sc.nextLine();
            if (moneyChoice == 1) {
                money += 30;
                System.out.println("您已挣30元");
            } else if (moneyChoice == 2) {
                moneyCycled = false;
            } else {
                System.out.println("输入无效，请重新输入");
            }
        }
        return money;
    }


    public static int[] showMyFood(int[] haveFood,Scanner sc) {
        boolean foodCycled = true;
        while (foodCycled) {
            System.out.println("你现在拥有的商品:" +
                    "\n香蕉  " + haveFood[0] + "个" +
                    "\n苹果  " + haveFood[1] + "个" +
                    "\n鸡蛋  " + haveFood[2] + "个" +
                    "\n牛奶  " + haveFood[3] + "个" +
                    "\n蜂蜜  " + haveFood[4]);
            System.out.println("请选择：\n1.食用\n2.返回");
            int foodChoice = sc.nextInt();
            sc.nextLine();
            switch (foodChoice) {
                case 1 -> {

                }
                case 2 -> {
                    foodCycled = false;
                }
                default -> {
                    System.out.println("输入有误");
                }
            }

        }
        return haveFood;
    }

    //购买会员
    public static double[] buyVIP(int level,double money,Scanner sc) {
        if (level == 0) {
            System.out.println("您现在不是会员\n1.购买会员\n2.返回");
            int levelChoice = sc.nextInt();
            sc.nextLine();
            if (levelChoice == 1) {
                System.out.println("请选择会员类型:(您现在有" + money + "元)" +
                        "\n1.一级会员(50)" +
                        "\n2.二级会员(90)" +
                        "\n3.三级会员(150)");
                int buyLevelChoice = sc.nextInt();
                sc.nextLine();
                switch (buyLevelChoice) {
                    case 1 -> {
                        if (money >= 50) {
                            money -= 50;
                            level = 1;
                            System.out.println("一级会员购买成功！");
                        } else {
                            System.out.println("金额不足!");
                        }
                    }
                    case 2 -> {
                        if (money >= 90) {
                            money -= 90;
                            level = 2;
                            System.out.println("二级会员购买成功！");
                        } else {
                            System.out.println("金额不足!");
                        }
                    }
                    case 3 -> {
                        if (money >= 150) {
                            money -= 150;
                            level = 3;
                            System.out.println("三级会员购买成功！");
                        } else {
                            System.out.println("金额不足!");
                        }
                    }
                    default -> System.out.println("输入无效！");
                }
            }
        } else {
            System.out.println("您现在是" + level + "级会员,购买食品时会有" + (10 - level) + "折优惠");
            System.out.println("按Enter键以继续");
            sc.nextLine();
        }
        double[] mix = {level,money};
        return mix;
    }

    //购买食物
    public static double[] buyFood(double money,int[] haveFood,int level,Scanner sc) {
        int[] unitPrice = {4,6,2,4,16};
        showFood();
        System.out.print("请选择类别(您现在有" + money + "元):");
        int buyFoodChoice = sc.nextInt();
        System.out.print("请选择数量:");
        int buynumChoice = sc.nextInt();
        sc.nextLine();
        int adjustedBuyFoodChoice =buyFoodChoice -1;
        double discount = 1 - level * 0.1; // 计算折扣
        if (money >= unitPrice[adjustedBuyFoodChoice] * buynumChoice * discount) {
            haveFood[adjustedBuyFoodChoice] += buynumChoice;
            money -= unitPrice[adjustedBuyFoodChoice] * buynumChoice * discount;
            System.out.println("购买成功");
        }else {
            System.out.println("金额不足！");
        }
        double[] mix = {haveFood[0],haveFood[1],haveFood[2],haveFood[3],money};
        return mix;
    }
}
