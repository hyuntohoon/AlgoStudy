package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_11050_MW {

    static int n, k;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        n = Integer.parseInt(s[0]);
        k = Integer.parseInt(s[1]);

        System.out.println(BC(n, k));
    }

    static int BC(int n, int k) {

        if(n == k || k == 0) {
            return 1;
        }

        return BC(n-1, k-1) + BC(n-1, k);
    }
}
