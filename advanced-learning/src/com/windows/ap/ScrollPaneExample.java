package com.windows.ap;

import javax.swing.*;
import java.awt.*;

public class ScrollPaneExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("滚动条示例");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // 创建一个内容面板，使用 BoxLayout 布局
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // 添加多个组件（示例：多个按钮）
        for (int i = 1; i <= 20; i++) {
            contentPanel.add(new JButton("按钮 " + i));
        }
        JTextField textField1 = new JTextField();
        textField1.setMaximumSize(new Dimension(10000, 30));
        textField1.setToolTipText("这是一个提示");
        contentPanel.add(textField1);

        // 创建 JScrollPane，并将内容面板添加到其中
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 只在需要时显示垂直滚动条
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 禁止水平滚动条

        // 将 JScrollPane 添加到 JFrame
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}
