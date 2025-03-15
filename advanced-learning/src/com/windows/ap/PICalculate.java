package com.windows.ap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;

public class PICalculate {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("PIC Calculate");
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setSize(420, 300);
        frame.setLocationRelativeTo(null);
        //frame.setResizable(false);

        JLabel label1 = new JLabel();
        label1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        label1.setText("步数：");
        JLabel label2 = new JLabel();
        label2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        label2.setText("PI值：");
        JLabel label3 = new JLabel();
        label3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        label3.setText("限制步数：");

        JTextField textField1 = new JTextField();
        textField1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        textField1.setEditable(false);
        textField1.setPreferredSize(new Dimension(90, 25));
        JTextArea textArea1 = new JTextArea(5,20);
        textArea1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        textArea1.setEditable(false);
        textArea1.setLineWrap(true); // 启用自动换行
        textArea1.setPreferredSize(new Dimension(150, 50));
        JTextField textField2 = new JTextField();
        textField2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        textField2.setPreferredSize(new Dimension(100, 25));
        JButton button1 = new JButton("开始");
        button1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        button1.setFocusable(false);
        frame.getContentPane().add(label3);
        frame.getContentPane().add(textField2);
        frame.getContentPane().add(label1);
        frame.getContentPane().add(textField1);
        frame.getContentPane().add(label2);
        frame.getContentPane().add(textArea1);
        frame.getContentPane().add(button1);
        frame.setVisible(true);

        InfinitePiCalculator calculator = new InfinitePiCalculator();
        System.out.println("创建对象成功");



        TimerManange.Build(new TimerManange.Build(){
            @Override
            public void build(Timer timeS) {
                 timeS = new Timer(1000, new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                         calculator.calculateNextTerm();
                         textField1.setText(String.valueOf(calculator.getStep()));
                         int number = 0;
                         try {
                             String numStr =  textField2.getText();
                             number = Integer.parseInt(numStr);
                         } catch (NumberFormatException e1) {
                             System.out.println("Invalid input. The string is not a valid integer.");
                         }
                         if(true) {
                             textArea1.setText(String.valueOf(calculator.getPiValue()));
                         }else {

                         }

                         frame.repaint();

                     }
                 });
                 timeS.setRepeats(true);
            }
        });


        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(TimerManange.timer.isRunning()) {
                    TimerManange.timer.stop();
                    button1.setText("恢复");
                }else {
                    TimerManange.timer.start();
                    button1.setText("停止");
                }
            }
        });
    }
    public static void stopAndStart(){

    }

}

class InfinitePiCalculator {

    // 使用 BigDecimal 来存储高精度的结果
    private BigDecimal a;
    private BigDecimal b;
    private BigDecimal t;
    private BigDecimal p;

    private int step;

    public InfinitePiCalculator() {
        // 初始化高斯-勒让德算法的变量
        this.a = BigDecimal.ONE;
        this.b = BigDecimal.ONE.divide(new BigDecimal(Math.sqrt(2)), MathContext.DECIMAL128);
        this.t = new BigDecimal("0.25");
        this.p = BigDecimal.ONE;
        this.step = 0;
    }

    // 计算圆周率的下一个步骤
    public void calculateNextTerm() {
        BigDecimal aNext = (a.add(b)).divide(new BigDecimal("2"), MathContext.DECIMAL128);
        BigDecimal bNext = (a.multiply(b)).sqrt(MathContext.DECIMAL128);
        BigDecimal tNext = t.subtract(p.multiply(a.subtract(aNext).pow(2)));
        BigDecimal pNext = p.multiply(new BigDecimal("2"));

        // 更新变量
        a = aNext;
        b = bNext;
        t = tNext;
        p = pNext;

        // 增加步数
        step++;
    }

    // 获取当前计算的圆周率值
    public BigDecimal getPiValue() {
        // 使用高斯-勒让德算法公式来计算圆周率的当前值
        return (a.add(b)).pow(2).divide(t.multiply(new BigDecimal("4")), MathContext.DECIMAL128);
    }

    // 获取当前计算的步数
    public int getStep() {
        return step;
    }

    // 获取指定精度的小数位数的圆周率
    public String getPiValueWithScale(int scale) {
        return getPiValue().setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

}

class TimerManange{
    static Timer timer;

    public static void Build(Build thisbuild) {
        thisbuild.build(TimerManange.timer);
    }

    static interface Build {
        void build(Timer timer);
    }
}