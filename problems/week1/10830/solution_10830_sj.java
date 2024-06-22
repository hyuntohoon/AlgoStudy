package baekjoon;

import java.util.*;
import java.io.*;

public class prob10830 {
	static int N;
	static long B;
	static final int MOD = 1000;

	static class Matrix {
		int size;
		long[][] mat;

		public Matrix(int size) {
			this.size = size;
			mat = new long[size][size];
		}

		public void multiply(Matrix m) {
			Matrix ret = new Matrix(size);

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					for (int k = 0; k < N; k++) {
						ret.mat[i][j] += (this.mat[i][k] * m.mat[k][j]) % MOD;
						ret.mat[i][j] %= MOD;
					}
				}
			}
			this.mat = ret.mat;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					sb.append(mat[i][j] + " ");
				}
				sb.append("\n");
			}

			return sb.toString();
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		B = Long.parseLong(st.nextToken());
		Matrix ans = new Matrix(N);
		Matrix origin = new Matrix(N);

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				origin.mat[i][j] = Integer.parseInt(st.nextToken());
				ans.mat[i][i] = 1;
			}
		}
		
		while (true) {
			if (B == 0) {
				break;
			}

			if (B % 2 == 1) {
				ans.multiply(origin);
				B--;
			} else {
				origin.multiply(origin);
				B /= 2;
			}
		}
		System.out.println(ans.toString());
	}

}
