import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

/*
 * # 풀이 아이디어: DFS(조합) + BFS + pruning
 * ## 벽을 무조건 3개를 세워야한다. => dfs를 이용한 조합으로 모든 벽의 조합을 구한다.
 * ## 모든 벽의 조합을 구했으면 bfs를 수행하며 바이러스가 퍼지는 범위를 판단한다.
 * #### 안전구역이 가장 넓다는 것은 바이러스가 퍼진범위가 가장 작다는 말과 같다.
 * #### 그러므로 바이러스가 퍼진 범위가 현재 min값 보다 작다면 그 즉시 탐색을 중지하고 다음으로 넘어간다.
 * ## bfs 탐색이 끝났으면, min값을 업데이트해준다.
 * 
 * # 시간복잡도
 * ## 조합(DFS): O((NM)^3)
 * ## BFS 탐색: O(NM)
 * ## 총합: O((NM)^4)
 * 
 * # 메모리: 223,484 kb
 * # 실행시간: 572 ms
 * # 난이도: 하
 */

public class Main {
	static int n, m;
	static int[][] lab;
	
	static final int[][] dirs = new int[][] {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
	static int minBirusArea = Integer.MAX_VALUE;
	
	static List<int[]> emptys = new ArrayList<>();
	static List<int[]> birus = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		int saftyArea = -3;
		lab = new int[n][m];
		for(int row=0; row<n; ++row) {
			st = new StringTokenizer(br.readLine());
			for(int col=0; col<m; ++col) {
				lab[row][col] = Integer.parseInt(st.nextToken());
				
				if(lab[row][col] == 0) {
					++saftyArea;
					emptys.add(new int[] {row, col});
				} else if(lab[row][col] == 2) birus.add(new int[] {row, col});
			}
		}
		
		simulation(0, 0);
		System.out.print(saftyArea - minBirusArea);
	}
	
	static void simulation(int start, int depth) {
		if(depth == 3) {
			Deque<int[]> q = new ArrayDeque<int[]>();
			for(int[] it : birus) q.offer(it);
			int[][] simulMap = Arrays.stream(lab)
                    				 .map(row -> row.clone())
                    				 .toArray(int[][]::new);
			
			int birusArea = 0;
			while(!q.isEmpty() && (birusArea < minBirusArea)) {
				int[] cur = q.poll();
				
				for(int[] dir : dirs) {
					int[] next = new int[] {cur[0]+dir[0], cur[1]+dir[1]};
					
					if(next[0] < 0 || next[0] >= n|| next[1] < 0 || next[1] >= m
							|| simulMap[next[0]][next[1]] != 0) continue;
					
					++birusArea;
					simulMap[next[0]][next[1]] = 2;
					q.offer(next);
				}
			}
			
			minBirusArea = (birusArea < minBirusArea) ? birusArea : minBirusArea;
			
			return;
		}
		
		for(int i = start; i<emptys.size(); ++i) {
			int row = emptys.get(i)[0];
			int col = emptys.get(i)[1];
			
			lab[row][col] = 1;
			simulation(i+1, depth+1);
			lab[row][col] = 0;
		}
	}
}
