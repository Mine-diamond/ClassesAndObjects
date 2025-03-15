package com.windows.ap;

import javax.swing.*;
import java.awt.*;

public class GridBagFlowLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GridBagLayout 和 FlowLayout 混合");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 使用 GridBagLayout 布局
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 第一个区域使用 FlowLayout
        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.add(new JButton("按钮1"));
        panel1.add(new JButton("按钮2"));

        // 设置 GridBagConstraints 以确定位置
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(panel1, gbc);

        // 第二个区域直接使用 FlowLayout
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel2.add(new JButton("按钮3"));
        panel2.add(new JButton("按钮4"));

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(panel2, gbc);

        frame.setVisible(true);
    }
}
