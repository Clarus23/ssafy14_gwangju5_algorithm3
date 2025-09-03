import java.io.*;
import java.util.*;

public class Main {
/* 2252 줄 세우기 골드3 중하
	메모리 45984 kb
	실행시간 408 ms
	시간복잡도 O(N + M)
	공간복잡도 O(N + M)
	아이디어
		위상정렬
		자신에게 연결된 친구 카운트
		연결이 없는 친구들 먼저 수행
		연결 해제해줌 -> 연결 해제한 친구 중에 연결이 없는 친구들 큐에 넣기
*/

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		List<Integer>[] list = new ArrayList[N + 1];
		int[] nums = new int[N + 1];
		for (int i = 1; i <= N; i++) { 
			list[i] = new ArrayList<>();
		}
		for (int i = 0; i < M; i++) { 
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			nums[e]++;
			list[s].add(e);
		}
		
		// 0인 친구들만 넣기
		Queue<Integer> q = new ArrayDeque<>();
		for (int i = 1; i <= N; i++) {
			if(nums[i] == 0) {
				q.offer(i);
			}
		}
		while(!q.isEmpty()) {
			int n = q.poll();
			sb.append(n).append(" ");
			for(int next : list[n]) {
				if(--nums[next] == 0) {
					q.offer(next);
				}
			}
		}
		System.out.println(sb);
	}
}
