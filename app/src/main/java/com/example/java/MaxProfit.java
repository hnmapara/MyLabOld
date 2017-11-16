package com.example.java;

/**
 * Created by mapara on 2/26/17.
 */

public class MaxProfit {
    public static void main(String[] args) {
        int k = 2;
        int price[] = {10, 22, 5, 75, 65, 80};
        //output : 87
        System.out.println("Maximum Profit = " + maxProfit(price, price.length, k));
    }

    static int maxProfit(int price[], int n, int k) {
        // table to store results of subproblems
        // profit[t][i] stores maximum profit using
        // atmost t transactions up to day i (including
        // day i)
        int profit[][] = new int[k + 1][n + 1];

        // For day 0, you can't earn money
        // irrespective of how many times you trade
        for (int i = 0; i <= k; i++)
            profit[i][0] = 0;

        // profit is 0 if we don't do any transation
        // (i.e. k =0)
        for (int j = 0; j <= n; j++)
            profit[0][j] = 0;

        // fill the table in bottom-up fashion
        for (int i = 1; i <= k; i++) {
            for (int j = 1; j < n; j++) {
                int max_so_far = Integer.MIN_VALUE;

                /*
                profit[t][i] = max(profit[t][i-1], max(price[i] – price[j] + profit[t-1][j]))
                 for all j in range [0, i-1]

                profit[t][i] will be maximum of –

                    profit[t][i-1] which represents not doing any transaction on the ith day.
                    Maximum profit gained by selling on ith day. In order to sell shares on ith day,
                    we need to purchase it on any one of [0, i – 1] days. If we buy shares on jth day and sell it on ith day,
                    max profit will be price[i] – price[j] + profit[t-1][j] where j varies from 0 to i-1. Here profit[t-1][j] is best
                    we could have done with one less transaction till jth day.
                 */
                for (int m = 0; m < j; m++) {
                    max_so_far = Math.max(max_so_far,
                            price[j] - price[m] + profit[i - 1][m]);
                }
                profit[i][j] = Math.max(profit[i][j - 1], max_so_far);
            }
        }

        return profit[k][n - 1];
    }
}