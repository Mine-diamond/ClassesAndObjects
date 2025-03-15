package com.block.learn1;

import javax.swing.*;
import java.awt.event.*;

public class AnonymousInnerClassExample {

    public static void main(String[] args) {
        // 创建 JFrame 窗口
        JFrame frame = new JFrame("匿名内部类示例");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建 JButton
        JButton button = new JButton("点击我");

        // 使用匿名内部类实现 ActionListener 接口
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("按钮被点击了！");
            }
        });

        // 将按钮添加到窗口中
        frame.getContentPane().add(button);

        // 显示窗口
        frame.setVisible(true);
    }
}
