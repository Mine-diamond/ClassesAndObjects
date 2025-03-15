package com.jigsaw.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game2048Swing extends JFrame {
    private static final int SIZE = 4;  // 游戏板大小
    private static final int TILE_SIZE = 100;  // 每个方块的尺寸
    private static final int GAP = 10;  // 方块之间的间隔
    private int[][] board = new int[SIZE][SIZE];  // 游戏板
    private boolean gameOver = false;  // 游戏是否结束
    private int steps = 0;  // 步数统计
    private Random random = new Random();

    private GamePanel gamePanel;  // 自定义的游戏面板
    private JLabel stepsLabel;  // 步数显示标签
    private JButton restartButton;  // 重新开始按钮

    public Game2048Swing() {
        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // 窗口居中显示

        // 创建游戏面板
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(SIZE * (TILE_SIZE + GAP) + GAP, SIZE * (TILE_SIZE + GAP) + GAP));

        // 创建步数标签
        stepsLabel = new JLabel("Steps: 0", SwingConstants.CENTER);
        stepsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        stepsLabel.setForeground(Color.BLACK);

        // 创建重新开始按钮
        restartButton = new JButton("Restart");
        restartButton.setFocusable(false);
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setBackground(new Color(187, 173, 160));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.setBorder(BorderFactory.createLineBorder(new Color(143, 122, 102), 2));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();  // 重置游戏
            }
        });

        // 创建底部控制面板
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(239, 228, 218));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 将按钮和步数标签添加到控制面板
        controlPanel.add(restartButton);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // 添加间隔
        controlPanel.add(stepsLabel);

        // 设置窗口布局
        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        initializeBoard();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver) return;

                boolean moved = false;
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    moved = moveUp();
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    moved = moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    moved = moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    moved = moveRight();
                }

                if (moved) {
                    addRandomTile();
                    steps++; // 统计步数
                    stepsLabel.setText("Steps: " + steps);  // 更新步数标签
                    gamePanel.repaint();  // 仅重绘游戏面板
                    checkGameOver();
                }
            }
        });
        setFocusable(true);  // 确保窗口可以获取焦点
        pack();  // 自动调整窗口大小
        setVisible(true);
    }

    // 初始化游戏板
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }
        addRandomTile();
        addRandomTile();
        steps = 0;  // 重置步数
        stepsLabel.setText("Steps: " + steps);  // 更新步数显示
        gameOver = false;
    }

    // 重置游戏
    private void resetGame() {
        initializeBoard();  // 重新初始化游戏板
        gamePanel.repaint();  // 重绘界面
    }

    // 随机添加一个2或4
    private void addRandomTile() {
        int row, col;
        do {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE);
        } while (board[row][col] != 0);  // 找到一个空位置

        // 50% 的概率添加 4, 否则添加 2
        board[row][col] = random.nextInt(2) == 0 ? 2 : 4;
    }

    // 判断游戏是否结束
    private void checkGameOver() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) return; // 还有空格，游戏未结束
                if (i < SIZE - 1 && board[i][j] == board[i + 1][j]) return; // 可以合并
                if (j < SIZE - 1 && board[i][j] == board[i][j + 1]) return; // 可以合并
            }
        }
        gameOver = true; // 没有空格且没有可以合并的方块，游戏结束
        gamePanel.repaint();
    }

    // 向左移动
    private boolean moveLeft() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            int[] row = board[i];
            int[] newRow = slideAndMerge(row);
            if (!arraysEqual(row, newRow)) {
                moved = true;
            }
            board[i] = newRow;
        }
        return moved;
    }

    // 向右移动
    private boolean moveRight() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            int[] row = reverseArray(board[i]);
            int[] newRow = slideAndMerge(row);
            if (!arraysEqual(row, newRow)) {
                moved = true;
            }
            board[i] = reverseArray(newRow);
        }
        return moved;
    }

    // 向上移动
    private boolean moveUp() {
        boolean moved = false;
        for (int j = 0; j < SIZE; j++) {
            int[] col = getColumn(j);
            int[] newCol = slideAndMerge(col);
            if (!arraysEqual(col, newCol)) {
                moved = true;
            }
            setColumn(j, newCol);
        }
        return moved;
    }

    // 向下移动
    private boolean moveDown() {
        boolean moved = false;
        for (int j = 0; j < SIZE; j++) {
            int[] col = getColumn(j);
            col = reverseArray(col);
            int[] newCol = slideAndMerge(col);
            if (!arraysEqual(col, newCol)) {
                moved = true;
            }
            setColumn(j, reverseArray(newCol));
        }
        return moved;
    }

    // 合并和滑动方块
    private int[] slideAndMerge(int[] line) {
        int[] newLine = new int[SIZE];
        int insertPos = 0;

        for (int i = 0; i < SIZE; i++) {
            if (line[i] != 0) {
                if (insertPos > 0 && newLine[insertPos - 1] == line[i]) {
                    newLine[insertPos - 1] *= 2;
                    line[i] = 0;
                } else {
                    newLine[insertPos++] = line[i];
                }
            }
        }
        return newLine;
    }

    // 获取某列
    private int[] getColumn(int col) {
        int[] column = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            column[i] = board[i][col];
        }
        return column;
    }

    // 设置某列
    private void setColumn(int col, int[] newCol) {
        for (int i = 0; i < SIZE; i++) {
            board[i][col] = newCol[i];
        }
    }

    // 反转数组
    private int[] reverseArray(int[] array) {
        int[] reversed = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            reversed[i] = array[SIZE - 1 - i];
        }
        return reversed;
    }

    // 判断两个数组是否相等
    private boolean arraysEqual(int[] arr1, int[] arr2) {
        for (int i = 0; i < SIZE; i++) {
            if (arr1[i] != arr2[i]) return false;
        }
        return true;
    }

    // 自定义游戏面板
    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 绘制每个方块
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    int value = board[i][j];
                    drawTile(g2d, i, j, value);
                }
            }

            // 如果游戏结束，显示提示信息
            if (gameOver) {
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 40));
                String gameOverText = "Game Over!";
                FontMetrics metrics = g2d.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(gameOverText)) / 2;
                int y = getHeight() / 2;
                g2d.drawString(gameOverText, x, y);
            }
        }

        // 绘制单个方块
        private void drawTile(Graphics2D g2d, int row, int col, int value) {
            int x = col * (TILE_SIZE + GAP) + GAP;
            int y = row * (TILE_SIZE + GAP) + GAP;
            g2d.setColor(getTileColor(value));
            g2d.fillRoundRect(x, y, TILE_SIZE, TILE_SIZE, 15, 15);

            if (value != 0) {
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 40));
                String text = String.valueOf(value);
                FontMetrics metrics = g2d.getFontMetrics();
                int textWidth = metrics.stringWidth(text);
                int textHeight = metrics.getAscent();
                g2d.drawString(text, x + (TILE_SIZE - textWidth) / 2, y + (TILE_SIZE + textHeight) / 2);
            }
        }

        // 获取方块颜色
        private Color getTileColor(int value) {
            switch (value) {
                case 2: return new Color(238, 228, 218);
                case 4: return new Color(237, 224, 200);
                case 8: return new Color(242, 177, 121);
                case 16: return new Color(245, 149, 99);
                case 32: return new Color(245, 124, 95);
                case 64: return new Color(246, 94, 59);
                case 128: return new Color(237, 207, 114);
                case 256: return new Color(237, 204, 97);
                case 512: return new Color(237, 200, 80);
                case 1024: return new Color(237, 197, 63);
                case 2048: return new Color(237, 194, 46);
                default: return new Color(204, 192, 179);
            }
        }
    }

    // 启动游戏
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game2048Swing::new);
    }
}
