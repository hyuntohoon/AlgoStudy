package baekjoon;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class prob6549 {
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st = null;
    static Rect[] diagrams;
    static TreeSet<Rect> rectSet;
    static long max;
    static int n;

    public static void main(String[] args) throws IOException {
        // BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("ps_solution/src/input/input6549.txt")));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());

            if (n == 0) {
                break;
            }

            max = 0;
            rectSet = new TreeSet<>((Rect o1, Rect o2) -> o1.idx - o2.idx);
            diagrams = new Rect[n];
            for (int i = 0; i < n; i++) {
                diagrams[i] = new Rect(Integer.parseInt(st.nextToken()), i, i);
            }

            Arrays.sort(diagrams);
            for (int i = 0; i < n; i++) {
                Solve(diagrams[i]);
            }

            sb.append(max).append("\n");
        }

        System.out.println(sb.toString());

    }

    private static void Solve(Rect rect) {
        int lowerIdx = -1;
        int upperIdx = n;
        Rect lower = rectSet.lower(rect);
        Rect upper = rectSet.higher(rect);

        if (lower != null) {
            lowerIdx = lower.idx;
        }
        if (upper != null) {
            upperIdx = upper.idx;
        }

        if (lower != null && lower.height == rect.height) {
            rectSet.add(rect);
            rect.size = lower.size;
            max = Math.max(max, rect.size);
            return;
        }

        if(upper != null && upper.height == rect.height){
            rectSet.add(rect);
            rect.size = upper.size;
            max = Math.max(max, rect.size);
            return;
        }

        rect.size = (long)rect.height * (upperIdx - lowerIdx - 1);
        rectSet.add(rect);
        max = Math.max(max, rect.size);
    }

    static class Rect implements Comparable<Rect> {
        int height;
        int idx;
        long size;

        public Rect(int height, int idx, long size) {
            this.height = height;
            this.idx = idx;
            this.size = size;
        }

        @Override
        public int compareTo(Rect o) {
            return this.height - o.height;
        }

    }
}
