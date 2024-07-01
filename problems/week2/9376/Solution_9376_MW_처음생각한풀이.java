/*
 * 시간, 메모리 초과
 * 먼저 한 죄수를 이동 시키고, 두번째 죄수를 이동 시키는 방법으로 했음.
 * 한 죄수가 밖에 나가는 경우에 대하여, 문을 연 상태를 담은 배열을 2번째 죄수에게 넘겨주었음.
 * 이 때 첫 번째 죄수는 나갈 수 있는 모든 경우를 탐색해야 하기 때문에 vis를 큐에 넣어서 같이 넘겨줌.
 * 방문 체크가 매번 새로 이루어져서 중복되는 방문이 많아져서 시간 초과랑 메모리 초과가 발생하는 것 같음.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Solution_9376_MW_처음생각한풀이 {

    static class Pair {
        public int y, x, cnt;
        public boolean[][] isOpen;
        public boolean[][] vis;
        public Pair(int y, int x, int cnt, boolean[][] isOpen, boolean[][] vis) {
            this.y = y;
            this.x = x;
            this.cnt = cnt;
            this.isOpen = new boolean[h][w];
            for(int i=0; i<h; i++) {
                for(int j=0; j<w; j++) {
                    this.isOpen[i][j] = isOpen[i][j];
                }
            }
            this.vis = new boolean[h][w];
            for(int i=0; i<h; i++) {
                for(int j=0; j<w; j++) {
                    this.vis[i][j] = vis[i][j];
                }
            }
        }

        public Pair(int y, int x, int cnt, boolean[][] isOpen) {
            this.y = y;
            this.x = x;
            this.cnt = cnt;
            this.isOpen = new boolean[h][w];
            for(int i=0; i<h; i++) {
                for(int j=0; j<w; j++) {
                    this.isOpen[i][j] = isOpen[i][j];
                }
            }
            this.vis = null;
        }
    }

    static int T;
    static int h, w;
    static char[][] board;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        T = Integer.parseInt(br.readLine());

        for(int t=1; t<=T; t++) {
            String[] s = br.readLine().split(" ");
            h = Integer.parseInt(s[0]);
            w = Integer.parseInt(s[1]);

            board = new char[h][w];
            List<Pair> people = new ArrayList<>();

            for(int i=0; i<h; i++) {
                String line = br.readLine();
                for(int j=0; j<w; j++) {
                    board[i][j] = line.charAt(j);
                    if(board[i][j] == '$') {
                        boolean[][] vis = new boolean[h][w];
                        vis[i][j] = true;
                        people.add(new Pair(i, j, 0, new boolean[h][w], vis));
                    }
                }
            }

            int sum = 0;
            int mn = Integer.MAX_VALUE;

            Queue<Pair> q = new ArrayDeque<>();
            Pair p1 = people.get(0);
            q.offer(p1);

            while(!q.isEmpty()) {
                Pair cur = q.poll();
                boolean[][] isOpen = cur.isOpen;
                boolean[][] vis = cur.vis;
                int cnt = cur.cnt;
                for(int dir=0; dir<4; dir++) {
                    int ny = cur.y + dy[dir];
                    int nx = cur.x + dx[dir];
                    if(ny < 0 || ny >= h || nx < 0 || nx >= w) {
                        Pair p2 = people.get(1);
                        p2.isOpen = isOpen;
                        sum += cur.cnt;
                        int tmp = moveOtherPeople(p2);
                        sum += tmp;
                        mn = Math.min(mn, sum);
                        sum = 0;
                        continue;
                    }
                    if(board[ny][nx] == '*' || vis[ny][nx]) continue;
                    if(board[ny][nx] == '#' && !isOpen[ny][nx]) {
                        isOpen[ny][nx] = true;
                        vis[ny][nx] = true;
                        q.offer(new Pair(ny, nx, cnt + 1, isOpen, vis));
                    } else {
                        vis[ny][nx] = true;
                        q.offer(new Pair(ny, nx, cnt, isOpen, vis));
                    }
                }
            }

            System.out.println(mn);
        }
    }

    static int moveOtherPeople(Pair p2) {

        int mn = Integer.MAX_VALUE;

        boolean[][] vis = new boolean[h][w];

        Queue<Pair> q = new ArrayDeque<>();
        q.offer(p2);

        vis[p2.y][p2.x] = true;

        while(!q.isEmpty()) {
            Pair cur = q.poll();
            int cnt = cur.cnt;
            boolean[][] isOpen = new boolean[h][w];
            for(int i=0; i<h; i++) {
                for(int j=0; j<w; j++) {
                    isOpen[i][j] = cur.isOpen[i][j];
                }
            }
            for(int dir=0; dir<4; dir++) {
                int ny = cur.y + dy[dir];
                int nx = cur.x + dx[dir];
                if(ny < 0 || ny >= h || nx < 0 || nx >= w) {
                    mn = Math.min(mn, cnt);
                    continue;
                }
                if(board[ny][nx] == '*' || vis[ny][nx]) continue;
                if(board[ny][nx] == '#' && !isOpen[ny][nx]) {
                    isOpen[ny][nx] = true;
                    vis[ny][nx] = true;
                    q.offer(new Pair(ny, nx, cnt + 1, isOpen));
                } else {
                    vis[ny][nx] = true;
                    q.offer(new Pair(ny, nx, cnt, isOpen));
                }
            }
        }
        return mn;
    }
}
