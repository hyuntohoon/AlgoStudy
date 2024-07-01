package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class prob11066 {
    static final int INF = 1_000_000_000;
    static StringTokenizer st = null;
    static StringBuilder sb = new StringBuilder();
    static int[][] dpCost;
    static int[][] dpSize;
    static int[] arr;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            int n = Integer.parseInt(br.readLine());
            dpCost = new int[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(dpCost[i], INF);
            }

            dpSize = new int[n][n];

            arr = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }

            dpCost[0][n - 1] = DP(0, n - 1);
            sb.append(dpCost[0][n - 1]).append("\n");
        }

        System.out.println(sb.toString());
    }

    private static int DP(int left, int right) {
        if (dpCost[left][right] != INF) {
            return dpCost[left][right];
        }

        if (left == right) {
            dpSize[left][right] = arr[left];
            return dpCost[left][right] = 0;
        }

        for (int idx = left; idx < right; idx++) {
            int file1 = DP(left, idx);
            int file2 = DP(idx + 1, right);
            int newFile = file1 + file2 + dpSize[left][idx] + dpSize[idx + 1][right];

            if (dpCost[left][right] > newFile) {
                dpCost[left][right] = newFile;
                dpSize[left][right] = dpSize[left][idx] + dpSize[idx + 1][right];
            }
        }

        return dpCost[left][right];
    }
}
