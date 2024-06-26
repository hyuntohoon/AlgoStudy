package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Math.pow;

public class Solution_MW {

    static int n, k;
    static int[][] d;
    static final int mod = 1000000007;
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        n = Integer.parseInt(s[0]);
        k = Integer.parseInt(s[1]);

        long numerator = factorial(n);
        long divisor = (factorial(k) * factorial(n-k)) % mod;

        long inverse = myPow(divisor, mod-2);

        System.out.println(numerator * inverse % mod);
    }

    static Long factorial(int n) {

        long fact = 1L;

        while (n > 1) {
            fact = (n * fact) % mod;
            n--;
        }
        return fact % mod;
    }

    static Long myPow(Long base, int p) {

        if(p == 1) {
            return base % mod;
        }

        long tmp = myPow(base, p/2);

        if(p % 2 == 0) { // 짝수 승일 때
            return tmp * tmp % mod;
        } else {
            return (base * tmp % mod) * tmp % mod;
        }
    }
}
