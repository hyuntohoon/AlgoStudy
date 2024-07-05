package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_4991_jh {
    static int []dx = {0,0,-1,1};
    static int []dy = {1,-1,0,0};
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());
            int N = Integer.parseInt(st.nextToken());
            if(M == 0 && N == 0) break;
            int[][] map = new int[N + 1][M + 1];
            int dustIndex = 0;
            int[] start = new int[2];
            int dustCount = 0;
            for (int i = 0; i < N; i++) {
                String temp = br.readLine();
                for (int j = 0; j < M; j++) {
                    map[i][j] = 0;
                    if (temp.charAt(j) == '*') {
                        map[i][j] = ++dustIndex;
                        dustCount++;
                    } else if (temp.charAt(j) == 'o') {
                        start[0] = i;
                        start[1] = j;
                    } else if (temp.charAt(j) == 'x') map[i][j] = -1;
                }
            }

            Queue<Robot> Q = new LinkedList<>();
            Q.add(new Robot(start[0], start[1], 0, 0));
            boolean[][][] visit = new boolean[N][M][1 << 10];
            int ans = -1;
            while (!Q.isEmpty()) {
                Robot currentRobot = Q.poll();
                if (currentRobot.dustMask == (1 << dustCount) - 1) {
                    ans = currentRobot.move;
                    break;
                }
                for (int i = 0; i < 4; i++) {
                    int nx = currentRobot.x + dx[i];
                    int ny = currentRobot.y + dy[i];
                    if (nx >= 0 && ny >= 0 && nx < N && ny < M && map[nx][ny] != -1) {
                        int currentDust = currentRobot.dustMask;
                        if (map[nx][ny] > 0) {
                            currentDust |= (1 << (map[nx][ny] - 1));
                        }
                        if (!visit[nx][ny][currentDust]) {
                            visit[nx][ny][currentDust] = true;
                            Q.add(new Robot(nx, ny, currentDust, currentRobot.move + 1));
                        }
                    }

                }
            }
            System.out.println(ans);
        }
    }
    static class Robot{
        int x,y, dustMask,move;

        public Robot(int x, int y, int dustCount, int move) {
            this.x = x;
            this.y = y;
            this.dustMask = dustCount;
            this.move = move;
        }
    }
}
