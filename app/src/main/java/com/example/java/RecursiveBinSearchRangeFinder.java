package com.example.java;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by mapara on 11/21/16.
 */
/*
          0 1 2 3 4 5 6 7 8

   i/p  = 1 1 3 3 3 3 3 3 9 , k =3
   o/p  = [2,7] in logn

          0 1 2 3 4 5 6 7 8,9
   i/p  = 1,1,3,3,3,3,3,8,9,9  k =3
   o/p  = [2,6] in logn
 */
public class RecursiveBinSearchRangeFinder {
    static int min = -1;
    static int max = -1;
    public static void main(String[] args) {
        int[] ip = new int[]{1, 1, 3, 3, 3, 3, 3, 3, 9};
        printRange(ip, 0, ip.length-1, 3);
    }

    public static void printRange(int[] arr, int low, int high, int k) {

        while(low <= high) {
            int mid = low + (high-low)/2;
            if (k == arr[mid]) {
                min = min == -1 ? mid : Math.min(mid, min);
                max = max == -1 ? mid : Math.max(mid, max);

                System.out.println("[" + min + "," + max + "]");
                printRange(arr, low, mid - 1, k);
                printRange(arr, mid + 1, high, k);
                return;
            } else if (k > arr[mid]) {
                low = mid + 1;
            } else if (k < arr[mid]) {
                high = mid -1;
            }
        }
    }
}

/*
1 3 3 3 3 3 3 3 9

        1 3 3 3 3    3 3 3 9

        1 3 3    3 3


        int[] arr = [1,1,3,3,3,3,3,8,9,9]

        input: int k = 1
        output: [0,1]

        input: int k = 3
        output: [2,6]

        input: int k = 8
        output: [7,7]

        input: int k = 5
        output: [-1,-1]

        int min, max
        int k
public void printRange(int[] arr, int low, int high) {

        int mid = low + (high-low)/2;
        if (arr[mid] == k) {
        min = Math.min(mid, min);
        max = Math.max(mid, max);
        printRange(arr, low, mid -1);
        printRange(arr, mid +1, high);
        } else if (arr[mid] > k) {
        high = mid -1;
        } else if (arr[mid] < k) {
        low = mid;
        }
        }
*/