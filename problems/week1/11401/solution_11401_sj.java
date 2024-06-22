package baekjoon;

import java.util.Scanner;

public class prob11401 {
    static final int MOD = 1_000_000_007;
    static long[] fact = new long[4000001];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();

        fact[0] = 1;
        for (int i = 1; i <= N; i++) {
            fact[i] = fact[i - 1] * i;
            fact[i] %= MOD;
        }

        long bunja = fact[N];
        long bunmo = fact[N - K] * fact[K];
        bunmo %= MOD;

        long result = bunja * expend(bunmo, MOD - 2);
        System.out.println(result % MOD);
    }

    private static long expend(long n, long p) {
        long ret = 1;
        while (p > 0) {
            if (p % 2 == 1) {
                ret *= n;
                ret %= MOD;
                p--;
            } else {
                n *= n;
                n %= MOD;
                p /= 2;
            }
        }

        return ret;
    }
}
