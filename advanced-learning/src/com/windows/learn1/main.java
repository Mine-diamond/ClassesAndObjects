package com.windows.learn1;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("我的窗口");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        frame.setSize(500, 500);

        JTextArea textArea1 = new JTextArea(5,5);
        textArea1.setEditable(true);
        frame.add(textArea1);

        JPasswordField passwordField = new JPasswordField(10);
        frame.add(passwordField);

        frame.add(new JButton("按钮1"));
        frame.add(new JButton("按钮2"));
        frame.add(new JButton("按钮3"));
        frame.add(new JButton("按钮4"));

        frame.setVisible(true);

    }
}
