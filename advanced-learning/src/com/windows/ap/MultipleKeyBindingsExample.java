package com.windows.ap;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MultipleKeyBindingsExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("多组合键绑定示例");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JButton button = new JButton("按下快捷键");
        frame.add(button);

        // 获取 InputMap 和 ActionMap
        InputMap inputMap = button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = button.getActionMap();



        // 绑定 Ctrl + S
        inputMap.put(KeyStroke.getKeyStroke("ctrl S"), "saveAction");
        actionMap.put("saveAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("检测到 Ctrl + S 组合键");
            }
        });

        // 绑定 Alt + Shift + X
        inputMap.put(KeyStroke.getKeyStroke("alt shift X"), "customAction");
        actionMap.put("customAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("检测到 Alt + Shift + X 组合键");
            }
        });

        frame.setVisible(true);
    }
}

