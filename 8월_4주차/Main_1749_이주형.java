import java.util.*;
import java.io.*;
/*
 * 단순 누적합으로 풀면 N^2*M*2 => 2^4*10^8 시간초과!!!!!!!!!!
 * 
 * 세로 누적합 + 가로 카데인 -> O(N^2*M)
 */
public class Main_1749_이주형 {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[][] map = new int[N+1][M+1];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int ans = Integer.MIN_VALUE;
		for(int rs=0;rs<N;rs++) { // rs = rowStart
			int[] colSum = new int[M];
			for(int re=rs;re<N;re++) { // re = rowEnd
				for(int m=0;m<M;m++) {
					colSum[m] += map[re][m]; // colSum에는 rowEnd까지의 열의 누적합 값이 저장
				}
				int curMax = colSum[0];
				int tempMax = colSum[0];
				for(int i=1;i<M;i++) { // 카데인 알고리즘 
					curMax = Math.max(colSum[i], curMax+colSum[i]);
					tempMax = Math.max(curMax, tempMax);
					}
				ans = Math.max(ans, tempMax);
			}
		}
		System.out.println(ans);
	}
}
