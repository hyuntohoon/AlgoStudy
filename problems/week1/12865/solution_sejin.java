package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class prob12865_2 {
    static int N, K;
    static StringTokenizer st = null;
    static int[] dp;
    static int[] w;
    static int[] v;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dp = new int[K + 1];
        w = new int[N];
        v = new int[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            w[i] = Integer.parseInt(st.nextToken());
            v[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < N; i++) {
            for (int j = K; j >= 0; j--) {
                if (w[i] > j) {
                    continue;
                }

                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }
        }

        System.out.println(dp[K]);
    }
}
