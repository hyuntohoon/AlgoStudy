package baekjoon;

import java.math.BigInteger;
import java.util.Scanner;

public class prob2749 {
	static final int MOD = 1_000_000;
	static class Matrix{
		int size;
		long[][] mat;
		
		public Matrix(int size) {
			this.size = size;
			mat = new long[size][size];
		}
		
		public Matrix multiplay(Matrix m) {
			Matrix ret = new Matrix(size);
			
			for(int i=0;i<size;i++) {
				for(int j=0;j<size;j++) {
					for(int k=0;k<size;k++) {
						ret.mat[i][j] += (this.mat[i][k] * m.mat[k][j]);
						ret.mat[i][j] %= MOD;
					}
				}
			}
			
			return ret;
		}
	}
	static BigInteger N;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = new BigInteger(sc.nextLine());

		Matrix unitMat = new Matrix(2);
		unitMat.mat[0][0] = 1;
		unitMat.mat[0][1] = 1;
		unitMat.mat[1][0] = 1;
		unitMat.mat[1][1] = 0;
		
		Matrix fiboMat = new Matrix(2);
		fiboMat.mat[0][0] = 1;
		fiboMat.mat[0][1] = 1;
		fiboMat.mat[1][0] = 1;
		fiboMat.mat[1][1] = 0;
		
		long result = expend(fiboMat, unitMat, N);
		
		System.out.println(result);
	}
	private static long expend(Matrix fiboMat, Matrix unitMat, BigInteger n) {
		while(n.compareTo(BigInteger.valueOf(0)) != 0) {
			if(n.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) {
				unitMat = unitMat.multiplay(unitMat);
				n = n.divide(BigInteger.valueOf(2));
			} else {
				fiboMat = fiboMat.multiplay(unitMat);
				n = n.subtract(BigInteger.valueOf(1));
			}
		}
		
		return fiboMat.mat[1][1];
	}

}