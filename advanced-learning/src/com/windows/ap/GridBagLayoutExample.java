package com.windows.ap;

import javax.swing.*;
import java.awt.*;

public class GridBagLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GridBagLayout 示例");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建 GridBagLayout 布局管理器
        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();

        // 按钮 1，占据位置 (0,0)
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JButton("按钮1"), gbc);

        // 按钮 2，占据位置 (1,0)
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(new JButton("按钮2"), gbc);

        // 按钮 3，占据位置 (0,1)，并且水平和垂直方向都占据 2 个单元格
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(new JTextField("文本框"), gbc);

        frame.setVisible(true);
    }
}
