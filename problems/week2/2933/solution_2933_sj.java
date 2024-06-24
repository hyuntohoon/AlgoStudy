package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class prob2933 {
    static class xy {
        int x;
        int y;

        public xy(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    static final int[][] d = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };
    static int N, M, K;
    static List<xy> target = new ArrayList<>();
    static char[][] board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new char[101][101];
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = s.charAt(j);
            }
        }

        K = Integer.parseInt(br.readLine());
        String s[] = br.readLine().split(" ");
        for (int i = 0; i < K; i++) {
            int h = Integer.parseInt(s[i]);

            // 막대 던지기
            ThrowStick(i, h);

            // BFS로 공중에 떠있는 미네랄 찾기
            FloodFill(i);

            // 미네랄 내리기 (떠있는 미네랄은 i로 표시함)
            if (target.size() > 0) {
                DownCluster(i);
            }

        }

        PrintBoard();
    }

    private static void DownCluster(int n) {
        int down = N;
        for (int i = 0; i < M; i++) {
            int maxColPos = -1;
            int bottom = N;
            for (int j = 0; j < N; j++) {
                if (board[j][i] == 'M') {
                    maxColPos = Math.max(maxColPos, j);
                }
            }

            for (int j = maxColPos + 1; j < N; j++) {
                if (board[j][i] == 'x') {
                    bottom = j;
                    break;
                }
            }

            // 해당 열에 클러스터가 없다면
            if (maxColPos == -1) {
                continue;
            }

            // 있다면
            down = Math.min(down, bottom - maxColPos - 1);
        }

        // down만큼 모두 하강하면됨
        for (xy cdnt : target) {
            board[cdnt.x][cdnt.y] = '.';
        }
        for (xy cdnt : target) {
            board[cdnt.x + down][cdnt.y] = 'x';
        }
    }

    private static void FloodFill(int n) {
        boolean[][] visited = new boolean[101][101];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] != 'x' || visited[i][j]) {
                    continue;
                }

                boolean isFloat = true;
                // bfs
                target.clear();
                Queue<xy> q = new ArrayDeque<>();
                q.add(new xy(i, j));
                target.add(new xy(i, j));
                visited[i][j] = true;

                while (!q.isEmpty()) {
                    xy cur = q.poll();

                    for (int k = 0; k < 4; k++) {
                        int nx = cur.x + d[0][k];
                        int ny = cur.y + d[1][k];

                        if (IsOutBound(nx, ny) || board[nx][ny] != 'x' || visited[nx][ny]) {
                            continue;
                        }

                        if (nx == N - 1) {
                            isFloat = false;
                        }

                        q.add(new xy(nx, ny));
                        visited[nx][ny] = true;
                        target.add(new xy(nx, ny));
                    }
                }

                // bfs가 끝남
                // 조사한 클러스터가 떠있으면
                // list에 저장된 좌표를 "M"로 치환
                if (isFloat) {
                    target.forEach(o -> board[o.x][o.y] = 'M');
                    return;
                }
            }
        }
        target.clear();
    }

    private static boolean IsOutBound(int nx, int ny) {
        return nx < 0 || nx >= N || ny < 0 || ny >= M;
    }

    private static void ThrowStick(int i, int h) {
        if (N - h < 0 || N - h >= N) {
            return;
        }

        if (i % 2 == 0) {
            for (int col = 0; col < M; col++) {
                if (board[N - h][col] == 'x') {
                    board[N - h][col] = '.';
                    return;
                }

            }
        } else {
            for (int col = M - 1; col >= 0; col--) {
                if (board[N - h][col] == 'x') {
                    board[N - h][col] = '.';
                    return;
                }
            }
        }
    }

    private static void PrintBoard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(board[i][j]);
                // if (board[i][j] == 'x') {
                // sb.append(board[i][j]);
                // } else {
                // sb.append('.');
                // }
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
}