/**
 * 아이디어:
 * 	위상정렬
 * 	근거 : 키순서는 "선행 - 후행" 관계	
 * 	
 * 메모리: 45976 kb
 * 시간: 408 ms
 * 난이도: ★★☆☆☆
 */

import java.io.*;
import java.util.*;

public class Main_2252_최진서 {
	static ArrayList<Integer>[] parents;
	static int[] count;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // 학생 수
		int M = Integer.parseInt(st.nextToken()); // 키 비교 수
		
		parents = new ArrayList[N+1]; // 그 다음에 줄 서야 되는 사람
		count = new int[N+1]; // 그 이전에 줄서야 되는 사람 수
		for (int i = 1; i <= N; i++) parents[i] = new ArrayList<>();
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			parents[a].add(b); // a 가 줄을 서야 b 가 설수있다.
			count[b]++; // b 의 선행 노드 수 1 감소
		}
		
		Queue<Integer> queue = new ArrayDeque<>();
		for (int i = 1; i <= N; i++) {
			if (count[i] == 0) queue.add(i); // 선행노드의 개수가 0인 노드면 큐에 담음
		}
		// bfs
		while (!queue.isEmpty()) {
			int cur = queue.poll();
			sb.append(cur).append(" ");
			for (int next : parents[cur]) { // cur 노드의 후행노드
				count[next]--; // 후행노드의 선행노드 수를 1 감소
				if (count[next] == 0) queue.add(next); // 만약 후행노드의 선행되어야 하는 노드 수가 0이면 큐에 담음
			}
		}
		System.out.println(sb);
		
		br.close();
	}
}
