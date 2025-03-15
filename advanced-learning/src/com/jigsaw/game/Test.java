package com.jigsaw.game;

public class Test {
    public static void main(String[] args) {
        int[][] rightData = {{1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,0}};
        int[][] Data = {{1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,0}};
        boolean equalData = true;
        for (int i = 0; i < Data.length; i++) {
            for (int j = 0; j < Data[i].length; j++) {
                if (rightData[i][j] != Data[i][j]) {
                    equalData = false;
                }
            }
        }

        if (equalData) {
            System.out.println("Data is equal");
            System.exit(0);
        }
    }
}
