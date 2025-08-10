package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();

        long[] a = new long[N + 1];
        for (int i = 1; i <= N; i++) {
            a[i] = sc.nextLong();
        }

        long[] dp = new long[N + 1];
        dp[0] = 0;
        long max = 0;

        for (int i = 1; i <= N; i++) {
            long sum = 0;
            for (int j = 1; j <= K && i - j + 1 >= 1; j++) {
                sum += a[i - j + 1];
                int prev = i - j - 1;
                long prevDp = prev >= 0 ? dp[prev] : 0;
                dp[i] = Math.max(dp[i], prevDp + sum);
            }
            max = Math.max(max, dp[i]);
        }

        System.out.println(max);
    }
}
