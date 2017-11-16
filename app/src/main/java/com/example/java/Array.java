package com.example.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Created by mapara on 9/9/17.
 */

public class Array {
    public static void main(String[] args) {
        //getSubSeqDivisibleByk();
        maxSubArray();
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
        //int[] arr = { -2, -4, 3, -1, 2, -1 };
        int[] arr = { -2, -4, -3, -1, -2, -7 };
        int max = arr[0];
        int cmax = arr[0];
//        for (int x : arr) {
//            cmax = cmax + x;
//            if (cmax < 0) cmax = 0;
//            max = Math.max(max, cmax);
//        }
        for (int i = 1; i < arr.length; i++) {
            cmax = Math.max(arr[i], cmax + arr[i]);
            max = Math.max(max, cmax);
        }
        System.out.println("max sum window : " + max);
        return max;
    }

    public static int getSubSeqDivisibleByk() {
        //NOT WORKING. SEE NEXT SOLUTION
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

    public static int getSubSeqDivisibleBykWorking() {
//        int k = 3;
//        int[] nums = {1,2,3,4};  //output -> 4 ( i.e {1,2}, {3}, {1,2,3}, {2,3,4}
        int k = 2;
        int[] nums = {1, 2, 1, 2, 1, 2};

        int count = 0;
        int [] sum = new int[nums.length];
        sum[0] = nums[0];

        for(int i = 1; i < nums.length; i++){
            sum[i] = sum[i-1] + nums[i];
        }

        int [] kVal = new int[k];

        for(int i = 0; i < sum.length; i++){
            int mod = sum[i] % k;

            if(mod == 0)
                count++;

            count += kVal[mod];
            kVal[mod] += 1;

        }
        System.out.println(count);
        return count;
    }

    public static boolean hasSumOfSubSeqDivisibleByk() {
        int arr[] = {23, 2, 6, 4, 7};
        int k = 6;
        int minLengthOfNum = 2;

        /*
        (a+(n*x))%x is same as (a%x)

        For e.g. in case of the array [23,2,6,4,7] the running sum is [23,25,31,35,42] and the remainders are [5,1,1,5,0].
        We got remainder 5 at index 0 and at index 3. That means, in between these two indexes we must have added a number which is multiple of the k.
        Hope this clarifies your doubt :)
         */

        Map<Integer, Integer> mymap = new HashMap<>();
        int runningSum = 0;
        for (int i = 0; i< arr.length; i++) {
            runningSum = runningSum + arr[i];
            // If k == 0, that means we just need to check the runningsum as previuos value in map
            if (k != 0) runningSum = runningSum % k;
            Integer prev = mymap.get(runningSum);
            if (prev != null) {
                if (i - prev >= minLengthOfNum) return true;
            }
            mymap.put(runningSum, i);
        }
        return false;
    }
}
