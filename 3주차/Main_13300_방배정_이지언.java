
 * 문제 : 백준_13300_방배장
 * 난이도 : 브론즈2 (체감난이도 : 약간 쉬움)
 * 메모리 13636KB 시간 116ms
 * 풀이 아이디어
 * 2차배열로 행은 성별, 열은 성별로 된 표를 만들어 조건에 맞는 입력값이 올 떄마다 1씩 증가하도록 함
 * 완성된 표에 각 칸에는 같은 방을 쓸 수 있는 인원이 분류됨. 
 * 해당 인원 수에 k(인원제한)로 나누면 필요한 방 수가 나온다.
 * 여기서 나머지가 있을 경우에 값 +1.
 */

import java.util.*;
import java.io.*;

public class Main_13300_방배정 {
	public static void main(String[] args)throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st =  new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // 학생 수
		int k = Integer.parseInt(st.nextToken()); // 방 최대 수용인원

		// 학년과 성별에 따라 구분해 인원을 세는 표
		// 행: 성별 열: 학년
		int[][] count = new int[2][6]; 
		for(int i = 0 ; i < N ; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()); // 성별
			int y = Integer.parseInt(st.nextToken()) - 1; // 학년(인덱스 0 부터 시작)
			count[s][y] +=1;
		}
		
		int sum = 0;// 최소한의 방의 수
		for(int i = 0; i < 2 ; i++) {
			for(int j=0; j < 6; j++) {
				int num = count[i][j];
				sum += num/k;
				if(num % k > 0) sum = sum+1;
						
			}
		}
		System.out.println(sum);
	}

}
