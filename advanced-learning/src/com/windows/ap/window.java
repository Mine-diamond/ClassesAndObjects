package com.windows.ap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class window {
    public static void main(String[] args) throws InterruptedException {
        JFrame jf = new JFrame("登录界面");
        jf.setLocationRelativeTo(null); // 窗口居中
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        jf.setSize(500, 250);

        JLabel label1 = new JLabel("请输入账号：");
        jf.add(label1);

        JTextField textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(100, 20));
        jf.add(textField1);

        JLabel label2 = new JLabel("请输入密码：");
        jf.add(label2);

        JTextField textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(100, 20));
        jf.add(textField2);

        JButton button1 = new JButton("登录");
        //button1.setPreferredSize(new Dimension(100, 20));
        jf.add(button1);
        jf.setFocusable(false);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String account = textField1.getText();
                String password = textField2.getText();
                et(account, password);
            }
        });


        jf.setVisible(true);


        Timer timer = new Timer(1000, e -> {
            int newWidth = button1.getPreferredSize().width + 10;
            int newHeight = button1.getPreferredSize().height;

            // 设置按钮的新大小
            button1.setPreferredSize(new Dimension(newWidth, newHeight));

            // 重新验证布局并重绘界面
            button1.revalidate();
            button1.repaint();

            // 停止定时器，如果宽度超过一定值
            if (newWidth >= 200) {
                ((Timer) e.getSource()).stop();
            }
        });

        timer.setRepeats(true); // 定时器重复执行
        timer.start(); // 启动定时器


    }

    public static void et(String account, String password) {
        String rightAccount = "mine";
        String rightPassword = "password";

        JFrame jf = new JFrame();

        jf.setLayout(null);
        jf.setSize(250, 200);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null); // 窗口居中

        JButton button1 = new JButton("确定");
        JButton button2 = new JButton("取消");
        button1.setBounds(10, 105, 100, 30);
        button2.setBounds(125, 105, 100, 30);
        jf.getContentPane().add(button1);
        jf.getContentPane().add(button2);


        JLabel label1 = new JLabel();
        label1.setBounds(10, 10, 100, 30);

        boolean success = rightAccount.equals(account) && rightPassword.equals(password);
        if (success) {
            System.out.println("登录成功");
            label1.setText("登录成功");
        } else {
            System.out.println("登录失败");
            label1.setText("登陆失败");
        }

        jf.getContentPane().add(label1);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (success) {
                    System.exit(0);
                } else {
                    jf.setVisible(false);
                }

            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf.setVisible(false);
            }
        });

        jf.setVisible(true);
    }

}

