import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_12865_MW {

    static int n, k;
    static int[] w;
    static int[] v;
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] s = br.readLine().split(" ");
        n = Integer.parseInt(s[0]);
        k = Integer.parseInt(s[1]);

        w = new int[n+1];
        v = new int[n+1];

        for(int i=1; i<=n; i++) {
            s = br.readLine().split(" ");
            w[i] = Integer.parseInt(s[0]);
            v[i] = Integer.parseInt(s[1]);
        }

        int[][] d = new int[n+1][k+1]; // d[i][j]: i번째 물건까지 고려했을 때 무게 j로 담을 수 있는 배낭의 최대 가치
        for(int i=1; i<=n; i++) {
            for(int weight=1; weight<=k; weight++) {

                if(weight < w[i]) { // i번째 물건을 배낭에 물건을 넣을 수 없으면
                    d[i][weight] = d[i-1][weight]; // 이전 가방의 같은 무게의 가치를 저장
                    continue;
                }

                // i번째 물건을 배낭에 넣을 수 있으면
                d[i][weight] = Math.max(d[i-1][weight-w[i]] + v[i], d[i-1][weight]);
                // i번째 물건을 추가하는 경우, 추가하지 않는 경우 중 최대값을 저장
            }
        }
        System.out.println(d[n][k]);
    }
}
