package com.windows.ap;

import javax.swing.*;
import java.awt.*;

public class CardLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("CardLayout 示例");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建 CardLayout
        CardLayout cardLayout = new CardLayout();
        JPanel panel = new JPanel(cardLayout);

        // 创建三个不同的面板
        JPanel card1 = new JPanel();
        card1.add(new JLabel("这是第一个面板"));
        JPanel card2 = new JPanel();
        card2.add(new JLabel("这是第二个面板"));
        JPanel card3 = new JPanel();
        card3.add(new JLabel("这是第三个面板"));

        panel.add(card1, "卡片1");
        panel.add(card2, "卡片2");
        panel.add(card3, "卡片3");

        // 显示面板
        cardLayout.show(panel, "卡片2"); // 显示卡片2

        frame.add(panel);
        frame.setVisible(true);
    }
}
