package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

public class 가운데를말해요_1655 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());
        int mid = Integer.parseInt(br.readLine());
        sb.append(mid).append("\n");
        PriorityQueue<Integer> minQ = new PriorityQueue<>();
        PriorityQueue<Integer> maxQ = new PriorityQueue<>(Collections.reverseOrder());

        for(int i=1; i<n; i++) {

            int a = Integer.parseInt(br.readLine());
            if(mid > a) {
                maxQ.offer(a);
            } else {
                minQ.offer(a);
            }

            // maxQ와 minQ의 개수 차이가 최대 1개
            if(i % 2 != 0) { // 짝수
                if(maxQ.size() > minQ.size()) {
                    minQ.offer(mid);
                    mid = maxQ.poll();
                }
                sb.append(mid).append("\n");
            } else { // 홀수
                if(maxQ.size() > minQ.size()) {
                    minQ.offer(mid);
                    mid = maxQ.poll();
                } else if(maxQ.size() < minQ.size()) {
                    maxQ.offer(mid);
                    mid = minQ.poll();
                }
                sb.append(mid).append("\n");
            }
        }
        System.out.println(sb);
    }
}