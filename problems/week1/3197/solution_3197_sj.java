package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class prob3197 {
    static class xy {
        int x;
        int y;

        public xy(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }

    }

    static StringBuilder result = new StringBuilder();
    static StringTokenizer st = null;
    static int R;
    static int C;
    static char[][] board;
    static int x1 = -1, x2 = -1, y1 = -1, y2 = -1;
    static int cnt = 0;
    static int[] d_x = { -1, 0, 1, 0 };
    static int[] d_y = { 0, 1, 0, -1 };
    static Queue<xy> waterQ;
    static boolean[][] visited;
    static Queue<xy> swanQ;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        board = new char[R][C];
        waterQ = new ArrayDeque<>();
        visited = new boolean[R][C];
        swanQ = new ArrayDeque<>();

        // 입력
        for (int i = 0; i < R; i++) {
            String s = br.readLine();
            for (int j = 0; j < C; j++) {
                board[i][j] = s.charAt(j);
                if (board[i][j] == 'L') {
                    if (x1 == -1) {
                        x1 = i;
                        y1 = j;
                    } else {
                        x2 = i;
                        y2 = j;
                    }
                    board[i][j] = '.';
                    waterQ.add(new xy(i, j));
                }

                if (board[i][j] == '.') {
                    waterQ.add(new xy(i, j));
                }
            }
        }

        swanQ.add(new xy(x1, y1));
        visited[x1][y1] = true;

        while (!IsPossibleMeet()) {
            Melting();
            cnt++;
        }

        System.out.println(cnt);
    }

    private static void Melting() {
        int size = waterQ.size();

        for (int i = 0; i < size; i++) {
            xy cur = waterQ.poll();

            for (int j = 0; j < 4; j++) {
                int newX = cur.x + d_x[j];
                int newY = cur.y + d_y[j];

                if (IsOutBound(newX, newY)) {
                    continue;
                }

                if (board[newX][newY] == 'X') {
                    board[newX][newY] = '.';
                    waterQ.add(new xy(newX, newY));
                }
            }
        }
    }

    private static boolean IsPossibleMeet() {
        Queue<xy> q = new ArrayDeque<>();

        while (!swanQ.isEmpty()) {
            xy cur = swanQ.poll();

            if (cur.x == x2 && cur.y == y2) {
                return true;
            }
            for (int i = 0; i < 4; i++) {
                int newX = cur.x + d_x[i];
                int newY = cur.y + d_y[i];

                if (IsOutBound(newX, newY) || visited[newX][newY]) {
                    continue;
                }

                visited[newX][newY] = true;

                if (board[newX][newY] == 'X') {
                    q.offer(new xy(newX, newY));
                    continue;
                }

                swanQ.offer(new xy(newX, newY));
            }
        }
        swanQ = q;

        return false;
    }

    private static boolean IsOutBound(int x, int y) {
        return x < 0 || x >= R || y < 0 || y >= C;
    }

}