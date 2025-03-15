package com.jigsaw.game;

import java.util.Scanner;
import java.util.Random;

public class Game2048 {
    private static final int SIZE = 4; // 游戏板大小
    private static int[][] board = new int[SIZE][SIZE]; // 游戏板
    private static boolean gameOver = false; // 游戏是否结束
    private static Random random = new Random();

    public static void main(String[] args) {
        initializeBoard();
        printBoard();

        while (!gameOver) {
            System.out.println("Enter move (w = up, a = left, s = down, d = right): ");
            Scanner scanner = new Scanner(System.in);
            String move = scanner.nextLine();
            boolean moved = false;

            switch (move) {
                case "w":
                    moved = moveUp();
                    break;
                case "a":
                    moved = moveLeft();
                    break;
                case "s":
                    moved = moveDown();
                    break;
                case "d":
                    moved = moveRight();
                    break;
                default:
                    System.out.println("Invalid move! Please enter 'w', 'a', 's', or 'd'.");
                    continue;
            }

            if (moved) {
                addRandomTile(); // 增加一个新的随机方块
                printBoard(); // 打印更新后的游戏板
                checkGameOver(); // 检查游戏是否结束
            }
        }
        System.out.println("Game Over!");
    }

    // 初始化游戏板
    private static void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }
        addRandomTile();
        addRandomTile();
    }

    // 打印游戏板
    private static void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] == 0 ? "." : board[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    // 随机添加一个2或4
    private static void addRandomTile() {
        int row, col;
        do {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE);
        } while (board[row][col] != 0);

        // 50% chance of adding a 4, otherwise add a 2
        board[row][col] = random.nextInt(2) == 0 ? 2 : 4;
    }

    // 判断游戏是否结束
    private static void checkGameOver() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) return; // 还有空格，游戏未结束
                if (i < SIZE - 1 && board[i][j] == board[i + 1][j]) return; // 可以合并
                if (j < SIZE - 1 && board[i][j] == board[i][j + 1]) return; // 可以合并
            }
        }
        gameOver = true; // 没有空格且没有可以合并的方块，游戏结束
    }

    // 向左移动
    private static boolean moveLeft() {
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
    private static boolean moveRight() {
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
    private static boolean moveUp() {
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
    private static boolean moveDown() {
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
    private static int[] slideAndMerge(int[] line) {
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
    private static int[] getColumn(int col) {
        int[] column = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            column[i] = board[i][col];
        }
        return column;
    }

    // 设置某列
    private static void setColumn(int col, int[] newCol) {
        for (int i = 0; i < SIZE; i++) {
            board[i][col] = newCol[i];
        }
    }

    // 反转数组
    private static int[] reverseArray(int[] array) {
        int[] reversed = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            reversed[i] = array[SIZE - 1 - i];
        }
        return reversed;
    }

    // 判断两个数组是否相等
    private static boolean arraysEqual(int[] arr1, int[] arr2) {
        for (int i = 0; i < SIZE; i++) {
            if (arr1[i] != arr2[i]) return false;
        }
        return true;
    }
}
