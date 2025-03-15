import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Minesweeper extends JFrame {
    private int rows = 18;  // 网格行数
    private int cols = 30;  // 网格列数
    private int mineCount = 99;  // 地雷数量

    private JButton[][] buttons;  // 网格按钮
    private int[][] grid;  // 游戏逻辑网格 (-1 表示地雷，其他表示周围地雷数)
    private JLabel mineLabel;  // 显示剩余地雷数
    private JLabel timerLabel;  // 显示计时器
    private Timer timer;  // 计时器
    private int elapsedTime = 0;  // 经过的时间（秒）
    private int remainingMines;  // 剩余地雷数
    private boolean gameActive = true;  // 游戏状态

    public Minesweeper() {
        setTitle("扫雷");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 830);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        initTopPanel();  // 初始化顶部状态栏
        initGrid();  // 初始化网格区域
        generateGrid();  // 生成地雷和网格数据

        setVisible(true);
        startTimer();  // 开始计时
    }

    // 初始化顶部状态栏
    private void initTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 3));

        // 剩余地雷数标签
        remainingMines = mineCount;
        mineLabel = new JLabel("剩余地雷: " + remainingMines, JLabel.CENTER);
        topPanel.add(mineLabel);

        // 重置按钮
        JButton resetButton = new JButton("重置");
        resetButton.setFocusable(false);
        resetButton.addActionListener(e -> resetGame());
        topPanel.add(resetButton);

        // 计时器标签
        timerLabel = new JLabel("时间: 0 秒", JLabel.CENTER);
        topPanel.add(timerLabel);

        // 添加到主窗口
        add(topPanel, BorderLayout.NORTH);
    }

    // 初始化网格区域
    private void initGrid() {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, cols));

        buttons = new JButton[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(50, 50));
                buttons[i][j].addMouseListener(new CellClickListener(i, j));
                buttons[i][j].setFocusable(false);
                gridPanel.add(buttons[i][j]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
    }

    // 生成网格和地雷
    private void generateGrid() {
        grid = new int[rows][cols];
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < mineCount) {
            int r = random.nextInt(rows);
            int c = random.nextInt(cols);
            if (grid[r][c] != -1) {
                grid[r][c] = -1;  // 放置地雷
                minesPlaced++;
                updateSurroundingCells(r, c);  // 更新周围的格子数字
            }
        }
    }

    // 更新地雷周围的数字
    private void updateSurroundingCells(int r, int c) {
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < cols && grid[i][j] != -1) {
                    grid[i][j]++;
                }
            }
        }
    }

    // 重置游戏
    private void resetGame() {
        elapsedTime = 0;
        remainingMines = mineCount;
        mineLabel.setText("剩余地雷: " + remainingMines);
        timerLabel.setText("时间: 0 秒");
        gameActive = true;

        // 停止计时器并重新开始
        if (timer != null) {
            timer.stop();
        }
        startTimer();

        // 清空按钮并重新生成网格
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(null);
            }
        }
        generateGrid();
    }

    // 开始计时器
    private void startTimer() {
        timer = new Timer(1000, e -> {
            elapsedTime++;
            timerLabel.setText("时间: " + elapsedTime + " 秒");
        });
        timer.start();
    }

    // 停止计时器
    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    // 单元格点击事件监听器
    private class CellClickListener extends MouseAdapter {
        private int x, y;

        public CellClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!gameActive) return;

            if (SwingUtilities.isLeftMouseButton(e)) {
                onLeftClick(x, y);
            } else if (SwingUtilities.isRightMouseButton(e)) {
                onRightClick(x, y);
            }
        }
    }

    // 左键点击事件
    private void onLeftClick(int x, int y) {
        if (!buttons[x][y].isEnabled()) {
            return;  // 已经打开的格子不处理
        }

        if (grid[x][y] == -1) {
            buttons[x][y].setText("雷");
            buttons[x][y].setBackground(Color.RED);
            gameOver();
        } else if (grid[x][y] == 0) {
            revealEmptyCells(x, y);
        } else {
            buttons[x][y].setText(String.valueOf(grid[x][y]));
            buttons[x][y].setEnabled(false);
        }

        checkWin();
    }

    // 右键点击事件
    private void onRightClick(int x, int y) {
        if (buttons[x][y].isEnabled()) {
            if (buttons[x][y].getText().equals("旗")) {
                buttons[x][y].setText("");
                remainingMines++;
            } else {
                buttons[x][y].setText("旗");
                remainingMines--;
            }
            mineLabel.setText("剩余地雷: " + remainingMines);
        }
    }

    // 展开空白区域
    private void revealEmptyCells(int x, int y) {
        if (x < 0 || x >= rows || y < 0 || y >= cols || !buttons[x][y].isEnabled()) {
            return;
        }

        buttons[x][y].setEnabled(false);
        if (grid[x][y] > 0) {
            buttons[x][y].setText(String.valueOf(grid[x][y]));
            return;
        }

        // 递归展开周围的空白区域
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                revealEmptyCells(i, j);
            }
        }
    }

    // 游戏结束
    private void gameOver() {
        gameActive = false;
        stopTimer();
        JOptionPane.showMessageDialog(this, "游戏结束！你踩到了地雷！");

        // 显示所有地雷
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == -1) {
                    buttons[i][j].setText("雷");
                    buttons[i][j].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    // 检查是否胜利
    private void checkWin() {
        boolean win = true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != -1 && buttons[i][j].isEnabled()) {
                    win = false;
                    break;
                }
            }
        }

        if (win) {
            gameActive = false;
            stopTimer();
            JOptionPane.showMessageDialog(this, "恭喜你！你赢了！");
        }
    }

    // 主方法
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Minesweeper::new);
    }
}
