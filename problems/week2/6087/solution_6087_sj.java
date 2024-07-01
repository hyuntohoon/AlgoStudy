package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class prob6087 {
    static final int[][] d = { { 0, -1, 0, 1, 0 }, { 0, 0, 1, 0, -1 } };
    static final int INF = 1_000_000;

    static class Point implements Comparable<Point> {
        int x;
        int y;
        int prevDirection;
        int depth;

        public Point(int x, int y, int prevDirection, int depth) {
            this.x = x;
            this.y = y;
            this.prevDirection = prevDirection;
            this.depth = depth;
        }

        @Override
        public int compareTo(Point o) {
            return this.depth - o.depth;
        }

    }

    static final int GND = 0;
    static final int WALL = 1;
    static StringTokenizer st = null;
    static int W, H;
    static int[][] board;
    static Point c1;
    static Point c2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        board = new int[H][W];
        for (int i = 0; i < H; i++) {
            String input = br.readLine();
            for (int j = 0; j < W; j++) {
                switch (input.charAt(j)) {
                    case 'C':
                        if (c1 == null) {
                            c1 = new Point(i, j, 0, 0);
                        } else {
                            c2 = new Point(i, j, 0, 0);
                        }
                    case '.':
                        board[i][j] = GND;
                        break;
                    case '*':
                        board[i][j] = WALL;
                        break;
                }
            }
        }

        System.out.println(bfs());
    }

    private static int bfs() {
        int[][] result = new int[H][W];
        for (int i = 0; i < H; i++) {
            Arrays.fill(result[i], INF);
        }

        PriorityQueue<Point> q = new PriorityQueue<>();
        boolean[][][] visited = new boolean[H][W][5];
        result[c1.x][c1.y] = 0;
        q.add(c1);

        while (!q.isEmpty()) {
            Point cur = q.poll();

            if(visited[cur.x][cur.y][cur.prevDirection]){
                continue;
            }
            visited[cur.x][cur.y][cur.prevDirection] = true;

            for (int i = 1; i < 5; i++) {
                int nx = cur.x + d[0][i];
                int ny = cur.y + d[1][i];

                if (IsOutBound(nx, ny) || board[nx][ny] == WALL) {
                    continue;
                }

                int distance = 0;
                if (cur.prevDirection == 0 || cur.prevDirection == i) {
                    distance = cur.depth;
                } else {
                    distance = cur.depth + 1;
                }

                if(result[nx][ny] >= distance){
                    result[nx][ny] = distance;
                    q.add(new Point(nx, ny, i, distance));
                }
            }
        }
        // PrintForDebug(result);
        return result[c2.x][c2.y];
    }

    private static void PrintForDebug(int[][] result) {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n\n");
    }

    private static boolean IsOutBound(int nx, int ny) {
        return nx < 0 || nx >= H || ny < 0 || ny >= W;
    }
}