import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * 난이도 : 실버3 (체감 - 어려움)
 * 메모리 : 14260 KB
 * 시간 : 108 ms
 * 알고리즘 : dp 
 * 근거
 * 1. 최적해를 구하기
 * 2. 이전의 작은 부분이 다른 큰 부분에 활용. 즉, 연관성 존재.
 * 풀이 아이디어
 * 계단은 한 번에 1칸 또는 2칸 이동 가능하나 3개 연속으로 계단을 밝을 수 없음
 * 점화식 
 * dp[N] = score[N] + Math.max( dp[N-2] , score[N-1} + dp[N-3])
 * 
 * score[N-1} + dp[N-3]인 이유?
 * dp[N-1]은 score[N-1] + Math.max( dp[N-3] , score[N-2] + dp[N-4])
 * 여기서 뒤의 값이 선택되었을 경우 N, N-1 , N-2으로 세 칸 연속으로 밝게 될 수도 있음.
 * 그러므로 dp[N - 3] + socre[N-1]로 연속 세 칸을 밝을 확률을 완전히 삭제
 */



public class Main_2579_계단오르기 {
	static int[] score; //각 계단의 점수
	static int[] dp;//n번째 계단까지 오를 떄 얻을 수 있는 총점수 중 최댓값.
	
	public static void main(String[] args)throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());//계단 수
		score = new int[N+1]; //칸 별 점수를 담을 배열 
		for(int i = 1; i <= N ; i++) {//칸 별 점수 입력
			score[i] = Integer.parseInt(br.readLine());
		}
		dp = new int[N+1]; //테이블. i번째 칸까지 얻을 수 있는 최고점
		//초기값(=기저조건)
		dp[1] = score[1];
		if(N>=2) dp[2] = score[2]+score[1]; //n=1부터 시작. 
		
		//Bottom-up + 테이블링 => 모든값을 배열에 저장
		for(int i = 3; i <= N ; i++) {
			dp[i] = score[i] + Math.max(dp[i-2], score[i-1] +  dp[i-3]);
		}
		System.out.println(dp[N]);
	}
	
}
