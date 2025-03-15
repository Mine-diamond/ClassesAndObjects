package com.other.ai;

import java.util.Scanner;

public class SexGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String characterGender = "female";
        int currentOptionIndex = 0;
        String[] options = {"我爱你", "我想和你做爱", "你能让我舒服吗?"};
        System.out.println("欢迎来到纯文字色情游戏！您将扮演一位" + characterGender + "角色。");

        while (true) {
            System.out.println("当前选项：" + options[currentOptionIndex]);
            System.out.print("请输入您的选择（q退出，其他选项进入下一回合）：");
            String userChoice = scanner.nextLine().toLowerCase();

            if ("q".equals(userChoice)) {
                break;
            } else if ("i".equals(userChoice) || "love".equals(userChoice)) {
                currentOptionIndex++;
                if (currentOptionIndex >= options.length) {
                    currentOptionIndex = 0;
                }
            } else if ("f".equals(userChoice) || "follow".equals(userChoice)) {
                currentOptionIndex--;
                if (currentOptionIndex < 0) {
                    currentOptionIndex = options.length - 1;
                }
            } else if ("m".equals(userChoice) || "make".equals(userChoice)) {
                System.out.println("您选择了" + options[currentOptionIndex] + ".");

                // 添加更多的对话和情节发展
                System.out.println("他听到您的声音，开始有些冲动。他靠近您，轻轻吻了您的嘴唇...");
                currentOptionIndex++;  // 进入下一个回合
            } else {
                System.out.println("无效的选择，请输入正确的选项或q退出。");
            }
        }

        System.out.println("游戏结束！感谢您参与我们的色情文字游戏。再见！");
    }
}
