package com.example.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by mapara on 9/5/16.
 */

public class SpiralMatrix {
    public static void main(String[] args) {
        printSpiral2();
    }

    public static void printSpiralMatrix() {
        //        int input[][] = {
//                        {1,  2,  3,  4},
//                        {5,  6,  7,  8},
//                        {9,  10, 11, 12},
//                        {13, 14, 15, 16}
//                        };

        int input[][] = {
                {1,   2,   3,   4,  5,   6},
                {7,   8,   9,  10,  11,  12},
                {13,  14,  15, 16,  17,  18}
        };
        int xLength = input.length;
        int yLength = input[0].length;
        List<Integer> visited = new ArrayList<>(xLength * yLength);

        int x=0;
        int y=0;

        char cons = 'x';
        int xup = 1; //1 or -1
        int yup = 1; //1 or -1
        while(visited.size() != (xLength*yLength)) {
            if (!visited.contains(input[x][y])) {
//                System.out.println(input[x][y]);
                visited.add(input[x][y]);
                if (cons == 'y') {
                    if (xup == 1) {
                        x = x + 1;
                        if (x == xLength || visited.contains(input[x][y])) {
                            x = x - 1;
                            y = y + yup;
                            cons = 'x';
                            xup = -1;
                        }
                    } else {
                        x = x - 1;
                        if (x == -1 || visited.contains(input[x][y])) {
                            x = x + 1;
                            y = y + yup;
                            cons = 'x';
                            xup = 1;
                        }
                    }
                } else {
                    if (yup == 1) {
                        y = y + 1;
                        if (y == yLength || visited.contains(input[x][y])) {
                            y = y - 1;
                            x = x + xup;
                            cons = 'y';
                            yup = -1;
                        }
                    } else {
                        y = y - 1;
                        if (y == -1 || visited.contains(input[x][y])) {
                            y = y + 1;
                            x = x + xup;
                            cons = 'y';
                            yup = 1;
                        }
                    }
                }
            }
        }

        for(int val : visited) {
            System.out.print(val + " ");
        }
    }

    public static List<String> letterCombinations(String digits) {
        // call this method with : List<String> list = letterCombinations("23");
        //        for (String s: list) {
        //            System.out.println(s);
        //        }
        LinkedList<String> ans = new LinkedList<String>();
        String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        for(int i =0; i<digits.length();i++){
            int x = Character.getNumericValue(digits.charAt(i));
            while(ans.peek().length()==i){
                String t = ans.remove();
                for(char s : mapping[x].toCharArray()) {
                    System.out.println(t+s);
                    ans.add(t + s);
                }
            }
        }
        return ans;
    }

    static void printSpiral2() {
                int input[][] = {
                        {1,  2,  3,  4},
                        {5,  6,  7,  8},
                        {9,  10, 11, 12},
                        {13, 14, 15, 16}
                        };
//        int[][] input = new int[][] {
//                {1,   2,   3,   4,  5,   6},
//                {7,   8,   9,  10,  11,  12},
//                {13,  14,  15, 16,  17,  18}};

        int yLength = input.length; //On y-axis
        int xLength = input[0].length; //on x-axis
        int xInit = -1;
        int yInit = -1;

        int max = xLength*yLength;
        int right = 1, down = 2, left = 3, up = 4;
        int dir = right;
        int sofar = 0;
        int x = 0, y=0;
        System.out.print(input[y][x] + ",");
        sofar++;
        while (sofar < max) {

            if (dir == right) {
                x = x +1;
                if (x == xLength) {
                    x = x - 1;
                    dir = down;
                    yInit++; //Will make sure this row is covered and next up direction should not reach till here
                } else {
                    System.out.print(input[y][x] + ",");
                    sofar++;
                }
            } else if (dir == down) {
                y = y + 1;
                if (y == yLength) {
                    y = y -1;
                    dir = left;
                    xLength--; //Will make sure this column is covered and next right direction should not reach till here
                } else {
                    System.out.print(input[y][x] + ",");
                    sofar++;
                }
            } else if (dir == left) {
                x = x - 1;
                if (x == xInit) {
                    x = x + 1;
                    dir = up;
                    yLength--; //Will make sure this row is covered and next down direction should not reach till here
                } else {
                    System.out.print(input[y][x] + ",");
                    sofar++;
                }
            } else if (dir == up) {
                y = y - 1;
                if (y == yInit) {
                    y = y + 1;
                    dir = right;
                    xInit++; //Will make sure this column is covered and next left direction should not reach till here
                } else {
                    System.out.print(input[y][x] + ",");
                    sofar++;
                }
            }
        }

    }
}
