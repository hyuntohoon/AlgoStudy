import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class Solution_9376_MW_2 {

    static class Pair implements Comparable<Pair> {
        public int y, x, door;

        public Pair(int y, int x, int door) {
            this.y = y;
            this.x = x;
            this.door = door;
        }

        @Override
        public int compareTo(Pair o) {
            return this.door - o.door;
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

        for (int t = 1; t <= T; t++) {
            String[] s = br.readLine().split(" ");
            h = Integer.parseInt(s[0]);
            w = Integer.parseInt(s[1]);

            board = new char[h + 2][w + 2];
            List<Pair> prisoner = new ArrayList<>();

            for (int i = 1; i <= h; i++) {
                String line = br.readLine();
                for (int j = 1; j <= w; j++) {
                    board[i][j] = line.charAt(j - 1);
                    if (board[i][j] == '$') {
                        prisoner.add(new Pair(i, j, 0));
                    }
                }
            }

            for (int i = 0; i <= h + 1; i++) { // 상근이가 돌아다닐 수 있도록, 감옥 외부에 패딩을 줌
                if (i == 0 || i == h + 1) {
                    for (int j = 0; j <= w + 1; j++) {
                        board[i][j] = '.';
                    }
                } else {
                    board[i][0] = '.';
                    board[i][w + 1] = '.';
                }
            }

            if (bfs(prisoner.get(0)) && bfs(prisoner.get(1))) { // 문을 열지 않고 갈 수 있는 경우 확인
                System.out.println(0);
                continue;
            }

            int[][] cost1 = dijkstra(new Pair(0, 0, 0)); // 상근이가 감옥을 돌면서 각 좌표에 도달하기 위해 문을 여는 최소 횟수 세기
            int[][] cost2 = dijkstra(prisoner.get(0)); // 죄수1이 감옥을 돌면서 각 좌표에 도달하기 위해 문을 여는 최수 횟수 세기
            int[][] cost3 = dijkstra(prisoner.get(1)); // 죄수2가 감옥을 돌면서 각 좌표에 도달하기 위해 문을 여는 최소 횟수 세기

            // 문제 조건에서 "각 죄수와 감옥의 바깥을 연결하는 경로가 항상 존재하는 경우만 입력으로 주어진다."라고 하였으니 세 명은 반드시 만날 수 있다.
            // 상근이와 만날 수 있다면 탈출한 것과 같다.
            // 따라서 모든 좌표에 대해 상근, 죄수1, 죄수2가 해당 좌표에 도달하기 위해 문을 여는 최소 횟수를 구한다.
            // 이후, 각 좌표에서 세 명의 문을 여는 횟수를 모두 더하고, -2를 해주면 그 좌표에서 만날 때 문을 연 최소 횟수가 된다.

            int mn = Integer.MAX_VALUE;
            for (int i = 0; i <= h+1; i++) {
                for (int j = 0; j <= w+1; j++) {

                    if (board[i][j] == '*') continue; // 벽일 경우

                    int door = cost1[i][j] + cost2[i][j] + cost3[i][j]; // 각 사람이 해당 좌표로 가기 위한

                    if (board[i][j] == '#') { // 세 명이므로 -2를 해서 중복을 제거
                        door -= 2;
                    }

                    mn = Math.min(mn, door);
                }
            }
            System.out.println(mn);
        }
    }

    // 문을 열지 않고 두 죄수가 탈출할 수 있는지 확인
    private static boolean bfs(Pair person) {
        boolean[][] vis = new boolean[h + 2][w + 2];

        Queue<Pair> q = new ArrayDeque<>();
        q.offer(person);
        vis[person.y][person.x] = true;

        while (!q.isEmpty()) {
            Pair cur = q.poll();
//            System.out.println(cur.y + " " + cur.x);
            for (int dir = 0; dir < 4; dir++) {
                int ny = cur.y + dy[dir];
                int nx = cur.x + dx[dir];
                if (ny < 0 || ny >= h + 2 || nx < 0 || nx >= w + 2) {
                    return true;
                }
                if (board[ny][nx] == '*' || board[ny][nx] == '#' || vis[ny][nx]) continue;
                vis[ny][nx] = true;
                q.offer(new Pair(ny, nx, 0));
            }
        }
        return false;
    }

    // 상근, 죄수1, 죄수2가 모든 좌표에 도달할 때 문을 연 최소 개수를 기록
    private static int[][] dijkstra(Pair person) {

        int[][] cost = new int[h + 2][w + 2];
        boolean[][] vis = new boolean[h + 2][w + 2];

        for(int i=0; i<=h+1; i++) {
            for(int j=0; j<=w+1; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.offer(person);
        cost[person.y][person.x] = 0;

        while (!pq.isEmpty()) {
            Pair minVertex = pq.poll();

            if(vis[minVertex.y][minVertex.x])   continue;

            vis[minVertex.y][minVertex.x] = true;

            int door = minVertex.door;

            for (int dir = 0; dir < 4; dir++) {
                int ny = minVertex.y + dy[dir];
                int nx = minVertex.x + dx[dir];
                if (ny < 0 || ny >= h + 2 || nx < 0 || nx >= w + 2) continue;
                if (board[ny][nx] == '*' || cost[ny][nx] <= cost[minVertex.y][minVertex.x]) continue; // 벽이거나 현재 vertex를 이용하여 최소값으로 업데이트 할 수 없으면
                if (board[ny][nx] == '#') { // 문 일 경우, 현재 문의 개수 + 1
                    cost[ny][nx] = cost[minVertex.y][minVertex.x] + 1;
                    pq.offer(new Pair(ny, nx, door+1));
                } else { // 문이 아니면, 현재 문의 개수 그대로 유지
                    cost[ny][nx] = cost[minVertex.y][minVertex.x];
                    pq.offer(new Pair(ny, nx, door));
                }
            }
        }
        return cost;
    }
}
