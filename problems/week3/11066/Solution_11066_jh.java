package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_11066_jh {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            int N = Integer.parseInt(br.readLine()); // 요소의 수
            StringTokenizer st = new StringTokenizer(br.readLine());

            int[] arr = new int[N + 1];
            int[] sum = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                int temp = Integer.parseInt(st.nextToken());
                arr[i] = temp;
                sum[i] = sum[i - 1] + temp;
            }

            int[][] DP = new int[N + 1][N + 1];
            for (int length = 1; length < N; length++) { // 부분 배열의 길이
                for (int i = 1; i + length <= N; i++) { // 시작 위치(I+LENGTH로 벗어나지 않게)
                    int j = i + length; // 범위의 끝
                    DP[i][j] = 987654321;
                    for (int k = i; k < j; k++) { // 하나씩 범위의 끝을 지정하여 모든 경우 중 가장 작은 수 출력
                        DP[i][j] = Math.min(DP[i][j], DP[i][k] + DP[k + 1][j] + sum[j] - sum[i - 1]);
                    }
                }
            }
            System.out.println(DP[1][N]);
        }
    }
}
