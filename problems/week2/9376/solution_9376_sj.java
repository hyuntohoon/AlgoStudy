package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;
import java.awt.Point;

public class prob9376 {
    static final int INF = 1_000_000;
    static final int GROUND = 0;
    static final int WALL = 1;
    static final int DOOR = 2;

    static final int[][] d = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };

    static int T;
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st = null;
    static int h, w;
    static int[][] board;
    static Point helper;
    static Point prisoner1;
    static Point prisoner2;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            st = new StringTokenizer(br.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            board = new int[h + 2][w + 2];
            for (int i = 0; i < h + 2; i++) {
                Arrays.fill(board[i], GROUND);
            }

            prisoner1 = null;
            prisoner2 = null;
            for (int i = 1; i <= h; i++) {
                String s = br.readLine();
                for (int j = 1; j <= w; j++) {
                    switch (s.charAt(j - 1)) {
                        case '#':
                            board[i][j] = DOOR;
                            break;
                        case '*':
                            board[i][j] = WALL;
                            break;
                        case '$':
                            if (prisoner1 == null) {
                                prisoner1 = new Point(i, j);
                            } else {
                                prisoner2 = new Point(i, j);
                            }
                        case '.':
                            board[i][j] = GROUND;
                            break;
                    }
                }
            }

            helper = new Point(0, 0);
            sb.append(solution()).append("\n");
        }
        System.out.println(sb.toString());
    }

    private static int solution() {
        int ret = INF;

        int[][] helperMinBoard = bfs(helper);
        int[][] prisoner1MinBoard = bfs(prisoner1);
        int[][] prisoner2MinBoard = bfs(prisoner2);
        int[][] sumBoard = new int[h + 2][w + 2];

        // PrintForDebug(helperMinBoard, prisoner1MinBoard, prisoner2MinBoard);

        for (int i = 0; i < h + 2; i++) {
            for (int j = 0; j < w + 2; j++) {
                sumBoard[i][j] = helperMinBoard[i][j] + prisoner1MinBoard[i][j] + prisoner2MinBoard[i][j];

                if (board[i][j] == DOOR) {
                    sumBoard[i][j] -= 2;
                }
                ret = Math.min(ret, sumBoard[i][j]);
            }
        }

        return ret;
    }

    private static void PrintForDebug(int[][] helperMinBoard, int[][] prisoner1MinBoard, int[][] prisoner2MinBoard) {
        System.out.println("=============helper=============");

        for (int i = 0; i < h + 2; i++) {
            for (int j = 0; j < w + 2; j++) {
                System.out.print(helperMinBoard[i][j] >= INF ? 9 : helperMinBoard[i][j]);
                System.out.print(" ");
            }
            System.out.println("\n");
        }

        System.out.println("=============p1=============");

        for (int i = 0; i < h + 2; i++) {
            for (int j = 0; j < w + 2; j++) {
                System.out.print(prisoner1MinBoard[i][j] >= INF ? 9 : prisoner1MinBoard[i][j]);
                System.out.print(" ");

            }
            System.out.println("\n");
        }

        System.out.println("=============p2=============");

        for (int i = 0; i < h + 2; i++) {
            for (int j = 0; j < w + 2; j++) {
                System.out.print(prisoner2MinBoard[i][j] >= INF ? 9 : prisoner2MinBoard[i][j]);
                System.out.print(" ");

            }
            System.out.println("\n");

        }
    }

    private static int[][] bfs(Point p) {
        int[][] retBoard = new int[h + 2][w + 2];
        for (int i = 0; i < h + 2; i++) {
            Arrays.fill(retBoard[i], INF);
        }

        Deque<Point> dq = new ArrayDeque<>();
        boolean[][] visited = new boolean[h + 2][w + 2];
        dq.add(p);
        visited[p.x][p.y] = true;
        retBoard[p.x][p.y] = 0;

        while (!dq.isEmpty()) {
            Point cur = dq.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + d[0][i];
                int ny = cur.y + d[1][i];

                if (IsOutBound(nx, ny) || board[nx][ny] == WALL || visited[nx][ny]) {
                    continue;
                }

                if (board[nx][ny] == GROUND) {
                    visited[nx][ny] = true;
                    retBoard[nx][ny] = retBoard[cur.x][cur.y];
                    dq.addFirst(new Point(nx, ny));
                } else {
                    visited[nx][ny] = true;
                    retBoard[nx][ny] = retBoard[cur.x][cur.y] + 1;
                    dq.addLast(new Point(nx, ny));
                }
            }
        }

        return retBoard;
    }

    private static boolean IsOutBound(int nx, int ny) {
        return nx < 0 || nx >= h + 2 || ny < 0 || ny >= w + 2;
    }
}