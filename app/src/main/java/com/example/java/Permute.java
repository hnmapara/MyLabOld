package com.example.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by mapara on 10/1/15.
 */
public class Permute {
    public static void main(String[] args) {
        m3();
    }

    static void m1() {
//        ArrayList<ArrayList<Integer>> result = permute(new int[] {1,2,3});
//        System.out.println("-------");
//        for (ArrayList<Integer> indi : result ) {
//            System.out.println(indi);
//        }

        permutationNonRec("abcd");
    }

    static void m2() {
        byte[] arr = {100,6,1, (byte) 255};
        processM2(arr);
    }

    static void m3() {
        final Work work = new Work("pre", "fix");
        new Thread(new Runnable() {
            @Override
            public void run() {
              work.m2();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                work.m1();
            }
        }).start();
    }

    static void processM2(byte[] arr) {
        for (int i : arr) {
            System.out.println(i);
        }
    }
    public  static ArrayList<ArrayList<Integer>> permute(int[] num) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        //start from an empty list
        result.add(new ArrayList<Integer>());

        for (int i = 0; i < num.length; i++) {
            //list of list in current iteration of the array num
            ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();

            for (ArrayList<Integer> l : result) {
                // # of locations to insert is largest index + 1
                for (int j = 0; j < l.size()+1; j++) {
                    // + add num[i] to different locations
                    l.add(j, num[i]);

                    ArrayList<Integer> temp = new ArrayList<Integer>(l);
                    current.add(temp);

//                    System.out.print(current + " -> ");

                    // - remove num[i] add
                    int rn = l.remove(j);
//                    System.out.print(rn);
//                    System.out.println();
                    System.out.println(current);
                    System.out.println("----");

                }
            }

            result = new ArrayList<ArrayList<Integer>>(current);
        }

        return result;
    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) System.out.println(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
    }

    private static void permutationNonRec(String str) {
        Stack<Work> myStack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            Work work = new Work("" + str.charAt(i), str.substring(0, i) + str.substring(i + 1, str.length()));
            myStack.push(work);
            do {
                Work w = myStack.peek();
                if (w.prefix.length() == str.length()) {
                    System.out.println(w.prefix);
                    myStack.pop();
                    myStack.pop();
                } else {
                    for (int m = 0; m < w.str.length(); m++) {
                        Work subwork = new Work(w.prefix + w.str.charAt(m), w.str.substring(0, m) + w.str.substring(m + 1, w.str.length()));
                        myStack.push(subwork);
                    }
                }
            } while(myStack.size() > 1);

            myStack.clear();
        }

    }

    private static class Work{
        String prefix;
        String str;

        public Work(String prefix, String str) {
            this.prefix = prefix;
            this.str = str;
        }

         public void m1() {
            System.out.println("m1");
        }

        synchronized public void m2() {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("m2");
        }
    }

}
