package com.jiandianqi.nwe;

import com.melloware.jintellitype.JIntellitype;
import java.awt.event.InputEvent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class ClickerTool {
    private Robot robot;
    private Map<String, Runnable> hotkeyActions;

    public ClickerTool() {
        try {
            robot = new Robot();
            hotkeyActions = new HashMap<>();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        // 初始化JIntellitype并注册快捷键监听器
        JIntellitype.getInstance().addHotKeyListener(keyCode -> {
            // 查找并执行快捷键对应的操作
            Runnable actionToPerform = hotkeyActions.get("Hotkey-" + keyCode);
            if (actionToPerform != null) {
                actionToPerform.run();
            }
        });
    }

    // 模拟鼠标点击
    public void clickMouse(int x, int y, boolean leftClick) {
        robot.mouseMove(x, y);
        if (leftClick) {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } else {
            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        }
    }

    // 模拟键盘输入
    public void typeString(String text) {
        for (char c : text.toCharArray()) {
            robot.keyPress(c);
            robot.keyRelease(c);
        }
    }

    // 模拟鼠标移动
    public void moveMouseTo(int x, int y) {
        robot.mouseMove(x, y);
    }

    // 绑定快捷键到特定操作
    public void bindHotkey(int keycode, Runnable action) {
        hotkeyActions.put("Hotkey-" + keycode, action);
        // 注册快捷键，注意这里使用的 keycode 可以是 JIntellitype 中定义的快捷键常量
        JIntellitype.getInstance().registerHotKey(keycode, String.valueOf(keycode));
    }

    // 执行操作脚本
    public void executeScript() {
        // 这里你可以写脚本的具体调用逻辑
        // 示例：点击位置 (100, 100)，然后键入文本 "Hello"
        //clickMouse(100, 100, true);
        typeString("Hello");
    }

    // 启动程序时的快捷键绑定
    public static void main(String[] args) {
        ClickerTool tool = new ClickerTool();

        // 绑定快捷键 F1 执行一个脚本
        tool.bindHotkey(KeyEvent.VK_F6, () -> {
            System.out.println("Executing script for F1!");
            tool.executeScript();
        });

        // 绑定快捷键 F2 来点击位置
        tool.bindHotkey(113, () -> {
            System.out.println("Clicking mouse at (200, 200)");
            tool.clickMouse(200, 200, true);
        });

        // 示例：启动时执行一段脚本
        //tool.executeScript();
    }
}
