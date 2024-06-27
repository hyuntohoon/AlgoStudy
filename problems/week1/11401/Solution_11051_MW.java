package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_11051_MW {


    static int n, k;
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        n = Integer.parseInt(s[0]);
        k = Integer.parseInt(s[1]);
        int[][] d = new int[n+1][n+1];
        for(int i=0; i<=n; i++) {
            for(int j=0; j<=n; j++) {
                if(i == j || j == 0) {
                    d[i][j] = 1;
                }
            }
        }

        for(int i=2; i<=n; i++) {
            for(int j=1; j<n; j++) {
                d[i][j] = (d[i-1][j-1] % 10007 + d[i-1][j] % 10007) % 10007 ;
            }
        }

        System.out.println(d[n][k]);
    }
}
