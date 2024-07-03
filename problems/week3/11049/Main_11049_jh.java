package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_11049_jh {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int N = Integer.parseInt(br.readLine()); // 요소의 수
            int [][]matrix = new int[N+1][2];
            int [][]DP = new int[N+1][N+1];
            for(int i = 0;i<=N;i++){
                for(int j =0;j<=N;j++){
                    DP[i][j] = Integer.MAX_VALUE;
                }
            }
            for(int i=0;i<N;i++){
                StringTokenizer st = new StringTokenizer(br.readLine());
                matrix[i][0] = Integer.parseInt(st.nextToken());
                matrix[i][1] = Integer.parseInt(st.nextToken());
            }
        for(int i = 0; i < N; i++) {
            DP[i][i] = 0;
        }
            for(int length = 1; length<N;length++){
                for(int i =0;i<N-length;i++){
                    int j = i+length;
                    for(int mid = i;mid<j;mid++){
                        DP[i][j] = Math.min(DP[i][j], DP[i][mid] + DP[mid+1][j] + matrix[i][0]*matrix[mid][1]*matrix[j][1]);
                    }
                }
            }
            System.out.println(DP[0][N-1]);
    }
}
