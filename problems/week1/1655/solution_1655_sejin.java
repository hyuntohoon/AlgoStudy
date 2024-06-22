import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

public class solution_1655_sejin {
  static StringBuilder result = new StringBuilder();
  static PriorityQueue<Integer> maxHeap;
  static PriorityQueue<Integer> minHeap;
  static int N;
  static int cnt;

  public static void main(String[] args) throws NumberFormatException, IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // 초기화
    maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    minHeap = new PriorityQueue<>();
    cnt = 0;

    N = Integer.parseInt(br.readLine());
    for (int i = 0; i < N; i++) {
      int n = Integer.parseInt(br.readLine());

      // 삽입
      if (maxHeap.size() == minHeap.size()) {
        maxHeap.add(n);
      } else {
        minHeap.add(n);
      }

      // 삽입 후 peek 검사
      if (maxHeap.size() > 0 && minHeap.size() > 0) {
        if (maxHeap.peek() > minHeap.peek()) {
          int i1 = maxHeap.poll();
          int i2 = minHeap.poll();

          maxHeap.add(i2);
          minHeap.add(i1);
        }
      }

      result.append(maxHeap.peek()).append("\n");
    }

    System.out.println(result);
  }
}
