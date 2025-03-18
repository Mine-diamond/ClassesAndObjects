package edu.play.rc;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class RandomSelector {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // 设置全局字体以改善显示
                setUIFont(new javax.swing.plaf.FontUIResource("SansSerif", Font.PLAIN, 14));
            } catch (Exception e) {
                e.printStackTrace();
            }
            new RandomSelectorApp();
        });
    }

    private static void setUIFont(javax.swing.plaf.FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }
}

class RandomSelectorApp extends JFrame {
    // UI组件
    private JTabbedPane tabbedPane;
    private JTable itemsTable;
    private DefaultTableModel tableModel;
    private JTextArea resultsTextArea;
    private JTextField configNameField;
    private JComboBox<String> configComboBox;
    private JSpinner numberToSelectSpinner;
    private JCheckBox allowRepeatCheckBox;
    private JCheckBox enableCooldownCheckBox;
    private JSpinner cooldownCountSpinner;
    private JCheckBox useWeightedSelectionCheckBox;
    private JComboBox<String> algorithmComboBox;
    private JLabel statusLabel;
    private JButton selectButton;

    // 数据管理
    private ConfigurationManager configManager;
    private DefaultComboBoxModel<String> configModel;
    private Map<String, Integer> itemCooldowns = new HashMap<>();
    private Map<String, Integer> itemSelectionCounts = new HashMap<>(); // 用于平衡随机算法
    private List<String> selectionHistory = new ArrayList<>(); // 用于全覆盖随机算法

    // 线程池
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    // 界面颜色
    private final Color HEADER_COLOR = new Color(40, 80, 120);
    private final Color BACKGROUND_COLOR = new Color(245, 245, 248);
    private final Color PANEL_COLOR = new Color(255, 255, 255);
    private final Color BUTTON_COLOR = new Color(70, 130, 180);
    private final Color TEXT_COLOR = new Color(33, 33, 33);
    private final Color HIGHLIGHT_COLOR = new Color(255, 140, 0);

    // 算法类型
    private static final String ALGORITHM_PURE_RANDOM = "完全随机";
    private static final String ALGORITHM_COOLDOWN = "冷却随机";
    private static final String ALGORITHM_BALANCED = "平衡随机";
    private static final String ALGORITHM_COVERAGE = "全覆盖随机";

    public RandomSelectorApp() {
        configManager = new ConfigurationManager();

        // 基本窗口设置
        setTitle("随机选择工具 - 终极版");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setIconImage(createIcon());

        // 创建标签页面板
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));

        // 创建配置面板
        JPanel configPanel = createConfigPanel();
        JScrollPane configScrollPane = new JScrollPane(configPanel);
        configScrollPane.setBorder(null);
        configScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // 创建选择面板
        JPanel selectionPanel = createSelectionPanel();

        // 添加标签页
        tabbedPane.addTab("配置管理", new ImageIcon(), configScrollPane, "管理项目和配置");
        tabbedPane.addTab("随机选择", new ImageIcon(), selectionPanel, "执行随机选择");

        // 创建底部状态栏
        statusLabel = new JLabel("就绪");
        statusLabel.setBorder(new CompoundBorder(
                new MatteBorder(1, 0, 0, 0, new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)
        ));
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        // 设置内容面板
        setContentPane(mainPanel);

        // 加载已保存的配置
        loadSavedConfigurations();

        // 默认添加几行
        for (int i = 0; i < 5; i++) {
            addTableRow();
        }

        // 注册窗口关闭事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                executorService.shutdown();
            }
        });

        // 显示窗口
        setVisible(true);
    }

    private Image createIcon() {
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(BUTTON_COLOR);
        g2d.fillOval(2, 2, 28, 28);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(8, 8, 16, 16);
        g2d.setColor(HIGHLIGHT_COLOR);
        g2d.fillOval(12, 12, 8, 8);
        g2d.dispose();
        return image;
    }

    private JPanel createConfigPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // 创建配置管理面板
        JPanel configManagerPanel = createConfigManagerPanel();
        panel.add(configManagerPanel);
        panel.add(Box.createVerticalStrut(15));

        // 创建项目表格面板
        JPanel itemsPanel = createItemsPanel();
        panel.add(itemsPanel);

        return panel;
    }

    private JPanel createConfigManagerPanel() {
        JPanel panel = createStyledPanel("配置管理");

        // 配置名称面板
        JPanel namePanel = new JPanel(new BorderLayout(5, 5));
        namePanel.setBackground(PANEL_COLOR);
        JLabel nameLabel = new JLabel("配置名称:");
        nameLabel.setForeground(TEXT_COLOR);
        configNameField = new JTextField();
        configNameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        namePanel.add(nameLabel, BorderLayout.WEST);
        namePanel.add(configNameField, BorderLayout.CENTER);

        // 配置选择面板
        JPanel selectPanel = new JPanel(new BorderLayout(5, 5));
        selectPanel.setBackground(PANEL_COLOR);
        JLabel configLabel = new JLabel("已保存配置:");
        configLabel.setForeground(TEXT_COLOR);
        configModel = new DefaultComboBoxModel<>();
        configComboBox = new JComboBox<>(configModel);
        configComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        selectPanel.add(configLabel, BorderLayout.WEST);
        selectPanel.add(configComboBox, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonPanel.setBackground(PANEL_COLOR);

        JButton newButton = createStyledButton("新建", "新建配置");
        JButton loadButton = createStyledButton("加载", "加载所选配置");
        JButton saveButton = createStyledButton("保存", "保存当前配置");
        JButton deleteButton = createStyledButton("删除", "删除所选配置");

        newButton.addActionListener(e -> newConfiguration());
        loadButton.addActionListener(e -> loadConfiguration());
        saveButton.addActionListener(e -> saveConfiguration());
        deleteButton.addActionListener(e -> deleteConfiguration());

        buttonPanel.add(newButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);

        // 导入/导出面板
        JPanel importExportPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        importExportPanel.setBackground(PANEL_COLOR);

        JButton importButton = createStyledButton("导入配置", "从文件导入配置");
        JButton exportButton = createStyledButton("导出配置", "导出配置到文件");

        importButton.addActionListener(e -> importConfiguration());
        exportButton.addActionListener(e -> exportConfiguration());

        importExportPanel.add(importButton);
        importExportPanel.add(exportButton);

        // 将所有组件添加到面板
        panel.add(namePanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(selectPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(importExportPanel);

        return panel;
    }

    private JPanel createItemsPanel() {
        JPanel panel = createStyledPanel("项目列表");
        panel.setLayout(new BorderLayout(0, 10));

        // 权重设置面板
        JPanel weightPanel = new JPanel(new BorderLayout(5, 5));
        weightPanel.setBackground(PANEL_COLOR);

        JPanel weightCheckPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        weightCheckPanel.setBackground(PANEL_COLOR);

        JLabel weightLabel = new JLabel("使用权重模式:");
        weightLabel.setForeground(TEXT_COLOR);
        useWeightedSelectionCheckBox = new JCheckBox();
        useWeightedSelectionCheckBox.addActionListener(e -> updateTableForWeightMode());

        weightCheckPanel.add(weightLabel);
        weightCheckPanel.add(useWeightedSelectionCheckBox);

        JLabel weightInfoLabel = new JLabel("(权重值越大，被选中概率越高)");
        weightInfoLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        weightInfoLabel.setForeground(Color.DARK_GRAY);

        weightPanel.add(weightCheckPanel, BorderLayout.WEST);
        weightPanel.add(weightInfoLabel, BorderLayout.EAST);

        // 创建表格
        String[] columns = {"项目", "权重"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return Integer.class;
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 1) {
                    return useWeightedSelectionCheckBox.isSelected();
                }
                return true;
            }
        };

        // 创建和配置表格
        itemsTable = new JTable(tableModel);
        itemsTable.setRowHeight(30);
        itemsTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        itemsTable.setForeground(TEXT_COLOR);
        itemsTable.setGridColor(new Color(230, 230, 230));

        JTableHeader header = itemsTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(HEADER_COLOR);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        // 自定义渲染器和编辑器
        itemsTable.getColumnModel().getColumn(1).setCellRenderer(new WeightCellRenderer());
        itemsTable.getColumnModel().getColumn(1).setCellEditor(new WeightCellEditor());

        // 设置列宽
        TableColumnModel columnModel = itemsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(350);
        columnModel.getColumn(1).setPreferredWidth(150);

        JScrollPane tableScrollPane = new JScrollPane(itemsTable);
        tableScrollPane.setPreferredSize(new Dimension(500, 300));
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        // 按钮面板
        JPanel tableButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        tableButtonPanel.setBackground(PANEL_COLOR);

        JButton addRowButton = createStyledButton("添加行", "添加一行");
        JButton addMultipleRowsButton = createStyledButton("+10行", "添加10行");
        JButton removeRowButton = createStyledButton("删除行", "删除选中的行");
        JButton clearTableButton = createStyledButton("清空表格", "清空所有行");

        addRowButton.addActionListener(e -> addTableRow());
        addMultipleRowsButton.addActionListener(e -> {
            for (int i = 0; i < 10; i++) {
                addTableRow();
            }
        });
        removeRowButton.addActionListener(e -> removeSelectedRows());
        clearTableButton.addActionListener(e -> clearTable());

        tableButtonPanel.add(addRowButton);
        tableButtonPanel.add(addMultipleRowsButton);
        tableButtonPanel.add(removeRowButton);
        tableButtonPanel.add(clearTableButton);

        // 添加到面板
        panel.add(weightPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(tableButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // 创建设置面板
        JPanel settingsPanel = createSelectionSettingsPanel();

        // 创建结果面板
        JPanel resultsPanel = createResultsPanel();

        // 添加到选择面板
        panel.add(settingsPanel, BorderLayout.NORTH);
        panel.add(resultsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSelectionSettingsPanel() {
        JPanel panel = createStyledPanel("选择设置");

        // 基本选择设置面板
        JPanel settingsGrid = new JPanel(new GridLayout(3, 4, 15, 10));
        settingsGrid.setBackground(PANEL_COLOR);

        // 第一行设置
        JLabel countLabel = new JLabel("选择数量:", JLabel.RIGHT);
        countLabel.setForeground(TEXT_COLOR);
        numberToSelectSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        numberToSelectSpinner.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ((JSpinner.DefaultEditor)numberToSelectSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);

        JLabel repeatLabel = new JLabel("允许重复:", JLabel.RIGHT);
        repeatLabel.setForeground(TEXT_COLOR);
        allowRepeatCheckBox = new JCheckBox();

        settingsGrid.add(countLabel);
        settingsGrid.add(numberToSelectSpinner);
        settingsGrid.add(repeatLabel);
        settingsGrid.add(allowRepeatCheckBox);

        // 第二行设置
        JLabel enableCooldownLabel = new JLabel("启用冷却期:", JLabel.RIGHT);
        enableCooldownLabel.setForeground(TEXT_COLOR);
        enableCooldownCheckBox = new JCheckBox();

        JLabel cooldownCountLabel = new JLabel("冷却次数:", JLabel.RIGHT);
        cooldownCountLabel.setForeground(TEXT_COLOR);
        cooldownCountSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 50, 1));
        cooldownCountSpinner.setEnabled(false);
        cooldownCountSpinner.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ((JSpinner.DefaultEditor)cooldownCountSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);

        enableCooldownCheckBox.addActionListener(e -> {
            cooldownCountSpinner.setEnabled(enableCooldownCheckBox.isSelected());
            // 如果启用冷却，自动选择冷却随机算法
            if (enableCooldownCheckBox.isSelected()) {
                algorithmComboBox.setSelectedItem(ALGORITHM_COOLDOWN);
            }
        });

        settingsGrid.add(enableCooldownLabel);
        settingsGrid.add(enableCooldownCheckBox);
        settingsGrid.add(cooldownCountLabel);
        settingsGrid.add(cooldownCountSpinner);

        // 第三行设置 - 算法选择
        JLabel algorithmLabel = new JLabel("选择算法:", JLabel.RIGHT);
        algorithmLabel.setForeground(TEXT_COLOR);

        String[] algorithms = {
                ALGORITHM_PURE_RANDOM,
                ALGORITHM_COOLDOWN,
                ALGORITHM_BALANCED,
                ALGORITHM_COVERAGE
        };

        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel algorithmInfoLabel = new JLabel("算法说明", JLabel.RIGHT);
        algorithmInfoLabel.setForeground(BUTTON_COLOR);
        algorithmInfoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        algorithmInfoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showAlgorithmInfo();
            }
        });

        // 占位符，保持网格对齐
        JPanel placeholderPanel = new JPanel();
        placeholderPanel.setBackground(PANEL_COLOR);

        settingsGrid.add(algorithmLabel);
        settingsGrid.add(algorithmComboBox);
        settingsGrid.add(algorithmInfoLabel);
        settingsGrid.add(placeholderPanel);

        // 选择按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(PANEL_COLOR);

        selectButton = createStyledButton("开始选择", "执行随机选择");
        selectButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        selectButton.setPreferredSize(new Dimension(160, 40));

        JButton clearButton = createStyledButton("清除结果", "清空结果显示");
        clearButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        clearButton.setPreferredSize(new Dimension(160, 40));

        selectButton.addActionListener(e -> performSelection());
        clearButton.addActionListener(e -> clearResults());

        buttonPanel.add(selectButton);
        buttonPanel.add(clearButton);

        // 添加到设置面板
        panel.add(settingsGrid);
        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel);

        return panel;
    }

    private void showAlgorithmInfo() {
        String message =
                "四种随机算法说明：\n\n" +
                        "1. 完全随机：每次选择完全独立，可能出现连续选中同一项目。\n\n" +
                        "2. 冷却随机：被选中的项目在指定次数内不会再次被选中。\n\n" +
                        "3. 平衡随机：尽量平衡每个项目被选中的次数，避免某些项目被过度选中。\n\n" +
                        "4. 全覆盖随机：确保所有项目都会被选中一次后，才开始下一轮选择。";

        JOptionPane.showMessageDialog(
                this,
                message,
                "随机算法说明",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private JPanel createResultsPanel() {
        JPanel panel = createStyledPanel("选择结果");
        panel.setLayout(new BorderLayout());

        resultsTextArea = new JTextArea();
        resultsTextArea.setEditable(false);
        resultsTextArea.setFont(new Font("SansSerif", Font.BOLD, 20));
        resultsTextArea.setForeground(TEXT_COLOR);
        resultsTextArea.setLineWrap(true);
        resultsTextArea.setWrapStyleWord(true);
        resultsTextArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        resultsTextArea.setBackground(new Color(252, 252, 252));

        JScrollPane scrollPane = new JScrollPane(resultsTextArea);
        scrollPane.setBorder(null);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // 创建一个带样式的面板
    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PANEL_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                        title,
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("SansSerif", Font.BOLD, 14),
                        BUTTON_COLOR
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return panel;
    }

    // 创建一个带样式的按钮
    private JButton createStyledButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(8, 15, 8, 15));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_COLOR.darker());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_COLOR);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_COLOR.darker().darker());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_COLOR.darker());
                }
            }
        });

        return button;
    }

    private void addTableRow() {
        boolean isWeightMode = useWeightedSelectionCheckBox.isSelected();
        Object[] rowData;

        if (isWeightMode) {
            rowData = new Object[]{"", 1};
        } else {
            rowData = new Object[]{"", ""};
        }

        tableModel.addRow(rowData);
    }

    private void removeSelectedRows() {
        int[] selectedRows = itemsTable.getSelectedRows();
        Arrays.sort(selectedRows);

        for (int i = selectedRows.length - 1; i >= 0; i--) {
            tableModel.removeRow(selectedRows[i]);
        }
    }

    private void clearTable() {
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
    }

    private void updateTableForWeightMode() {
        boolean isWeightMode = useWeightedSelectionCheckBox.isSelected();

        // 更新权重列的编辑状态
        itemsTable.getColumnModel().getColumn(1).setCellEditor(
                isWeightMode ? new WeightCellEditor() : new DefaultCellEditor(new JTextField()));

        // 更新表格数据
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (isWeightMode) {
                // 切换到权重模式，设置默认权重为1
                Object weightValue = tableModel.getValueAt(i, 1);
                if (weightValue == null || weightValue.toString().isEmpty()) {
                    tableModel.setValueAt(1, i, 1);
                }
            }
        }

        // 刷新表格
        itemsTable.repaint();
    }

    private void newConfiguration() {
        // 清空配置名称
        configNameField.setText("");

        // 重置选择设置
        numberToSelectSpinner.setValue(1);
        allowRepeatCheckBox.setSelected(false);
        enableCooldownCheckBox.setSelected(false);
        cooldownCountSpinner.setValue(3);
        cooldownCountSpinner.setEnabled(false);
        useWeightedSelectionCheckBox.setSelected(false);
        algorithmComboBox.setSelectedItem(ALGORITHM_PURE_RANDOM);

        // 清空表格并添加几行
        clearTable();
        for (int i = 0; i < 5; i++) {
            addTableRow();
        }

        // 清空结果
        clearResults();

        // 重置选择历史
        itemSelectionCounts.clear();
        selectionHistory.clear();

        // 更新状态
        statusLabel.setText("已创建新配置");
    }

    private void performSelection() {
        // 禁用选择按钮，防止重复点击
        selectButton.setEnabled(false);

        // 使用线程池执行选择操作，避免UI阻塞
        executorService.submit(() -> {
            try {
                List<SelectionItem> items = collectItemsFromTable();
                if (items.isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this,
                                "请至少输入一个项目", "无可选项目", JOptionPane.WARNING_MESSAGE);
                        tabbedPane.setSelectedIndex(0); // 切换到配置面板
                    });
                    return;
                }

                int count = (Integer) numberToSelectSpinner.getValue();
                boolean allowRepeat = allowRepeatCheckBox.isSelected();
                boolean enableCooldown = enableCooldownCheckBox.isSelected();
                int cooldownPeriod = (Integer) cooldownCountSpinner.getValue();
                boolean useWeightedSelection = useWeightedSelectionCheckBox.isSelected();
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();

                // 根据所选算法执行选择
                List<SelectionItem> selected = new ArrayList<>();

                switch (selectedAlgorithm) {
                    case ALGORITHM_PURE_RANDOM:
                        selected = performPureRandomSelection(items, count, allowRepeat, useWeightedSelection);
                        break;
                    case ALGORITHM_COOLDOWN:
                        selected = performCooldownSelection(items, count, allowRepeat, cooldownPeriod, useWeightedSelection);
                        break;
                    case ALGORITHM_BALANCED:
                        selected = performBalancedSelection(items, count, allowRepeat, useWeightedSelection);
                        break;
                    case ALGORITHM_COVERAGE:
                        selected = performCoverageSelection(items, count, allowRepeat, useWeightedSelection);
                        break;
                    default:
                        selected = performPureRandomSelection(items, count, allowRepeat, useWeightedSelection);
                }

                // 更新选择历史和计数
                for (SelectionItem item : selected) {
                    String name = item.getName();
                    itemSelectionCounts.put(name, itemSelectionCounts.getOrDefault(name, 0) + 1);

                    // 更新全覆盖算法的历史
                    if (!selectionHistory.contains(name)) {
                        selectionHistory.add(name);
                    }
                }

                // 如果全覆盖算法已经覆盖所有项目，重置历史
                if (selectionHistory.size() >= items.size()) {
                    selectionHistory.clear();
                }

                // 显示结果
                final List<SelectionItem> finalSelected = selected;
                SwingUtilities.invokeLater(() -> {
                    displaySelectionResults(finalSelected);
                    // 切换到结果标签页
                    tabbedPane.setSelectedIndex(1);
                });
            } finally {
                // 确保按钮总是被重新启用
                SwingUtilities.invokeLater(() -> selectButton.setEnabled(true));
            }
        });
    }

    private List<SelectionItem> collectItemsFromTable() {
        List<SelectionItem> items = new ArrayList<>();
        boolean isWeightMode = useWeightedSelectionCheckBox.isSelected();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object itemNameObj = tableModel.getValueAt(i, 0);
            String itemName = itemNameObj != null ? itemNameObj.toString().trim() : "";

            if (!itemName.isEmpty()) {
                int weight = 1;
                if (isWeightMode) {
                    try {
                        Object weightObj = tableModel.getValueAt(i, 1);
                        if (weightObj != null) {
                            if (weightObj instanceof Integer) {
                                weight = (Integer) weightObj;
                            } else {
                                weight = Integer.parseInt(weightObj.toString().trim());
                            }
                            // 确保权重至少为1
                            weight = Math.max(1, weight);
                        }
                    } catch (NumberFormatException e) {
                        // 使用默认权重1
                    }
                }
                items.add(new SelectionItem(itemName, weight));
            }
        }

        return items;
    }

    // 1. 完全随机算法
    private List<SelectionItem> performPureRandomSelection(
            List<SelectionItem> items, int count, boolean allowRepeat, boolean useWeighted) {
        List<SelectionItem> selected = new ArrayList<>();
        Random random = new Random();

        if (allowRepeat) {
            // 允许重复的选择
            for (int i = 0; i < count; i++) {
                if (useWeighted) {
                    // 权重选择
                    SelectionItem item = selectItemByWeight(items, random);
                    if (item != null) {
                        selected.add(item);
                    }
                } else {
                    // 普通随机选择
                    int index = random.nextInt(items.size());
                    selected.add(items.get(index));
                }
            }
        } else {
            // 不允许重复的选择
            List<SelectionItem> availableItems = new ArrayList<>(items);
            for (int i = 0; i < count && !availableItems.isEmpty(); i++) {
                if (useWeighted) {
                    // 权重选择
                    SelectionItem item = selectItemByWeight(availableItems, random);
                    if (item != null) {
                        selected.add(item);
                        availableItems.remove(item);
                    }
                } else {
                    // 普通随机选择
                    int index = random.nextInt(availableItems.size());
                    selected.add(availableItems.get(index));
                    availableItems.remove(index);
                }
            }
        }

        return selected;
    }

    // 2. 冷却随机算法
    private List<SelectionItem> performCooldownSelection(
            List<SelectionItem> items, int count, boolean allowRepeat,
            int cooldownPeriod, boolean useWeighted) {

        // 先减少所有项目的冷却计数
        decrementCooldowns();

        // 过滤掉冷却中的项目
        List<SelectionItem> availableItems = items.stream()
                .filter(item -> !itemCooldowns.containsKey(item.getName()))
                .collect(Collectors.toList());

        // 如果没有可用项目，但允许重复，则使用所有项目
        if (availableItems.isEmpty() && allowRepeat) {
            availableItems = new ArrayList<>(items);
        }

        // 如果还是没有可用项目，返回空列表
        if (availableItems.isEmpty()) {
            return new ArrayList<>();
        }

        // 执行选择
        List<SelectionItem> selected = performPureRandomSelection(
                availableItems, count, allowRepeat, useWeighted);

        // 将选中的项目添加到冷却列表
        for (SelectionItem item : selected) {
            itemCooldowns.put(item.getName(), cooldownPeriod);
        }

        return selected;
    }

    // 3. 平衡随机算法
    private List<SelectionItem> performBalancedSelection(
            List<SelectionItem> items, int count, boolean allowRepeat, boolean useWeighted) {

        List<SelectionItem> selected = new ArrayList<>();
        Random random = new Random();

        // 为每个项目计算选择优先级（被选择次数越少，优先级越高）
        Map<String, Integer> selectionPriority = new HashMap<>();
        int maxCount = 0;

        for (SelectionItem item : items) {
            int timesSelected = itemSelectionCounts.getOrDefault(item.getName(), 0);
            selectionPriority.put(item.getName(), timesSelected);
            maxCount = Math.max(maxCount, timesSelected);
        }

        // 创建可用项目列表
        List<SelectionItem> availableItems = new ArrayList<>(items);

        for (int i = 0; i < count; i++) {
            if (availableItems.isEmpty()) break;

            // 找出被选择次数最少的项目
            int minCount = maxCount;
            for (SelectionItem item : availableItems) {
                minCount = Math.min(minCount, selectionPriority.getOrDefault(item.getName(), 0));
            }

            // 筛选出被选择次数最少的项目
            int finalMinCount = minCount;
            List<SelectionItem> priorityItems = availableItems.stream()
                    .filter(item -> selectionPriority.getOrDefault(item.getName(), 0) == finalMinCount)
                    .collect(Collectors.toList());

            // 从优先级最高的项目中随机选择
            SelectionItem selectedItem;
            if (useWeighted) {
                selectedItem = selectItemByWeight(priorityItems, random);
            } else {
                int index = random.nextInt(priorityItems.size());
                selectedItem = priorityItems.get(index);
            }

            selected.add(selectedItem);

            // 更新选择次数
            String name = selectedItem.getName();
            selectionPriority.put(name, selectionPriority.getOrDefault(name, 0) + 1);

            // 如果不允许重复，从可用列表中移除
            if (!allowRepeat) {
                availableItems.removeIf(item -> item.getName().equals(name));
            }
        }

        return selected;
    }

    // 4. 全覆盖随机算法
    private List<SelectionItem> performCoverageSelection(
            List<SelectionItem> items, int count, boolean allowRepeat, boolean useWeighted) {

        List<SelectionItem> selected = new ArrayList<>();
        Random random = new Random();

        // 创建未选中项目列表（优先选择这些项目）
        List<SelectionItem> unselectedItems = items.stream()
                .filter(item -> !selectionHistory.contains(item.getName()))
                .collect(Collectors.toList());

        // 如果所有项目都已被选中过，重置历史
        if (unselectedItems.isEmpty()) {
            selectionHistory.clear();
            unselectedItems = new ArrayList<>(items);
        }

        // 创建可用项目列表（包括已选中和未选中的项目）
        List<SelectionItem> availableItems = new ArrayList<>(items);

        for (int i = 0; i < count; i++) {
            if (availableItems.isEmpty()) break;

            SelectionItem selectedItem;

            // 优先从未选中的项目中选择
            if (!unselectedItems.isEmpty()) {
                if (useWeighted) {
                    selectedItem = selectItemByWeight(unselectedItems, random);
                } else {
                    int index = random.nextInt(unselectedItems.size());
                    selectedItem = unselectedItems.get(index);
                }
                unselectedItems.remove(selectedItem);
            } else {
                // 如果所有项目都已被选中过，从所有可用项目中选择
                if (useWeighted) {
                    selectedItem = selectItemByWeight(availableItems, random);
                } else {
                    int index = random.nextInt(availableItems.size());
                    selectedItem = availableItems.get(index);
                }
            }

            selected.add(selectedItem);

            // 更新选择历史
            if (!selectionHistory.contains(selectedItem.getName())) {
                selectionHistory.add(selectedItem.getName());
            }

            // 如果不允许重复，从可用列表中移除
            if (!allowRepeat) {
                String name = selectedItem.getName();
                availableItems.removeIf(item -> item.getName().equals(name));
                unselectedItems.removeIf(item -> item.getName().equals(name));
            }
        }

        return selected;
    }

    private SelectionItem selectItemByWeight(List<SelectionItem> items, Random random) {
        if (items.isEmpty()) return null;

        // 计算总权重
        int totalWeight = 0;
        for (SelectionItem item : items) {
            totalWeight += item.getWeight();
        }

        if (totalWeight <= 0) return items.get(0);

        // 生成一个0到总权重之间的随机数
        int randomValue = random.nextInt(totalWeight);

        // 根据权重选择项目
        int cumulativeWeight = 0;
        for (SelectionItem item : items) {
            cumulativeWeight += item.getWeight();
            if (randomValue < cumulativeWeight) {
                return item;
            }
        }

        // 理论上不应该到达这里，但为安全返回最后一个项目
        return items.get(items.size() - 1);
    }

    private void decrementCooldowns() {
        // 减少所有项目的冷却计数
        Iterator<Map.Entry<String, Integer>> iterator = itemCooldowns.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            int newValue = entry.getValue() - 1;
            if (newValue <= 0) {
                iterator.remove();
            } else {
                entry.setValue(newValue);
            }
        }
    }

    private void displaySelectionResults(List<SelectionItem> selected) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < selected.size(); i++) {
            SelectionItem item = selected.get(i);
            result.append(String.format("%d. %s\n", i + 1, item.getName()));
        }

        // 设置结果文本
        resultsTextArea.setText(result.toString());

        // 使用动画效果显示结果
        animateResults();

        // 更新状态栏
        statusLabel.setText(String.format("已选择 %d 个项目", selected.size()));
    }

    private void animateResults() {
        // 使用动画效果显示结果
        resultsTextArea.setForeground(HIGHLIGHT_COLOR);

        Timer timer = new Timer(800, e -> {
            resultsTextArea.setForeground(TEXT_COLOR);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void clearResults() {
        resultsTextArea.setText("");
        statusLabel.setText("结果已清除");
    }

    private void saveConfiguration() {
        String configName = configNameField.getText().trim();
        if (configName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入配置名称", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 收集表格中的项目
        List<SelectionItem> items = collectItemsFromTable();

        EnhancedConfiguration config = new EnhancedConfiguration();
        config.setName(configName);
        config.setItems(items);
        config.setNumberToSelect((Integer) numberToSelectSpinner.getValue());
        config.setAllowRepeat(allowRepeatCheckBox.isSelected());
        config.setEnableCooldown(enableCooldownCheckBox.isSelected());
        config.setCooldownCount((Integer) cooldownCountSpinner.getValue());
        config.setUseWeightedSelection(useWeightedSelectionCheckBox.isSelected());
        config.setSelectedAlgorithm((String) algorithmComboBox.getSelectedItem());

        configManager.saveConfiguration(config);

        // 更新配置列表
        updateConfigList();
        statusLabel.setText("配置 '" + configName + "' 已保存");
    }

    private void loadConfiguration() {
        String selectedConfig = (String) configComboBox.getSelectedItem();
        if (selectedConfig == null) {
            JOptionPane.showMessageDialog(this, "请先选择一个配置", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EnhancedConfiguration config = configManager.loadConfiguration(selectedConfig);
        if (config != null) {
            // 加载配置名称和设置
            configNameField.setText(config.getName());
            numberToSelectSpinner.setValue(config.getNumberToSelect());
            allowRepeatCheckBox.setSelected(config.isAllowRepeat());
            enableCooldownCheckBox.setSelected(config.isEnableCooldown());
            cooldownCountSpinner.setValue(config.getCooldownCount());
            cooldownCountSpinner.setEnabled(config.isEnableCooldown());

            // 加载算法选择
            if (config.getSelectedAlgorithm() != null) {
                algorithmComboBox.setSelectedItem(config.getSelectedAlgorithm());
            } else {
                algorithmComboBox.setSelectedItem(ALGORITHM_PURE_RANDOM);
            }

            // 先设置权重模式，然后更新表格
            boolean useWeights = config.isUseWeightedSelection();
            useWeightedSelectionCheckBox.setSelected(useWeights);

            // 更新表格
            clearTable();
            for (SelectionItem item : config.getItems()) {
                if (useWeights) {
                    tableModel.addRow(new Object[]{item.getName(), item.getWeight()});
                } else {
                    tableModel.addRow(new Object[]{item.getName(), ""});
                }
            }

            // 重置选择历史和计数
            itemSelectionCounts.clear();
            selectionHistory.clear();

            statusLabel.setText("已加载配置 '" + selectedConfig + "'");

            // 清空结果
            clearResults();
        } else {
            JOptionPane.showMessageDialog(this, "加载配置失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteConfiguration() {
        String selectedConfig = (String) configComboBox.getSelectedItem();
        if (selectedConfig == null) {
            JOptionPane.showMessageDialog(this, "请先选择一个配置", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "确定要删除配置 '" + selectedConfig + "' 吗？",
                "确认删除", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            configManager.deleteConfiguration(selectedConfig);
            updateConfigList();
            statusLabel.setText("已删除配置 '" + selectedConfig + "'");
        }
    }

    private void importConfiguration() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("导入配置");
        fileChooser.setFileFilter(new FileNameExtensionFilter("配置文件 (*.cfg)", "cfg"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            boolean success = configManager.importConfiguration(selectedFile);

            if (success) {
                updateConfigList();
                statusLabel.setText("成功导入配置: " + selectedFile.getName());
            } else {
                JOptionPane.showMessageDialog(this, "导入配置失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportConfiguration() {
        String selectedConfig = (String) configComboBox.getSelectedItem();
        if (selectedConfig == null) {
            JOptionPane.showMessageDialog(this, "请先选择一个配置", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("导出配置");
        fileChooser.setFileFilter(new FileNameExtensionFilter("配置文件 (*.cfg)", "cfg"));
        fileChooser.setSelectedFile(new File(selectedConfig + ".cfg"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().toLowerCase().endsWith(".cfg")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".cfg");
            }

            boolean success = configManager.exportConfiguration(selectedConfig, selectedFile);

            if (success) {
                statusLabel.setText("成功导出配置到 " + selectedFile.getName());
            } else {
                JOptionPane.showMessageDialog(this, "导出配置失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadSavedConfigurations() {
        List<String> configs = configManager.getConfigurationNames();
        for (String config : configs) {
            configModel.addElement(config);
        }

        if (configs.size() > 0) {
            configComboBox.setSelectedIndex(0);
        }
    }

    private void updateConfigList() {
        configModel.removeAllElements();
        List<String> configs = configManager.getConfigurationNames();
        for (String config : configs) {
            configModel.addElement(config);
        }
    }

    // 权重单元格渲染器
    class WeightCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            Component component = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            // 只有在权重模式下才显示权重值
            if (column == 1 && !useWeightedSelectionCheckBox.isSelected()) {
                setText("");
                setBackground(new Color(240, 240, 240));
            } else if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            setHorizontalAlignment(JLabel.CENTER);
            return component;
        }
    }

    // 权重单元格编辑器
    class WeightCellEditor extends DefaultCellEditor {
        private JTextField textField;

        public WeightCellEditor() {
            super(new JTextField());
            textField = (JTextField) getComponent();
            textField.setHorizontalAlignment(JTextField.CENTER);
        }

        @Override
        public boolean stopCellEditing() {
            String value = textField.getText();
            if (!value.isEmpty()) {
                try {
                    int weight = Integer.parseInt(value);
                    if (weight <= 0) {
                        JOptionPane.showMessageDialog(null, "权重必须是正整数", "无效输入", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "权重必须是正整数", "无效输入", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            return super.stopCellEditing();
        }
    }
}

class ConfigurationManager {
    private static final String CONFIG_DIR = "configs";
    private Map<String, EnhancedConfiguration> configurations;

    public ConfigurationManager() {
        configurations = new HashMap<>();
        loadConfigurations();
    }

    public void saveConfiguration(EnhancedConfiguration config) {
        configurations.put(config.getName(), config);
        saveConfigurationToFile(config);
    }

    public EnhancedConfiguration loadConfiguration(String name) {
        return configurations.get(name);
    }

    public void deleteConfiguration(String name) {
        configurations.remove(name);
        File configFile = new File(CONFIG_DIR + File.separator + name + ".cfg");
        configFile.delete();
    }

    public List<String> getConfigurationNames() {
        List<String> names = new ArrayList<>(configurations.keySet());
        Collections.sort(names);
        return names;
    }

    public boolean importConfiguration(File file) {
        try {
            EnhancedConfiguration config = loadConfigurationFromFile(file);
            if (config != null) {
                configurations.put(config.getName(), config);
                saveConfigurationToFile(config);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportConfiguration(String name, File file) {
        EnhancedConfiguration config = configurations.get(name);
        if (config == null) {
            return false;
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(config);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadConfigurations() {
        File configDir = new File(CONFIG_DIR);
        if (!configDir.exists()) {
            configDir.mkdir();
            return;
        }

        File[] configFiles = configDir.listFiles((dir, name) -> name.endsWith(".cfg"));
        if (configFiles == null) {
            return;
        }

        for (File file : configFiles) {
            EnhancedConfiguration config = loadConfigurationFromFile(file);
            if (config != null) {
                configurations.put(config.getName(), config);
            }
        }
    }

    private EnhancedConfiguration loadConfigurationFromFile(File file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (obj instanceof EnhancedConfiguration) {
                return (EnhancedConfiguration) obj;
            } else if (obj instanceof Configuration) {
                // 向后兼容旧版本的配置文件
                Configuration oldConfig = (Configuration) obj;
                EnhancedConfiguration newConfig = new EnhancedConfiguration();
                newConfig.setName(oldConfig.getName());

                // 转换项目列表
                List<SelectionItem> items = new ArrayList<>();
                String[] lines = oldConfig.getItems().split("\n");
                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        items.add(new SelectionItem(line, 1));
                    }
                }
                newConfig.setItems(items);

                newConfig.setNumberToSelect(oldConfig.getNumberToSelect());
                newConfig.setAllowRepeat(oldConfig.isAllowRepeat());
                return newConfig;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveConfigurationToFile(EnhancedConfiguration config) {
        File configDir = new File(CONFIG_DIR);
        if (!configDir.exists()) {
            configDir.mkdir();
        }

        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(CONFIG_DIR + File.separator + config.getName() + ".cfg"))) {
            out.writeObject(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class SelectionItem implements Serializable {
    private static final long serialVersionUID = 2L;

    private String name;
    private int weight;

    public SelectionItem(String name, int weight) {
        this.name = name;
        this.weight = Math.max(1, weight); // 确保权重至少为1
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectionItem that = (SelectionItem) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class EnhancedConfiguration implements Serializable {
    private static final long serialVersionUID = 3L;

    private String name;
    private List<SelectionItem> items;
    private int numberToSelect;
    private boolean allowRepeat;
    private boolean enableCooldown;
    private int cooldownCount;
    private boolean useWeightedSelection;
    private String selectedAlgorithm;

    public EnhancedConfiguration() {
        items = new ArrayList<>();
        numberToSelect = 1;
        cooldownCount = 3;
        selectedAlgorithm = "完全随机";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SelectionItem> getItems() {
        return items;
    }

    public void setItems(List<SelectionItem> items) {
        this.items = items;
    }

    public int getNumberToSelect() {
        return numberToSelect;
    }

    public void setNumberToSelect(int numberToSelect) {
        this.numberToSelect = numberToSelect;
    }

    public boolean isAllowRepeat() {
        return allowRepeat;
    }

    public void setAllowRepeat(boolean allowRepeat) {
        this.allowRepeat = allowRepeat;
    }

    public boolean isEnableCooldown() {
        return enableCooldown;
    }

    public void setEnableCooldown(boolean enableCooldown) {
        this.enableCooldown = enableCooldown;
    }

    public int getCooldownCount() {
        return cooldownCount;
    }

    public void setCooldownCount(int cooldownCount) {
        this.cooldownCount = cooldownCount;
    }

    public boolean isUseWeightedSelection() {
        return useWeightedSelection;
    }

    public void setUseWeightedSelection(boolean useWeightedSelection) {
        this.useWeightedSelection = useWeightedSelection;
    }

    public String getSelectedAlgorithm() {
        return selectedAlgorithm;
    }

    public void setSelectedAlgorithm(String selectedAlgorithm) {
        this.selectedAlgorithm = selectedAlgorithm;
    }
}

// 为兼容旧版本保留的类
class Configuration implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String items;
    private int numberToSelect;
    private boolean allowRepeat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public int getNumberToSelect() {
        return numberToSelect;
    }

    public void setNumberToSelect(int numberToSelect) {
        this.numberToSelect = numberToSelect;
    }

    public boolean isAllowRepeat() {
        return allowRepeat;
    }

    public void setAllowRepeat(boolean allowRepeat) {
        this.allowRepeat = allowRepeat;
    }
}
