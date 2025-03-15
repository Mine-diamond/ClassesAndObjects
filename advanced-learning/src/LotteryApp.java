import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class LotteryApp {
    private JFrame frame;
    private JTextField minField, maxField;
    private JLabel resultLabel;
    private Timer timer;
    private int currentNumber;
    private boolean isDrawing;

    public LotteryApp() {
        frame = new JFrame("抽号软件");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout()); // 使用 GridBagLayout 布局

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // 输入最小值和最大值的文本框
        minField = new JTextField(10);
        maxField = new JTextField(10);

        // 设置标签
        JLabel minLabel = new JLabel("最小值: ");
        JLabel maxLabel = new JLabel("最大值: ");
        resultLabel = new JLabel("抽取号码：");

        // 设置号码字体和大小
        resultLabel.setFont(new Font("Serif", Font.BOLD, 50)); // 设置显示号码的字体和大小

        // 抽号按钮
        JButton drawButton = new JButton("开始抽号");
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startDrawing();
            }
        });

        // 布局组件
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(minLabel, gbc);

        gbc.gridx = 1;
        frame.add(minField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(maxLabel, gbc);

        gbc.gridx = 1;
        frame.add(maxField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        frame.add(drawButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(resultLabel, gbc);

        frame.setVisible(true);
    }

    private void startDrawing() {
        String minText = minField.getText();
        String maxText = maxField.getText();

        try {
            int min = Integer.parseInt(minText);
            int max = Integer.parseInt(maxText);

            if (min >= max) {
                JOptionPane.showMessageDialog(frame, "最小值必须小于最大值！");
                return;
            }

            // 开始抽号的逻辑
            isDrawing = true;
            resultLabel.setText("抽取号码：");
            Random random = new Random();

            // 设置一个定时器，模拟数字变动的效果
            timer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isDrawing) {
                        currentNumber = random.nextInt(max - min + 1) + min;
                        resultLabel.setText(String.valueOf(currentNumber)); // 显示变动的数字
                    }
                }
            });
            timer.start();

            // 设置定时器，1秒后停止抽号并显示结果
            new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isDrawing = false;
                    timer.stop();
                    resultLabel.setText(String.valueOf(currentNumber)); // 最终显示号码
                }
            }).start();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "请输入有效的数字！");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LotteryApp();
            }
        });
    }
}
