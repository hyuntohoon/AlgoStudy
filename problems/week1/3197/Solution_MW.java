package problems;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Solution_mw {

    public static class Pair {
        public int y, x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static int r, c;
    static char[][] board;
    static int[][] water;
    static int[] p;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};
    public static void main(String[] args) throws IOException {

        // 두 백조가 있는 물 공간이 합쳐지면 된다
        // 1. 각 백조가 있는 물 공간 좌표에 번호를 붙이기
        // 2. 물 공간 영역 확장하기
        // 3. 확장한 영역과 인접한 좌표의 물 공간 번호를 union 하기
        // 4. 1번과 2번 물 공간 영역이 같은 공간에 있으면 종료

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] s = br.readLine().split(" ");
        r = Integer.parseInt(s[0]);
        c = Integer.parseInt(s[1]);
        board = new char[r][c];
        water = new int[r][c];
        p = new int[r*c];
        List<Pair> ducks = new ArrayList<>();

        for(int i=0; i<r; i++) {
            String line = br.readLine();
            for(int j=0; j<c; j++) {
                board[i][j] = line.charAt(j);
                if(board[i][j] == 'L') {
                    ducks.add(new Pair(i, j));
                }
            }
        }

        // 물 공간 좌표에 번호 붙이기
        int num = 1;
        for(int i=0; i<r; i++) {
            for (int j=0; j<c; j++) {
                if(board[i][j] == 'X' || water[i][j] != 0)    continue;
                bfs(i, j, num++);
            }
        }

        // printForDebug();

        // 초기 물 공간 큐에 넣기
        Queue<Pair> q = new ArrayDeque<>();
        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                if(board[i][j] == 'X')  continue;
                q.add(new Pair(i, j));
            }
        }

        // 물 공간 영역 확장하기
        makeSet();
        int days = 0;
        while(true) {
            // 두 백조가 있는 물 공간 영역이 같으면 종료
            Pair duck1 = ducks.get(0);
            Pair duck2 = ducks.get(1);
            int num1 = water[duck1.y][duck1.x];
            int num2 = water[duck2.y][duck2.x];
            if(find(num1) == find(num2)) {
                System.out.println(days);
                break;
            }

            // 인접한 물 영역 확장
            int qsize = q.size();
            while(qsize-- > 0) {

                Pair cur = q.poll();
                for(int dir=0; dir<4; dir++) {
                    int ny = cur.y + dy[dir];
                    int nx = cur.x + dx[dir];
                    if(ny < 0 || ny >= r || nx < 0 || nx >= c)  continue;
                    if(board[ny][nx] != 'X')    continue;
                    q.offer(new Pair(ny, nx));
                    water[ny][nx] = water[cur.y][cur.x];
                    board[ny][nx] = '.';
                }
            }

            // 확장한 영역과 인접한 좌표의 물 공간 번호를 union 하기
            qsize = q.size();
            while(qsize-- > 0) {
                Pair cur = q.poll();
                for(int dir=0; dir<4; dir++) {
                    int ny = cur.y + dy[dir];
                    int nx = cur.x + dx[dir];
                    if(ny < 0 || ny >= r || nx < 0 || nx >= c)  continue;
                    if(board[ny][nx] == 'X' || water[cur.y][cur.x] == water[ny][nx])    continue;
                    union(water[cur.y][cur.x], water[ny][nx]);
                }
                q.offer(cur);
            }
            days++;
        }
    }

    static void printForDebug() {
        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                System.out.print(water[i][j]);
            }
            System.out.println();
        }
    }

    static void bfs(int i, int j, int num) {
        Queue<Pair> q = new ArrayDeque<>();
        q.offer(new Pair(i, j));
        water[i][j] = num;
        while(!q.isEmpty()) {
            Pair cur = q.poll();
            for(int dir=0; dir<4; dir++) {
                int ny = cur.y + dy[dir];
                int nx = cur.x + dx[dir];
                if(ny < 0 || ny >= r || nx < 0 || nx >= c)  continue;
                if(board[ny][nx] == 'X' || water[ny][nx] != 0)  continue;
                q.offer(new Pair(ny, nx));
                water[ny][nx] = num;
            }
        }
    }

    static void makeSet() {
        for(int i=0; i<r*c; i++) {
            p[i] = i;
        }
    }

    static int find(int a) {
        if(p[a] == a)   return a;
        return p[a] = find(p[a]);
    }

    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) return;
        p[rootA] = rootB;
    }
}
