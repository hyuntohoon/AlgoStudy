package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_MW {

    static int n;
    static long b;
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader( System.in));
        String[] s = br.readLine().split(" ");

        n = Integer.parseInt(s[0]);
        b = Long.parseLong(s[1]);

        int[][] A = new int[n][n];
        for(int i=0; i<n; i++) {
            s = br.readLine().split(" ");
            for(int j=0; j<n;  j++) {
                A[i][j] = Integer.parseInt(s[j]);
            }
        }

        int[][] res = matrixPow(A, b);
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }
    }

    static int[][] matrixPow(int[][] B, long p) {

        if(p == 1) {
            for(int i=0; i<n; i++) {
                for(int j=0; j<n; j++) {
                    B[i][j] = B[i][j] % 1000;
                }
            }
            return B;
        }

        int[][] tmp = matrixPow(B, p/2);
        if(p % 2 == 0) { // 짝수승일 때
            return matrixMul(tmp, tmp);
        } else {
            return matrixMul(matrixMul(tmp, tmp), B);
        }
    }

    static int[][] matrixMul(int[][] a, int[][] b) {
        int[][] res = new int[n][n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                for(int k=0; k<n; k++) {
                    res[i][j] += ((a[i][k] * b[k][j]) % 1000);
                    res[i][j] %= 1000;
                }
            }
        }
        return res;
    }
}
