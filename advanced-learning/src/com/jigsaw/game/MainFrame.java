package com.jigsaw.game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class MainFrame extends JFrame implements KeyListener{



    int[][] data = {{1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}};
    boolean ifGameOver = false;
    int gameStep = 0;

    public MainFrame() {
        this.addKeyListener(this);

        initialize();
        ruffleBlock();
        framePiant();

        setVisible(true);
    }

    public void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Jigsaw Game");
        setSize(539, 537);
        setLocationRelativeTo(null);
        //setAlwaysOnTop(true);
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("com/jigsaw/resource/puzzleicon.png")).getImage());
        setResizable(false);
        setLayout(new GridBagLayout());

        addKeyListener(new KeyAdapter() {});
    }


    public void framePiant() {

        getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(525, 500));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // 列位置
        gbc.gridy = 0; // 行位置
        gbc.anchor = GridBagConstraints.CENTER; // 设置面板在网格中的居中对齐

        add(panel, gbc);

        if(ifGameOver) {
            JLabel label3 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("com/jigsaw/resource/胜利.png")));
            label3.setBounds(150,200,200,100);
            panel.add(label3);

        }

        panel.setBackground(Color.LIGHT_GRAY);

        ArrayList<JLabel> labels = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (data[i][j] == 0) {
                    labels.add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("com/jigsaw/resource/blank.png"))));
                    labels.get(i * 4 + j).setBounds(50 + 100 * j, 50 + 100 * i, 100, 100);
                    panel.add(labels.get(i * 4 + j));
                } else {
                    labels.add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("com/jigsaw/resource/数字" + data[i][j] + ".png"))));
                    labels.get(i * 4 + j).setBounds(50 + 100 * j, 50 + 100 * i, 100, 100);
                    panel.add(labels.get(i * 4 + j));
                }

            }
        }

        JLabel labelBackground = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("com/jigsaw/resource/背景.png")));
        labelBackground.setBounds(25, 15, 450, 450);
        panel.add(labelBackground);

        JButton button1 = new JButton("帮助");
        button1.setFocusable(Boolean.FALSE);

        button1.setBounds(50, 475, 100, 20);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTip();
            }
        });
        panel.add(button1);

        JButton button2 = new JButton("重新开始");
        button2.setFocusable(Boolean.FALSE);
        button2.setBounds(205, 475, 100, 20);
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ruffleBlock();
                ifGameOver = false;
                gameStep = 0;
                framePiant();
                System.out.println("已重新开始");
            }
        });
        panel.add(button2);

        JLabel label1 = new JLabel("步数 :");
        label1.setBounds(380,475,50,20);
        panel.add(label1);

        JLabel label2 = new JLabel(String.valueOf(gameStep));
        label2.setBounds(430,475,50,20);
        panel.add(label2);

        revalidate();
        repaint();

    }



    public void ruffleBlock() {
        do{
            Random rand = new Random();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int num1 = rand.nextInt(4);
                    int num2 = rand.nextInt(4);
                    int medianNumber = data[num1][num2];
                    data[num1][num2] = data[i][j];
                    data[i][j] = medianNumber;
                }
            }

            System.out.println("游戏有解情况为："+ isSolvable());
        }while (!isSolvable());

    }

    public void restoreBlock() {
        data = new int[][]{{1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}};
    }

    public void ifGameOver(){
        int[][] rightData = {{1,2,3,4},
        {5,6,7,8},
        {9,10,11,12},
        {13,14,15,0}};
        boolean equalData = true;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (rightData[i][j] != data[i][j]) {
                    equalData = false;
                }
            }
        }

        if (equalData) {
            System.out.println("Data is equal");
            ifGameOver = true;
            System.out.println("ifGameOver is"+ifGameOver);
            framePiant();
        }
    }

    boolean CtrlPressed = false;
    boolean PPressed = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
            CtrlPressed = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_P) {
            PPressed = true;
        }

        if(CtrlPressed && PPressed) {
            System.out.println("Ctrl + P");
            restoreBlock();
            framePiant();
            ifGameOver();
            System.out.println("已复原");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        blockMove(e);

        if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
            CtrlPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_P) {
            PPressed = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE && ifGameOver == true) {
            ruffleBlock();
            ifGameOver = false;
            gameStep = 0;
            framePiant();
            System.out.println("已重新开始");
        }

    }

    private void blockMove(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("UP");
            updateGame(1);
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("DOWN");
            updateGame(2);
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("LEFT");
            updateGame(3);
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("RIGHT");
            updateGame(4);
        }
    }


    public int[] getBlankBlock(){
        int[] BlankBlockLoaction ={0, 0};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (data[i][j] == 0) {
                    BlankBlockLoaction[0] = i;
                    BlankBlockLoaction[1] = j;
                }
            }
        }
        return BlankBlockLoaction;
    }

    public void updateGame(int d){
        int[] BlankBlockLoaction = getBlankBlock();
        switch (d){
            case 1://up
                if(BlankBlockLoaction[0] > 0 && ifGameOver == false) {
                    System.out.println("已移动");
                    data[BlankBlockLoaction[0]][BlankBlockLoaction[1]] = data[BlankBlockLoaction[0]-1][BlankBlockLoaction[1]];
                    data[BlankBlockLoaction[0]-1][BlankBlockLoaction[1]] = 0;
                    gameStep++;
                    framePiant();
                    ifGameOver();
                }
                break;
            case 2://down
                if(BlankBlockLoaction[0] < 3 && ifGameOver == false) {
                    System.out.println("已移动");
                    data[BlankBlockLoaction[0]][BlankBlockLoaction[1]] = data[BlankBlockLoaction[0]+1][BlankBlockLoaction[1]];
                    data[BlankBlockLoaction[0]+1][BlankBlockLoaction[1]] = 0;
                    gameStep++;
                    framePiant();
                    ifGameOver();
                }
                break;
            case 3://left
                if(BlankBlockLoaction[1] > 0 && ifGameOver == false) {
                    System.out.println("已移动");
                    data[BlankBlockLoaction[0]][BlankBlockLoaction[1]] = data[BlankBlockLoaction[0]][BlankBlockLoaction[1]-1];
                    data[BlankBlockLoaction[0]][BlankBlockLoaction[1]-1] = 0;
                    gameStep++;
                    framePiant();
                    ifGameOver();
                }
                break;
            case 4://right
                if(BlankBlockLoaction[1] < 3 && ifGameOver == false) {
                    System.out.println("已移动");
                    data[BlankBlockLoaction[0]][BlankBlockLoaction[1]] = data[BlankBlockLoaction[0]][BlankBlockLoaction[1]+1];
                    data[BlankBlockLoaction[0]][BlankBlockLoaction[1]+1] = 0;
                    gameStep++;
                    framePiant();
                    ifGameOver();
                }
                break;
        }
    }


    public boolean isSolvable(){
        int flatData[] = new int[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                flatData[4*i+j] = data[i][j];
            }
        }
        int Zero[] = getBlankBlock();
        int thisZero = Zero[0];
        int inversions = 0;

        for (int i = 0; i < 15; i++) {
            for (int j = i; j < 16; j++) {
                if(flatData[i] > flatData[j]) {
                    inversions++;
                }
            }
        }

        return (inversions % 2 == 0) == (thisZero % 2 == 1);

    }

    public void showTip(){
        JFrame tipFrame = new JFrame("帮助");
        tipFrame.setLayout(null);
        tipFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tipFrame.setSize(815,600);
        tipFrame.setResizable(false);
        tipFrame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("com/jigsaw/resource/helpicon.png")).getImage());
        tipFrame.setLocationRelativeTo(null);
        Rectangle tipbounds = tipFrame.getBounds();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, tipbounds.width-13, tipbounds.height-23); // 设置滚动面板的位置和大小
        tipFrame.add(scrollPane);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        textArea.append(tip);
        textArea.setCaretPosition(0);

        scrollPane.setViewportView(textArea);

        tipFrame.setVisible(true);
    }

    String tip = """
             游戏操作说明：
             1.上下左右键移动空白方块。
             2.游戏胜利后，按“重新开始”按钮或者按空格键重新开始。
             
             数字华容道（15-Puzzle）是一种经典的滑块拼图游戏，目标是通过空白区域的滑动来将打乱的数字块按顺序排列。下面是该解法的详细步骤总结：
            
             1. 解法的基本思路
             本解法是基于层级（按行和列）逐步恢复数字块的顺序。整体解法按照以下顺序进行：
            
             先恢复第一层（上四个块）
             然后恢复第二层（接下来的四个块）
             最后恢复第三层和第四层（剩下的块）
             2. 第一层（1~4）的恢复
             恢复1： 将数字1移到正确位置，利用空白格进行滑动。通过不断调整空格位置，使数字1靠近目标位置，直到它处于正确位置。
             恢复2： 类似地，找到数字2的位置，重复上述过程。
             恢复3和4： 需要特别注意顺序。不要直接将数字3放到2后面，否则后续的步骤会变得复杂。步骤如下：
             首先将数字3移到4的位置。
             然后，将4放到3的下面，将3和4一起拼到正确位置。
             3. 第二层（5~8）的恢复
             恢复5和6： 直接将数字5和6按顺序放到正确位置，操作方法与恢复1和2类似。
             恢复7和8： 处理方法与恢复3和4相似，先将数字7放到8的位置，再将8放到7的下面。
             4. 第三层和第四层的恢复（9~15）
             恢复9和13： 这两块需要竖着恢复，首先将数字9放到13的位置，然后调整数字13到9的后面，最终完成两者的拼接。
             恢复10和14： 同理，先将10放到14的位置，再将14移到10的后面。
             恢复最后三块（11、12、15）： 这些最后的数字块较为简单，直接按顺序进行调整。
             5. 特殊情况处理
             在拼接过程中，可能会遇到一些特殊情况。例如：
            
             数字3和4的位置交换：如果数字4已经在3的位置，直接移动可能无法完成目标。需要通过先将数字4移远，再将3和4重新调整回正确位置。
             竖着拼接时的特殊情况：在竖着拼接9与13，10与14时，若两者已经错位，需通过先将其中一个数字移远，调整后再拼接。
             6. 最后的调整
             对于最后三个块（11、12、13），很简单就能调整好了。
             注意：拼图有可能无解，如果出现最后两个拼图数字相反，则无解，可以重新开始
             7. 总结
             此解法的关键是层级恢复的思路：
             先从上到下、从左到右逐步恢复每一行的数字，同时注意数字之间的顺序和空白格的灵活运用。
             需要特别关注数字3、4、7、8、9和13等的恢复顺序，确保它们能正确拼接。
             
             注：在本软件按下 Ctrl + P 可直接胜利。遇到无解情况可以按下此键胜利
            """;

    public static void main(String[] args) {
        new MainFrame();
    }
}
