package com.a2048.a2048;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Game2048 extends JFrame {
    // 棋盘尺寸（行数/列数）
    private int boardSize;
    // 游戏棋盘数据，0 表示空格
    private int[][] board;
    // 撤回栈，保存历史游戏状态（棋盘和分数）
    private Stack<GameState> undoStack = new Stack<>();

    // 新增：新方块生成配置
    private NewTileConfig tileConfig;

    // UI 组件
    private GamePanel gamePanel;
    private RoundedButton undoButton;
    private RoundedButton newGameButton;
    private RoundedButton changeSizeButton; // 改变棋盘大小按钮
    private RoundedButton saveButton;       // 存档按钮
    private RoundedButton loadButton;       // 读档按钮
    private RoundedButton configButton;     // 配置新方块生成按钮
    private RoundedButton resetConfigButton;// 重置新方块生成配置按钮
    private JLabel scoreLabel;              // 显示分数

    // -----------------------------
    // 新 tile 出现的缩放动画相关
    // -----------------------------
    private List<AnimationTile> animTiles = new ArrayList<>();
    private Timer newTileAnimTimer;  // 驱动新 tile 缩放动画

    // -----------------------------
    // 方块滑动动画相关（移动时触发）
    // -----------------------------
    private List<SlideAnimation> slideAnimations = new ArrayList<>();
    private Timer slideAnimTimer;    // 驱动滑动动画
    private boolean animatingMove = false; // 正在动画时禁止新的移动

    // -----------------------------
    // 合并动画相关（合并后新方块的弹出效果）
    // -----------------------------
    private List<MergeAnimation> mergeAnimations = new ArrayList<>();
    private Timer mergeAnimTimer;

    // -----------------------------
    // 积分系统：累计合并后生成新方块的数值
    // -----------------------------
    private int score = 0;

    // ============================================================
    // 内部类：保存游戏状态（棋盘和分数），用于撤回和存档
    // ============================================================
    private static class GameState implements Serializable {
        private static final long serialVersionUID = 1L;
        int[][] board;
        int score;
        GameState(int[][] board, int score) {
            this.board = new int[board.length][board[0].length];
            for (int i = 0; i < board.length; i++){
                System.arraycopy(board[i], 0, this.board[i], 0, board[i].length);
            }
            this.score = score;
        }
    }

    // ============================================================
    // 内部类：用于存档的游戏数据，包括新方块配置
    // ============================================================
    private static class SavedGame implements Serializable {
        private static final long serialVersionUID = 1L;
        int[][] board;
        int score;
        NewTileConfig tileConfig;
        SavedGame(int[][] board, int score, NewTileConfig tileConfig) {
            this.board = new int[board.length][board[0].length];
            for (int i = 0; i < board.length; i++){
                System.arraycopy(board[i], 0, this.board[i], 0, board[i].length);
            }
            this.score = score;
            // 深拷贝配置
            this.tileConfig = new NewTileConfig();
            for (NewTileOption option : tileConfig.options) {
                this.tileConfig.options.add(new NewTileOption(option.value, option.weight, option.minThreshold));
            }
        }
    }

    // ============================================================
    // 内部类：新方块生成配置 —— 每个选项包括数值、权重、以及出现条件（仅当场上最大方块>=此值时才出现）
    // ============================================================
    public static class NewTileOption implements Serializable {
        private static final long serialVersionUID = 1L;
        int value;
        int weight;
        int minThreshold;
        public NewTileOption(int value, int weight, int minThreshold) {
            this.value = value;
            this.weight = weight;
            this.minThreshold = minThreshold;
        }
        @Override
        public String toString() {
            return value + "," + weight + "," + minThreshold;
        }
    }
    public static class NewTileConfig implements Serializable {
        private static final long serialVersionUID = 1L;
        List<NewTileOption> options = new ArrayList<>();
    }

    // ============================================================
    // 内部类：新生成 tile 的缩放动画数据
    // ============================================================
    class AnimationTile {
        int row, col;
        float progress; // 从 0 到 1
        AnimationTile(int row, int col) {
            this.row = row;
            this.col = col;
            this.progress = 0f;
        }
    }

    // ============================================================
    // 内部类：滑动动画数据
    // ============================================================
    class SlideAnimation {
        int fromRow, fromCol, toRow, toCol, value;
        float progress;  // 0 ~ 1，表示动画进度
        boolean finished; // 动画进度到1后延迟一帧再移除
        SlideAnimation(int fromRow, int fromCol, int toRow, int toCol, int value) {
            this.fromRow = fromRow;
            this.fromCol = fromCol;
            this.toRow = toRow;
            this.toCol = toCol;
            this.value = value;
            this.progress = 0f;
            this.finished = false;
        }
    }

    // ============================================================
    // 内部类：合并动画数据，用于弹出新合并后的方块
    // ============================================================
    class MergeAnimation {
        int row, col, value;
        float progress;  // 0 ~ 1（0时scale=1.3, 1时scale=1.0）
        MergeAnimation(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
            this.progress = 0f;
        }
    }

    // ============================================================
    // 辅助类：用于移动计算时记录非零方块信息（横向或纵向）
    // ============================================================
    class TileData {
        int pos, value;
        TileData(int pos, int value) {
            this.pos = pos;
            this.value = value;
        }
    }
    // ============================================================
    // 辅助类：用于计算每个方块的目标位置及信息
    // ============================================================
    class AnimatedTile {
        int originalPos, targetPos, value;
        boolean merged;
        AnimatedTile(int originalPos, int targetPos, int value, boolean merged) {
            this.originalPos = originalPos;
            this.targetPos = targetPos;
            this.value = value;
            this.merged = merged;
        }
    }

    // 构造方法
    public Game2048(int size) {
        this.boardSize = size;
        board = new int[boardSize][boardSize];
        // 初始化配置为默认配置
        tileConfig = new NewTileConfig();
        tileConfig.options.add(new NewTileOption(2, 90, 0));
        tileConfig.options.add(new NewTileOption(4, 10, 0));
        initUI();
        newGame();
    }

    /**
     * 初始化界面：
     * 1. 增加顶部 Score 面板；
     * 2. 中间为棋盘显示区；
     * 3. 底部按钮面板采用 3行3列 GridLayout 布局，保证所有按钮均显示。
     */
    private void initUI() {
        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 将窗口宽度调整为500，确保左右空间足够
        setSize(500, 680);
        setLocationRelativeTo(null);
        //setResizable(false);
        setLayout(new BorderLayout());

        // Score 面板（放在窗口上方）
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(new Color(0xbbada0));
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        scoreLabel.setForeground(new Color(0x776e65));
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(new Color(0xeee4da));
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        scorePanel.add(scoreLabel);
        add(scorePanel, BorderLayout.NORTH);

        // 游戏面板
        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        // 控制按钮面板，采用3行3列布局
        JPanel controlPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        controlPanel.setBackground(new Color(0xbbada0));
        undoButton = new RoundedButton("Undo");
        newGameButton = new RoundedButton("New Game");
        changeSizeButton = new RoundedButton("Change Size");
        saveButton = new RoundedButton("Save");
        loadButton = new RoundedButton("Load");
        configButton = new RoundedButton("Configure");
        resetConfigButton = new RoundedButton("Reset Config");
        controlPanel.add(undoButton);
        controlPanel.add(newGameButton);
        controlPanel.add(changeSizeButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(configButton);
        controlPanel.add(resetConfigButton);
        // 剩余两个单元格留空
        controlPanel.add(new JLabel());
        controlPanel.add(new JLabel());
        add(controlPanel, BorderLayout.SOUTH);

        // 撤回按钮监听
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoMove();
                Game2048.this.requestFocusInWindow();
            }
        });
        // 新游戏按钮监听
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
                Game2048.this.requestFocusInWindow();
            }
        });
        // 改变棋盘大小按钮监听
        changeSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("请输入新的棋盘大小（例如输入4表示4x4棋盘）：");
                try {
                    int newSize = Integer.parseInt(input);
                    if (newSize < 2) {
                        newSize = 4;
                    }
                    // 以新尺寸重新启动游戏
                    Game2048 newGame = new Game2048(newSize);
                    newGame.setVisible(true);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Game2048.this, "输入无效！");
                }
                Game2048.this.requestFocusInWindow();
            }
        });
        // 存档按钮监听
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
                Game2048.this.requestFocusInWindow();
            }
        });
        // 读档按钮监听
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
                Game2048.this.requestFocusInWindow();
            }
        });
        // 配置按钮监听：弹出对话框编辑新方块生成配置
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 将当前配置转换为文本，每行"值,权重,最小条件"
                StringBuilder sb = new StringBuilder();
                for (NewTileOption option : tileConfig.options) {
                    sb.append(option.toString()).append("\n");
                }
                JTextArea textArea = new JTextArea(sb.toString(), 5, 20);
                JScrollPane scrollPane = new JScrollPane(textArea);
                int option = JOptionPane.showConfirmDialog(Game2048.this, scrollPane,
                        "编辑新方块生成配置（每行格式：值,权重,最小条件）", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String inputText = textArea.getText().trim();
                    String[] lines = inputText.split("\\n");
                    List<NewTileOption> newOptions = new ArrayList<>();
                    try {
                        for (String line : lines) {
                            line = line.trim();
                            if (line.isEmpty()) continue;
                            String[] parts = line.split(",");
                            if (parts.length != 3) throw new Exception("每行必须包含3个数字，用逗号分隔");
                            int value = Integer.parseInt(parts[0].trim());
                            int weight = Integer.parseInt(parts[1].trim());
                            int minThreshold = Integer.parseInt(parts[2].trim());
                            newOptions.add(new NewTileOption(value, weight, minThreshold));
                        }
                        if (newOptions.isEmpty()) {
                            throw new Exception("配置不能为空");
                        }
                        tileConfig.options = newOptions;
                        JOptionPane.showMessageDialog(Game2048.this, "配置已更新！");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Game2048.this, "配置格式错误：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
                Game2048.this.requestFocusInWindow();
            }
        });
        // 重置配置按钮监听：重置为默认配置
        resetConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tileConfig.options.clear();
                tileConfig.options.add(new NewTileOption(2, 90, 0));
                tileConfig.options.add(new NewTileOption(4, 10, 0));
                JOptionPane.showMessageDialog(Game2048.this, "配置已重置为默认！");
                Game2048.this.requestFocusInWindow();
            }
        });

        // 键盘监听：响应方向键（动画期间忽略）
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (animatingMove) return;
                int key = e.getKeyCode();
                boolean validKey = (key == KeyEvent.VK_LEFT ||
                        key == KeyEvent.VK_RIGHT ||
                        key == KeyEvent.VK_UP ||
                        key == KeyEvent.VK_DOWN);
                if (!validKey) return;
                switch(key) {
                    case KeyEvent.VK_LEFT:
                        moveLeftAnimated();
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRightAnimated();
                        break;
                    case KeyEvent.VK_UP:
                        moveUpAnimated();
                        break;
                    case KeyEvent.VK_DOWN:
                        moveDownAnimated();
                        break;
                }
                gamePanel.repaint();
                if (isGameOver()){
                    JOptionPane.showMessageDialog(null, "Game Over!\nFinal Score: " + score);
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();
    }

    /**
     * 更新分数显示
     */
    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    /**
     * 开始新游戏：清空棋盘、撤回记录、动画，并将分数清零
     */
    private void newGame() {
        board = new int[boardSize][boardSize];
        undoStack.clear();
        animTiles.clear();
        slideAnimations.clear();
        mergeAnimations.clear();
        if (newTileAnimTimer != null && newTileAnimTimer.isRunning()) {
            newTileAnimTimer.stop();
        }
        if (slideAnimTimer != null && slideAnimTimer.isRunning()) {
            slideAnimTimer.stop();
        }
        if (mergeAnimTimer != null && mergeAnimTimer.isRunning()) {
            mergeAnimTimer.stop();
        }
        animatingMove = false;
        score = 0;
        updateScoreLabel();
        spawnRandomTile();
        spawnRandomTile();
        gamePanel.repaint();
    }

    /**
     * 撤回上一步，同时恢复分数
     */
    private void undoMove() {
        if (!undoStack.isEmpty() && !animatingMove) {
            GameState state = undoStack.pop();
            board = state.board;
            score = state.score;
            updateScoreLabel();
            animTiles.clear();
            slideAnimations.clear();
            mergeAnimations.clear();
            if (newTileAnimTimer != null && newTileAnimTimer.isRunning()) {
                newTileAnimTimer.stop();
            }
            if (slideAnimTimer != null && slideAnimTimer.isRunning()) {
                slideAnimTimer.stop();
            }
            if (mergeAnimTimer != null && mergeAnimTimer.isRunning()) {
                mergeAnimTimer.stop();
            }
            animatingMove = false;
            gamePanel.repaint();
        }
    }

    /**
     * 保存当前状态（棋盘和分数）到撤回栈中
     */
    private void saveState() {
        undoStack.push(new GameState(board, score));
    }

    /**
     * 存档：将当前棋盘、分数和新方块配置保存到文件 "game_save.dat" 中
     */
    private void saveGame() {
        SavedGame sg = new SavedGame(board, score, tileConfig);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game_save.dat"))) {
            oos.writeObject(sg);
            JOptionPane.showMessageDialog(this, "游戏已成功存档！");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "存档失败：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 读档：从文件 "game_save.dat" 中加载棋盘、分数和新方块配置，并刷新界面
     * 读档时检测存档的棋盘尺寸是否与当前 boardSize 相符，
     * 如果不符，则弹出提示，询问是否以存档尺寸重新启动游戏并加载存档。
     */
    private void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game_save.dat"))) {
            SavedGame sg = (SavedGame) ois.readObject();
            if (sg.board.length != boardSize || sg.board[0].length != boardSize) {
                int option = JOptionPane.showConfirmDialog(this, "存档的棋盘尺寸为 " + sg.board.length + "x" + sg.board[0].length +
                        "，当前棋盘尺寸为 " + boardSize + "x" + boardSize +
                        "，是否以存档尺寸重新启动游戏并加载存档？", "尺寸不匹配", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    boardSize = sg.board.length;
                    Game2048 newGame = new Game2048(boardSize);
                    newGame.board = sg.board;
                    newGame.score = sg.score;
                    newGame.tileConfig = sg.tileConfig;
                    newGame.updateScoreLabel();
                    newGame.undoStack.clear();
                    newGame.setVisible(true);
                    dispose();
                }
                return;
            }
            board = sg.board;
            score = sg.score;
            tileConfig = sg.tileConfig;
            updateScoreLabel();
            animTiles.clear();
            slideAnimations.clear();
            mergeAnimations.clear();
            undoStack.clear();
            gamePanel.repaint();
            JOptionPane.showMessageDialog(this, "游戏已成功读档！");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "读档失败：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 随机生成一个新数字，并启动新 tile 的缩放动画。
     * 根据 tileConfig 配置，先计算当前棋盘上最大值，
     * 然后过滤出满足条件的选项，并按照权重随机选择生成的数字。
     */
    private void spawnRandomTile() {
        List<Point> emptyCells = new ArrayList<>();
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                if (board[i][j] == 0){
                    emptyCells.add(new Point(i, j));
                }
            }
        }
        if(emptyCells.isEmpty()) return;
        Point p = emptyCells.get(new Random().nextInt(emptyCells.size()));
        // 计算当前棋盘上最大值
        int currentMax = 0;
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                currentMax = Math.max(currentMax, board[i][j]);
            }
        }
        // 筛选满足条件的选项：minThreshold <= currentMax
        List<NewTileOption> candidates = new ArrayList<>();
        for (NewTileOption option : tileConfig.options) {
            if (option.minThreshold <= currentMax) {
                candidates.add(option);
            }
        }
        if (candidates.isEmpty()) {
            // 如果没有符合条件的选项，则使用所有minThreshold==0的选项
            for (NewTileOption option : tileConfig.options) {
                if (option.minThreshold == 0) {
                    candidates.add(option);
                }
            }
        }
        // 按权重随机选择
        int totalWeight = 0;
        for (NewTileOption option : candidates) {
            totalWeight += option.weight;
        }
        int r = new Random().nextInt(totalWeight);
        int sum = 0;
        int newValue = 2; // 默认
        for (NewTileOption option : candidates) {
            sum += option.weight;
            if (r < sum) {
                newValue = option.value;
                break;
            }
        }
        board[p.x][p.y] = newValue;
        animTiles.add(new AnimationTile(p.x, p.y));
        startNewTileAnimTimer();
    }

    /**
     * 启动新 tile 缩放动画定时器（间隔 10 毫秒，每帧进度增加 0.1）
     */
    private void startNewTileAnimTimer() {
        if (newTileAnimTimer == null || !newTileAnimTimer.isRunning()) {
            newTileAnimTimer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean updated = false;
                    Iterator<AnimationTile> it = animTiles.iterator();
                    while(it.hasNext()){
                        AnimationTile at = it.next();
                        at.progress += 0.1f;
                        if(at.progress >= 1f){
                            at.progress = 1f;
                            it.remove();
                        }
                        updated = true;
                    }
                    if(updated){
                        gamePanel.repaint();
                    } else {
                        newTileAnimTimer.stop();
                    }
                }
            });
            newTileAnimTimer.start();
        }
    }

    /**
     * 启动合并动画定时器（间隔 10 毫秒，每帧进度增加 0.04）
     */
    private void startMergeAnimTimer() {
        if (mergeAnimTimer == null || !mergeAnimTimer.isRunning()) {
            mergeAnimTimer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean updated = false;
                    Iterator<MergeAnimation> it = mergeAnimations.iterator();
                    while(it.hasNext()){
                        MergeAnimation ma = it.next();
                        ma.progress += 0.04f;
                        if (ma.progress >= 1f) {
                            it.remove();
                        }
                        updated = true;
                    }
                    if (updated) {
                        gamePanel.repaint();
                    } else {
                        mergeAnimTimer.stop();
                    }
                }
            });
            mergeAnimTimer.start();
        }
    }

    // =====================================================
    // 以下实现带滑动动画的移动操作（左右上下）
    // =====================================================

    /**
     * 向左移动（带滑动动画）
     */
    private void moveLeftAnimated() {
        if (animatingMove) return;
        saveState();
        int[][] old = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++){
            System.arraycopy(board[i], 0, old[i], 0, boardSize);
        }
        int[][] newBoard = new int[boardSize][boardSize];
        List<SlideAnimation> animations = new ArrayList<>();
        // 按行处理
        for (int i = 0; i < boardSize; i++){
            List<TileData> rowTiles = new ArrayList<>();
            for (int j = 0; j < boardSize; j++){
                if (old[i][j] != 0){
                    rowTiles.add(new TileData(j, old[i][j]));
                }
            }
            List<AnimatedTile> result = new ArrayList<>();
            for (TileData t : rowTiles) {
                if (!result.isEmpty() && result.get(result.size()-1).value == t.value && !result.get(result.size()-1).merged) {
                    AnimatedTile last = result.get(result.size()-1);
                    last.value *= 2;
                    last.merged = true;
                    score += last.value;
                    updateScoreLabel();
                    animations.add(new SlideAnimation(i, t.pos, i, last.targetPos, t.value));
                    mergeAnimations.add(new MergeAnimation(i, last.targetPos, last.value));
                    startMergeAnimTimer();
                } else {
                    int target = result.size();
                    result.add(new AnimatedTile(t.pos, target, t.value, false));
                    if (t.pos != target) {
                        animations.add(new SlideAnimation(i, t.pos, i, target, t.value));
                    }
                }
            }
            for (int j = 0; j < result.size(); j++){
                newBoard[i][j] = result.get(j).value;
            }
        }
        boolean moved = false;
        for (int i = 0; i < boardSize; i++){
            if (!Arrays.equals(old[i], newBoard[i])){
                moved = true;
                break;
            }
        }
        if (!moved) {
            if (!undoStack.isEmpty()) undoStack.pop();
            return;
        }
        board = newBoard;
        slideAnimations = animations;
        animatingMove = true;
        slideAnimTimer = new Timer(10, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean updated = false;
                Iterator<SlideAnimation> it = slideAnimations.iterator();
                while(it.hasNext()){
                    SlideAnimation anim = it.next();
                    if (anim.progress < 1f) {
                        anim.progress += 0.1f;
                        if (anim.progress >= 1f) {
                            anim.progress = 1f;
                            if (!anim.finished) {
                                anim.finished = true;
                            } else {
                                it.remove();
                            }
                        }
                        updated = true;
                    } else {
                        if (!anim.finished) {
                            anim.finished = true;
                            updated = true;
                        } else {
                            it.remove();
                        }
                    }
                }
                if (updated) {
                    gamePanel.repaint();
                } else {
                    slideAnimTimer.stop();
                    animatingMove = false;
                    spawnRandomTile();
                    gamePanel.repaint();
                }
            }
        });
        slideAnimTimer.start();
    }

    /**
     * 向右移动（带滑动动画）
     */
    private void moveRightAnimated() {
        if (animatingMove) return;
        saveState();
        int[][] old = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++){
            System.arraycopy(board[i], 0, old[i], 0, boardSize);
        }
        int[][] newBoard = new int[boardSize][boardSize];
        List<SlideAnimation> animations = new ArrayList<>();
        // 按行从右向左处理
        for (int i = 0; i < boardSize; i++){
            List<TileData> rowTiles = new ArrayList<>();
            for (int j = boardSize - 1; j >= 0; j--){
                if (old[i][j] != 0){
                    rowTiles.add(new TileData(j, old[i][j]));
                }
            }
            List<AnimatedTile> result = new ArrayList<>();
            for (TileData t : rowTiles) {
                if (!result.isEmpty() && result.get(result.size()-1).value == t.value && !result.get(result.size()-1).merged) {
                    AnimatedTile last = result.get(result.size()-1);
                    last.value *= 2;
                    last.merged = true;
                    score += last.value;
                    updateScoreLabel();
                    animations.add(new SlideAnimation(i, t.pos, i, boardSize - 1 - last.targetPos, t.value));
                    mergeAnimations.add(new MergeAnimation(i, boardSize - 1 - last.targetPos, last.value));
                    startMergeAnimTimer();
                } else {
                    int target = result.size();
                    result.add(new AnimatedTile(t.pos, target, t.value, false));
                    if (t.pos != boardSize - 1 - target) {
                        animations.add(new SlideAnimation(i, t.pos, i, boardSize - 1 - target, t.value));
                    }
                }
            }
            for (int j = 0; j < result.size(); j++){
                newBoard[i][boardSize - 1 - j] = result.get(j).value;
            }
        }
        boolean moved = false;
        for (int i = 0; i < boardSize; i++){
            if (!Arrays.equals(old[i], newBoard[i])){
                moved = true;
                break;
            }
        }
        if (!moved) {
            if (!undoStack.isEmpty()) undoStack.pop();
            return;
        }
        board = newBoard;
        slideAnimations = animations;
        animatingMove = true;
        slideAnimTimer = new Timer(10, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean updated = false;
                Iterator<SlideAnimation> it = slideAnimations.iterator();
                while(it.hasNext()){
                    SlideAnimation anim = it.next();
                    if (anim.progress < 1f) {
                        anim.progress += 0.1f;
                        if (anim.progress >= 1f) {
                            anim.progress = 1f;
                            if (!anim.finished) {
                                anim.finished = true;
                            } else {
                                it.remove();
                            }
                        }
                        updated = true;
                    } else {
                        if (!anim.finished) {
                            anim.finished = true;
                            updated = true;
                        } else {
                            it.remove();
                        }
                    }
                }
                if (updated) {
                    gamePanel.repaint();
                } else {
                    slideAnimTimer.stop();
                    animatingMove = false;
                    spawnRandomTile();
                    gamePanel.repaint();
                }
            }
        });
        slideAnimTimer.start();
    }

    /**
     * 向上移动（带滑动动画）
     */
    private void moveUpAnimated() {
        if (animatingMove) return;
        saveState();
        int[][] old = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++){
            System.arraycopy(board[i], 0, old[i], 0, boardSize);
        }
        int[][] newBoard = new int[boardSize][boardSize];
        List<SlideAnimation> animations = new ArrayList<>();
        // 按列处理
        for (int j = 0; j < boardSize; j++){
            List<TileData> colTiles = new ArrayList<>();
            for (int i = 0; i < boardSize; i++){
                if (old[i][j] != 0){
                    colTiles.add(new TileData(i, old[i][j]));
                }
            }
            List<AnimatedTile> result = new ArrayList<>();
            for (TileData t : colTiles) {
                if (!result.isEmpty() && result.get(result.size()-1).value == t.value && !result.get(result.size()-1).merged) {
                    AnimatedTile last = result.get(result.size()-1);
                    last.value *= 2;
                    last.merged = true;
                    score += last.value;
                    updateScoreLabel();
                    animations.add(new SlideAnimation(t.pos, j, last.targetPos, j, t.value));
                    mergeAnimations.add(new MergeAnimation(last.targetPos, j, last.value));
                    startMergeAnimTimer();
                } else {
                    int target = result.size();
                    result.add(new AnimatedTile(t.pos, target, t.value, false));
                    if (t.pos != target) {
                        animations.add(new SlideAnimation(t.pos, j, target, j, t.value));
                    }
                }
            }
            for (int i = 0; i < result.size(); i++){
                newBoard[i][j] = result.get(i).value;
            }
        }
        boolean moved = false;
        for (int i = 0; i < boardSize; i++){
            if (!Arrays.equals(old[i], newBoard[i])){
                moved = true;
                break;
            }
        }
        if (!moved) {
            if (!undoStack.isEmpty()) undoStack.pop();
            return;
        }
        board = newBoard;
        slideAnimations = animations;
        animatingMove = true;
        slideAnimTimer = new Timer(10, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean updated = false;
                Iterator<SlideAnimation> it = slideAnimations.iterator();
                while(it.hasNext()){
                    SlideAnimation anim = it.next();
                    if (anim.progress < 1f) {
                        anim.progress += 0.1f;
                        if (anim.progress >= 1f) {
                            anim.progress = 1f;
                            if (!anim.finished) {
                                anim.finished = true;
                            } else {
                                it.remove();
                            }
                        }
                        updated = true;
                    } else {
                        if (!anim.finished) {
                            anim.finished = true;
                            updated = true;
                        } else {
                            it.remove();
                        }
                    }
                }
                if (updated) {
                    gamePanel.repaint();
                } else {
                    slideAnimTimer.stop();
                    animatingMove = false;
                    spawnRandomTile();
                    gamePanel.repaint();
                }
            }
        });
        slideAnimTimer.start();
    }

    /**
     * 向下移动（带滑动动画）
     */
    private void moveDownAnimated() {
        if (animatingMove) return;
        saveState();
        int[][] old = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++){
            System.arraycopy(board[i], 0, old[i], 0, boardSize);
        }
        int[][] newBoard = new int[boardSize][boardSize];
        List<SlideAnimation> animations = new ArrayList<>();
        // 按列处理，从下向上扫描
        for (int j = 0; j < boardSize; j++){
            List<TileData> colTiles = new ArrayList<>();
            for (int i = boardSize - 1; i >= 0; i--){
                if (old[i][j] != 0){
                    colTiles.add(new TileData(i, old[i][j]));
                }
            }
            List<AnimatedTile> result = new ArrayList<>();
            for (TileData t : colTiles) {
                if (!result.isEmpty() && result.get(result.size()-1).value == t.value && !result.get(result.size()-1).merged) {
                    AnimatedTile last = result.get(result.size()-1);
                    last.value *= 2;
                    last.merged = true;
                    score += last.value;
                    updateScoreLabel();
                    animations.add(new SlideAnimation(t.pos, j, boardSize - 1 - last.targetPos, j, t.value));
                    mergeAnimations.add(new MergeAnimation(boardSize - 1 - last.targetPos, j, last.value));
                    startMergeAnimTimer();
                } else {
                    int target = result.size();
                    result.add(new AnimatedTile(t.pos, target, t.value, false));
                    if (t.pos != boardSize - 1 - target) {
                        animations.add(new SlideAnimation(t.pos, j, boardSize - 1 - target, j, t.value));
                    }
                }
            }
            for (int i = 0; i < result.size(); i++){
                newBoard[boardSize - 1 - i][j] = result.get(i).value;
            }
        }
        boolean moved = false;
        for (int i = 0; i < boardSize; i++){
            if (!Arrays.equals(old[i], newBoard[i])){
                moved = true;
                break;
            }
        }
        if (!moved) {
            if (!undoStack.isEmpty()) undoStack.pop();
            return;
        }
        board = newBoard;
        slideAnimations = animations;
        animatingMove = true;
        slideAnimTimer = new Timer(10, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean updated = false;
                Iterator<SlideAnimation> it = slideAnimations.iterator();
                while(it.hasNext()){
                    SlideAnimation anim = it.next();
                    if (anim.progress < 1f) {
                        anim.progress += 0.1f;
                        if (anim.progress >= 1f) {
                            anim.progress = 1f;
                            if (!anim.finished) {
                                anim.finished = true;
                            } else {
                                it.remove();
                            }
                        }
                        updated = true;
                    } else {
                        if (!anim.finished) {
                            anim.finished = true;
                            updated = true;
                        } else {
                            it.remove();
                        }
                    }
                }
                if (updated) {
                    gamePanel.repaint();
                } else {
                    slideAnimTimer.stop();
                    animatingMove = false;
                    spawnRandomTile();
                    gamePanel.repaint();
                }
            }
        });
        slideAnimTimer.start();
    }

    /**
     * 判断游戏是否结束：无空格且无可合并相邻方块
     */
    private boolean isGameOver() {
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                if (board[i][j] == 0) return false;
            }
        }
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                if ((i < boardSize - 1 && board[i][j] == board[i+1][j]) ||
                        (j < boardSize - 1 && board[i][j] == board[i][j+1])) {
                    return false;
                }
            }
        }
        return true;
    }

    // =====================================================
    // 自定义棋盘绘制面板
    // =====================================================
    class GamePanel extends JPanel {
        // 启用鼠标悬停提示功能
        public GamePanel() {
            setToolTipText("");
        }
        // 根据鼠标坐标计算所在单元格，并返回提示文本
        @Override
        public String getToolTipText(MouseEvent e) {
            int margin = 16;
            int gap = 10;
            int boardWidth = getWidth() - 2 * margin;
            int boardHeight = getHeight() - 2 * margin;
            int cellSize = Math.min((boardWidth - gap * (boardSize + 1)) / boardSize,
                    (boardHeight - gap * (boardSize + 1)) / boardSize);
            int x = e.getX();
            int y = e.getY();
            if (x < margin || x >= margin + boardWidth || y < margin || y >= margin + boardHeight) {
                return null;
            }
            int localX = x - margin;
            int localY = y - margin;
            int blockSize = cellSize + gap;
            int col = localX / blockSize;
            int row = localY / blockSize;
            int modX = localX % blockSize;
            int modY = localY % blockSize;
            if (modX < gap || modX >= gap + cellSize || modY < gap || modY >= gap + cellSize) {
                return null;
            }
            if (row < 0 || row >= boardSize || col < 0 || col >= boardSize) {
                return null;
            }
            int tileValue = board[row][col];
            return tileValue != 0 ? "Value: " + tileValue : null;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int margin = 16;
            int gap = 10; // 每个格子间的间隙

            // 整个棋盘区域尺寸
            int boardWidth = getWidth() - 2 * margin;
            int boardHeight = getHeight() - 2 * margin;
            int cellSize = Math.min((boardWidth - gap * (boardSize + 1)) / boardSize,
                    (boardHeight - gap * (boardSize + 1)) / boardSize);

            // 填充整个棋盘区域为官方背景色（0xbbada0）
            g.setColor(new Color(0xbbada0));
            g.fillRect(margin, margin, boardWidth, boardHeight);

            // 绘制静态单元格——空单元格与有数字的单元格
            for (int i = 0; i < boardSize; i++){
                for (int j = 0; j < boardSize; j++){
                    int x = margin + gap + j * (cellSize + gap);
                    int y = margin + gap + i * (cellSize + gap);
                    if (board[i][j] == 0) {
                        g.setColor(new Color(0xcdc1b4));
                        g.fillRoundRect(x, y, cellSize, cellSize, 15, 15);
                    } else {
                        drawTile(g, board[i][j], x, y, cellSize);
                    }
                }
            }

            // 绘制滑动动画中的方块
            for (SlideAnimation anim : slideAnimations) {
                float curCol = anim.fromCol + (anim.toCol - anim.fromCol) * anim.progress;
                float curRow = anim.fromRow + (anim.toRow - anim.fromRow) * anim.progress;
                int x = margin + gap + Math.round(curCol * (cellSize + gap));
                int y = margin + gap + Math.round(curRow * (cellSize + gap));
                drawTile(g, anim.value, x, y, cellSize);
            }

            // 绘制新 tile 缩放动画
            for (AnimationTile at : animTiles) {
                float scale = at.progress;
                int tileSize = Math.round(cellSize * scale);
                int x = margin + gap + at.col * (cellSize + gap) + (cellSize - tileSize) / 2;
                int y = margin + gap + at.row * (cellSize + gap) + (cellSize - tileSize) / 2;
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getTileColor(board[at.row][at.col]));
                g2.fillRoundRect(x, y, tileSize, tileSize, 15, 15);
                if (board[at.row][at.col] != 0) {
                    g2.setColor(board[at.row][at.col] < 16 ? new Color(0x776e65) : new Color(0xf9f6f2));
                    Font font = new Font("Arial", Font.BOLD, cellSize / 4);
                    g2.setFont(font);
                    FontMetrics fm = g2.getFontMetrics();
                    String s = String.valueOf(board[at.row][at.col]);
                    int strWidth = fm.stringWidth(s);
                    int strHeight = fm.getAscent();
                    g2.drawString(s, x + (tileSize - strWidth) / 2, y + (tileSize + strHeight) / 2 - 4);
                }
                g2.dispose();
            }

            // 绘制合并动画（弹出效果）——在单元格上方绘制
            for (MergeAnimation ma : mergeAnimations) {
                int x = margin + gap + ma.col * (cellSize + gap);
                int y = margin + gap + ma.row * (cellSize + gap);
                float scale = 1.3f - 0.3f * ma.progress;
                int tileSize = Math.round(cellSize * scale);
                int offset = (tileSize - cellSize) / 2;
                drawTile(g, ma.value, x - offset, y - offset, tileSize);
            }
        }

        /**
         * 绘制单个方块及其中数字
         */
        private void drawTile(Graphics g, int value, int x, int y, int size) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color tileColor = getTileColor(value);
            g2.setColor(tileColor);
            g2.fillRoundRect(x, y, size, size, 15, 15);
            if (value != 0) {
                g2.setColor(value < 16 ? new Color(0x776e65) : new Color(0xf9f6f2));
                Font font = new Font("Arial", Font.BOLD, size / 4);
                g2.setFont(font);
                FontMetrics fm = g2.getFontMetrics();
                String s = String.valueOf(value);
                int strWidth = fm.stringWidth(s);
                int strHeight = fm.getAscent();
                g2.drawString(s, x + (size - strWidth) / 2, y + (size + strHeight) / 2 - 4);
            }
            g2.dispose();
        }

        /**
         * 根据数字返回对应的背景颜色
         */
        private Color getTileColor(int value) {
            switch (value) {
                case 0:    return new Color(0xcdc1b4);
                case 2:    return new Color(0xeee4da);
                case 4:    return new Color(0xede0c8);
                case 8:    return new Color(0xf2b179);
                case 16:   return new Color(0xf59563);
                case 32:   return new Color(0xf67c5f);
                case 64:   return new Color(0xf65e3b);
                case 128:  return new Color(0xedcf72);
                case 256:  return new Color(0xedcc61);
                case 512:  return new Color(0xedc850);
                case 1024: return new Color(0xedc53f);
                case 2048: return new Color(0xedc22e);
                default:   return new Color(0x3c3a32);
            }
        }
    }

    // =====================================================
    // 自定义圆角按钮（美化效果）
    // =====================================================
    class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setFont(new Font("Arial", Font.BOLD, 16));
            setForeground(Color.WHITE);
            setBackground(new Color(0x8f7a66));
            setMargin(new Insets(5, 15, 5, 15));
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g2);
            g2.dispose();
        }
        @Override
        public void updateUI() {
            setUI(new BasicButtonUI());
        }
    }

    /**
     * 主函数：启动程序时询问棋盘大小，然后创建游戏
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                String input = JOptionPane.showInputDialog("请输入棋盘大小（例如输入4表示4x4棋盘）：");
                int size = 4;  // 默认 4x4
                try {
                    size = Integer.parseInt(input);
                    if (size < 2) {
                        size = 4;
                    }
                } catch (NumberFormatException e) {
                    size = 4;
                }
                Game2048 game = new Game2048(size);
                game.setVisible(true);
            }
        });
    }
}
