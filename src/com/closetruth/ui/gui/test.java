package com.closetruth.ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class test extends JFrame {
    private static final int GRID_SIZE = 4; // 4x4网格
    private static final int EMPTY_VALUE = 16; // 空白块的值

    private JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
    private int[][] puzzle = new int[GRID_SIZE][GRID_SIZE];
    private int emptyRow, emptyCol; // 空白块的位置
    private JLabel statusLabel;

    // 修复：构造函数名与类名一致
    public test() {
        initializeGame();
        setupGUI();
        shufflePuzzle();
    }

    private void initializeGame() {
        // 初始化拼图数组
        int value = 1;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                puzzle[i][j] = value++;
            }
        }
        // 最后一块设为空白
        puzzle[GRID_SIZE - 1][GRID_SIZE - 1] = EMPTY_VALUE;
        emptyRow = GRID_SIZE - 1;
        emptyCol = GRID_SIZE - 1;
    }

    private void setupGUI() {
        setTitle("数字华容道 - 4x4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout());

        // 创建主面板
        JPanel gamePanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 5, 5));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gamePanel.setBackground(Color.GRAY);

        // 创建按钮网格
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 20));
                button.setFocusPainted(false);
                button.addActionListener(new TileClickListener(i, j));

                buttons[i][j] = button;
                gamePanel.add(button);
            }
        }

        add(gamePanel, BorderLayout.CENTER);

        // 创建控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton shuffleButton = new JButton("重新洗牌");
        shuffleButton.addActionListener(e -> shufflePuzzle());

        statusLabel = new JLabel("点击方块移动，完成拼图！");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        controlPanel.add(shuffleButton);
        add(controlPanel, BorderLayout.NORTH);
        add(statusLabel, BorderLayout.SOUTH);

        updateButtons();
        pack();
        setLocationRelativeTo(null); // 居中显示
    }

    private void updateButtons() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (puzzle[i][j] == EMPTY_VALUE) {
                    buttons[i][j].setText("");
                    buttons[i][j].setEnabled(false);
                } else {
                    buttons[i][j].setText(String.valueOf(puzzle[i][j]));
                    buttons[i][j].setEnabled(true);
                    // 设置按钮颜色
                    buttons[i][j].setBackground(getColorForNumber(puzzle[i][j]));
                    buttons[i][j].setForeground(Color.WHITE);
                }
            }
        }
    }

    private Color getColorForNumber(int number) {
        // 为不同的数字分配不同的背景色
        switch (number % 8) {
            case 1: return new Color(255, 102, 102); // 红
            case 2: return new Color(255, 179, 102); // 橙
            case 3: return new Color(255, 255, 102); // 黄
            case 4: return new Color(128, 255, 102); // 浅绿
            case 5: return new Color(102, 255, 255); // 青
            case 6: return new Color(102, 153, 255); // 蓝
            case 7: return new Color(153, 102, 255); // 紫
            default: return new Color(255, 102, 179); // 粉
        }
    }

    private void shufflePuzzle() {
        // 执行多次随机移动来打乱拼图
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            List<int[]> possibleMoves = getPossibleMoves();
            if (!possibleMoves.isEmpty()) {
                int randomIndex = (int) (Math.random() * possibleMoves.size());
                int[] move = possibleMoves.get(randomIndex);
                swapTiles(move[0], move[1]);
            }
        }
        statusLabel.setText("拼图已重新洗牌，开始游戏吧！");
        updateButtons();
    }

    private List<int[]> getPossibleMoves() {
        List<int[]> possibleMoves = new ArrayList<>();

        // 检查四个方向是否可以移动
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 上下左右

        for (int[] dir : directions) {
            int newRow = emptyRow + dir[0];
            int newCol = emptyCol + dir[1];

            if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
                possibleMoves.add(new int[]{newRow, newCol});
            }
        }

        return possibleMoves;
    }

    private boolean isMoveValid(int row, int col) {
        // 检查点击的方块是否与空白块相邻
        return (Math.abs(row - emptyRow) + Math.abs(col - emptyCol)) == 1;
    }

    private void swapTiles(int row, int col) {
        // 将指定位置的方块与空白块交换
        puzzle[emptyRow][emptyCol] = puzzle[row][col];
        puzzle[row][col] = EMPTY_VALUE;
        emptyRow = row;
        emptyCol = col;
    }

    private boolean isPuzzleSolved() {
        int expectedValue = 1;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (i == GRID_SIZE - 1 && j == GRID_SIZE - 1) {
                    // 最后一个位置应该是空的
                    if (puzzle[i][j] != EMPTY_VALUE) return false;
                } else {
                    if (puzzle[i][j] != expectedValue) return false;
                    expectedValue++;
                }
            }
        }
        return true;
    }

    private class TileClickListener implements ActionListener {
        private int row, col;

        public TileClickListener(int r, int c) {
            this.row = r;
            this.col = c;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isMoveValid(row, col)) {
                swapTiles(row, col);
                updateButtons();

                if (isPuzzleSolved()) {
                    // 修复：使用正确的类名引用
                    JOptionPane.showMessageDialog(test.this,
                        "恭喜！你完成了数字华容道！", "胜利", JOptionPane.INFORMATION_MESSAGE);
                    statusLabel.setText("拼图已完成！点击重新洗牌继续游戏。");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 修复：使用正确的类名实例化
            new test().setVisible(true);
        });
    }
}
