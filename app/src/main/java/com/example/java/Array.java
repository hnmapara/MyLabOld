package com.example.java;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mapara on 9/9/17.
 */

public class Array {
    public static void main(String[] args) {
        getSubSeqDivisibleByk();
    }

    static void mergeSortedArrayInPlace() {
        int arr1[] = new int[]{1, 5, 9, 10, 15, 20};
        int arr2[] = new int[]{2, 3, 8, 13};
        int m = arr1.length;
        int n = arr2.length;

        // Iterate through all elements of ar2[] starting from
        // the last element
        for (int i = n - 1; i >= 0; i--) {
            /* Find the smallest element greater than ar2[i]. Move all
               elements one position ahead till the smallest greater
               element is not found */
            int j, last = arr1[m - 1];
            for (j = m - 2; j >= 0 && arr1[j] > arr2[i]; j--)
                arr1[j + 1] = arr1[j];

            // If there was a greater element
            if (j != m - 2 || last > arr2[i]) {
                arr1[j + 1] = arr2[i];
                arr2[i] = last;
            }
        }

        System.out.println("After Merging");
        System.out.println("arr1 : " + Arrays.toString(arr1));
        System.out.println("arr2 : " + Arrays.toString(arr2));
    }

    public static int maxSubArray() {
        int[] arr = { -2, -4, 3, -1, 2, -1 };
        int max = arr[0];
        int cmax = arr[0];
        for (int x : arr) {
            cmax = cmax + x;
            if (cmax < 0) cmax = 0;
            max = Math.max(max, cmax);
        }
        System.out.println("max sum window : " + max);
        return max;
    }

    public static int getSubSeqDivisibleByk() {
        int k = 3;
        int[] arr = {1,2,3,4};  //output -> 4 ( i.e {1,2}, {3}, {1,2,3}, {2,3,4}

        int count = 0;
        int j=0;

        int sumsofar = 0;

        while (j < arr.length) {
            if (arr[j] % k == 0) { count++;}
            sumsofar = sumsofar + arr[j];
            j++;

            if (sumsofar % k == 0) { count++; }
            
        }
        System.out.println("getSubSeqDivisibleByk : " + count);

        return count;

    }
}
