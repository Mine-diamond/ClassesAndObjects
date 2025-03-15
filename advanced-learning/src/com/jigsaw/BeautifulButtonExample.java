package com.jigsaw;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class BeautifulButtonExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("美化按钮示例");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        JButton button = new JButton("美化按钮");
        button.setPreferredSize(new Dimension(150, 50)); // 设置按钮大小
        button.setFont(new Font("Arial", Font.BOLD, 16)); // 设置字体
        button.setForeground(Color.BLACK);               // 设置文字颜色
        button.setBackground(Color.WHITE);               // 设置背景颜色
        button.setContentAreaFilled(false);              // 禁用默认填充
        button.setOpaque(false);                         // 使背景透明

        // 自定义圆角边框
        button.setBorder(new RoundedBorder(20, Color.RED));

        // 自定义按钮UI绘制
        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 绘制背景
                g2.setColor(button.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);

                // 绘制边框
                g2.setColor(Color.RED);
                g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 20, 20);

                super.paint(g, c);
            }
        });

        frame.add(button);
        frame.setVisible(true);
    }
}

// 自定义圆角边框类
class RoundedBorder extends javax.swing.border.AbstractBorder {
    private int radius;
    private Color borderColor;

    public RoundedBorder(int radius, Color borderColor) {
        this.radius = radius;
        this.borderColor = borderColor;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = insets.top = insets.bottom = radius + 1;
        return insets;
    }
}
