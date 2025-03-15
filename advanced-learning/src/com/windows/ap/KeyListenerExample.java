package com.windows.ap;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListenerExample {
    private static boolean ctrlPressed = false; // 是否按下 Ctrl 键
    private static boolean sPressed = false;   // 是否按下 S 键

    public static void main(String[] args) {
        JFrame frame = new JFrame("组合键监听示例");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // 添加 KeyListener
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // 检测按键
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    sPressed = true;
                }

                // 检测组合键 Ctrl + S
                if (ctrlPressed && sPressed) {
                    System.out.println("检测到 Ctrl + S");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // 释放按键时重置状态
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlPressed = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    sPressed = false;
                }
            }
        });

        frame.setVisible(true);
    }
}

