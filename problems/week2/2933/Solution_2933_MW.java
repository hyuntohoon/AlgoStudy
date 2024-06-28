package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class Solution_2933_MW {

    static class Mineral implements Comparable<Mineral> {
        public int y, x;

        public Mineral(int y, int x, int num) {
            this.y = y;
            this.x = x;
        }

        @Override
        public int compareTo(Mineral o) {
            if(this.y == o.y) {
                return this.x - o.x;
            }
            return o.y - this.y;
        }

        @Override
        public String toString() {
            return this.y + " " + this.x;
        }
    }

    static int r, c;
    static char[][] board;
    static int n;
    static int[] h;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] s = br.readLine().split(" ");
        r = Integer.parseInt(s[0]);
        c = Integer.parseInt(s[1]);

        board = new char[r][c];
        for(int i=0; i<r; i++) {
            String line = br.readLine();
            for(int j=0; j<c; j++) {
                board[i][j] = line.charAt(j);
            }
        }
        n = Integer.parseInt(br.readLine());

        h = new int[n];
        s = br.readLine().split(" ");
        for(int i=0; i<n; i++) {
            h[i] = Integer.parseInt(s[i]);
        }

        /*
        처음 생각한 풀이
         1. 왼쪽/오른쪽에서 던져 해당 높이의 미네랄을 없앤다.
         2. 미네랄이 분리 되었는지 판단
           - 2중 for문으로 x가 나오면 BFS를 통해 클러스터 결정
           - 만약 바닥과 연결된 미네랄이 없다면 맨 아래 행인 클러스터부터 떨어뜨려서,
           - 다른 미네랄에 떨어지거나 바닥에 떨어질때 까지 반복
         3. 분리된 미네랄을 아래로 이동 시킴
           - 새롭게 생성된 클러스터가 떠있는 경우 중력에 의해 바닥으로 떨어지게 된다.
           - 클러스터는 다른 클러스터나 땅을 만나기 전까지 계속해서 떨어진다.
         */


        for(int t=0; t<n; t++) {
            int height = r - h[t];
            if (t % 2 == 0) { // 왼쪽에서 던짐
                for (int j = 0; j < c; j++) {
                    if (board[height][j] == 'x') {
                        board[height][j] = '.';
                        break;
                    }
                }
            } else {
                for (int j = c - 1; j >= 0; j--) {
                    if (board[height][j] == 'x') {
                        board[height][j] = '.';
                        break;
                    }
                }
            }

            boolean[] isBottom = new boolean[r * c];

            Queue<Mineral> q = new ArrayDeque<>();
            List<Mineral> minerals = new ArrayList<>();
            int num = 1;
            boolean[][] vis = new boolean[r][c];
            int[][] mineralNum = new int[r][c];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {

                    if (board[i][j] == '.' || vis[i][j]) continue;
                    q.add(new Mineral(i, j, num));
                    mineralNum[i][j] = num;
                    vis[i][j] = true;

                    // BFS로 하나의 연결된 클러스트 결정
                    while (!q.isEmpty()) {
                        Mineral cur = q.poll();
                        int y = cur.y;
                        int x = cur.x;
                        minerals.add(cur);
//                        System.out.println(cur.y + " " + cur.x);
                        if (cur.y == r - 1) {
                            isBottom[mineralNum[y][x]] = true;
                        }
                        for (int dir = 0; dir < 4; dir++) {
                            int ny = cur.y + dy[dir];
                            int nx = cur.x + dx[dir];
                            if (ny < 0 || ny >= r || nx < 0 || nx >= c) continue;
                            if (board[ny][nx] == '.' || vis[ny][nx]) continue;
                            q.add(new Mineral(ny, nx, num));
                            mineralNum[ny][nx] = num;
                            vis[ny][nx] = true;
                        }
                    }
                    num++;
                }
            }

//            System.out.println(t);
//            System.out.println(minerals);

            // 클러스터를 몇 칸 내려야 하는지 세기
            Collections.sort(minerals);
            for (int i = 1; i < num; i++) { // i번 클러스터에 대해 진행

                if(isBottom[i]) continue;

                int mn = Integer.MAX_VALUE;
                for (Mineral mineral : minerals) { // 클러스터마다 몇 번 내려야 하는지 구하기 => 클러스터에 속한 미네랄 중 가장 먼저 바닥이나 다른 미네랄에 닿을 때 까지.
                    int y = mineral.y;
                    int x = mineral.x;
                    if (mineralNum[y][x] != i) {
                        continue;
                    }

                    int diff = 0;
                    for (int j = mineral.y + 1; j < r; j++) {
                        if (board[j][mineral.x] == '.') {
                            diff++;
                        } else if(board[j][mineral.x] == 'x' && mineralNum[y][x] != mineralNum[j][mineral.x]){
                            break;
                        }
                    }
                    mn = Math.min(mn, diff);
                }

                for (Mineral mineral : minerals) { // i번 클러스터를 구한 횟수만큼 내리기. 행으로 정렬되어 있으므로 아래부터 움직임.
                    int y = mineral.y;
                    int x = mineral.x;
                    if (mineralNum[y][x] != i) {
                        continue;
                    }

                    for (int j = 0; j < mn; j++) {
                        board[mineral.y][mineral.x] = '.';
                        mineral.y += 1;
                        board[mineral.y][mineral.x] = 'x';
                    }
                }
            }
        }

        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}
