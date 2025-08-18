import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

/*
	풀이 아이디어 : bitmask + BFS + 완전탐색
		- 선거구가 2개이므로 bitmask를 통해 선거구를 나눌 수 있음
		- 0, 2^n -1은 한개의 선거구만 있는 경우이므로 제외
		- 1010, 0101은 선거구가 반전되어있으나, diff값이 같을 것이란걸 알 수 있음
			-> bool 배열을 만들어 한번만 탐색.

		- 선거구를 나눴다면, bfs탐색을 통해 A선거구, B선거구를 탐색함
		- 각 선거구에서 이어지지 않는 지역이 있다면 다음 탐색 재개

		- 모든 탐색을 끝낸 후, 최소 diff값이 정답.

		** 완전 탐색이 가능한 이유?
			-> N의 조건이 2이상, 10이하 (매우 작음)

	시간 복잡도 :O(N*2^N)

	메모리 : 14448kb
	시간   : 104ms
*/

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int n = Integer.parseInt(br.readLine());
		boolean[] isSelected = new boolean[(1 << n)]; // [0 ~ 2^n - 1]
		
		// 인구수 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		int sumOfPopulation = 0;
		int[] populations = new int[n];
		for(int i=0; i<n; ++i) {
			populations[i] = Integer.parseInt(st.nextToken());
			sumOfPopulation += populations[i];
		}
		
		// 그래프 입력
		List<Integer>[] graph = new ArrayList[n];
		for(int i=0; i<n; ++i) {
			graph[i] = new ArrayList<>();
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			
			for(int j=0; j<a; ++j)
				graph[i].add(Integer.parseInt(st.nextToken()) - 1);
		}
		
		int minDiff = Integer.MAX_VALUE;
		// 이제부터 sub를 이용해 bitmask
		// 0 bit -> A선거구, 1 bit -> B선거구
		for(int sub = 1; sub < (1 << n) - 1; ++sub) {
			if(isSelected[sub]) continue;
			
			// 1010 과 0101의 인구수 차이는 똑같을 것이기 때문에 true 처리
			isSelected[sub] = true;
			isSelected[((~sub) & ((1 << n) - 1))] = true;
			
			
			// a 선거구의 인원과
			// 각 선거구 별 포함된 지역의 개수, 탐색을 시작할 지역을 정한다.
			int aPopulation = 0;
			int[] start = new int[2];
			int[] size = new int[2];
			for(int j=0; j<n; ++j) {
				if((sub & (1 << j)) == 0) {
					start[0] = j;
					++size[0];
					aPopulation += populations[j];
				} else {
					start[1] = j;
					++size[1];
				}
			}
			
			// diff 구하기, 음수라면  -1을 곱해서 양수로 바꾼다.
			int diff = sumOfPopulation - 2*aPopulation;
			diff *= ((diff < 0) ? -1 : 1);
			
			// diff가 minDiff보다 크거나 같다면 탐색할 필요가 없음
			if(diff >= minDiff) continue;
			
			// diff가 minDiff보다 작다면, A선거구와 B선거구에 포함된 지역들이 서로 이어져 있는지 탐색한다.
			// 0번은 A지역구, 1번은 B지역구의 정보들이 저장되어있다.
			boolean[] isVisited = new boolean[n];
			Deque<Integer> q = new ArrayDeque<Integer>();
			boolean isValid = true;
			for(int area=0; area<2; ++area) {
				int curSize = 1;
				isVisited[start[area]] = true;
				q.offer(start[area]);
				
				while(!q.isEmpty()) {
					int cur = q.poll();
					
					for(int next : graph[cur]) {
						if((area == 0 && (sub & (1 << next)) != 0) // A지역구 탐색 중 bit가 1이거나(B지역구 소속이거나)
						|| (area == 1 && (sub & (1 << next)) == 0) // B지역구 탐색 중 bit가 0이거나(A지역구 소속이거나)
						|| isVisited[next]) continue;			   // 이미 탐색한 지역이라면 continue
						
						++curSize;
						isVisited[next] = true;
						q.offer(next);
					}
				}
				// 만약 이번에 탐색한 지역구의 지역 개수와 A지역구 또는 B지역구의 지역개수가 다르면,
				// 이는 지역구가 연결되어 있지 않다는 것을 의미하므로 탐색 종료
				if(curSize != size[area]) {
					isValid = false;
					break;
				}
			}
			// 유효한 탐색이었으면 minDiff를 업데이트한다.
			// Q. diff와 minDiff의 크기 비교를 하지 않는 이유?
			// A. 위에서 이미 diff가 minDiff 이상이라면 Pruning을 하였으므로
			//    여기까지 내려온 시점에서 diff는 minDiff보다 작다는게 보장되어있다.
			minDiff = ((isValid) ? diff : minDiff);
		}
		
		// BufferedWriter의 write는 파라메터로 String 형만을 받으므로, Integer.toString()을 이용해 형변환
		// minDiff값이 Integer.MAX_VALUE라면, 유효한 지역구가 하나도 없었다는 소리이므로 -1을 출력.
		bw.write(Integer.toString(((minDiff == Integer.MAX_VALUE) ? -1 : minDiff)));
		bw.close();
	}
}
