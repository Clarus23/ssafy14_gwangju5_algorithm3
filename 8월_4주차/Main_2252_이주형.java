package _0830;
/*
 * 50112KB 748ms
 * 자신보다 키가 작은 사람이 없다 = 진입 차수가 0
 * 진입차수가 0인 사람을 시작으로 bfs 순회
 */
import java.io.*;
import java.util.*;

public class Main_2252_이주형 {
	static int N,M;
	static List<Integer>[] graph;
	static int[] indegree;
	static boolean[] visited;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		graph = new ArrayList[N];//List배열로 선언
		indegree = new int[N];
		visited = new boolean[N];
		for(int i=0;i<N;i++) graph[i]=new ArrayList<>();
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int p = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			graph[p].add(c);
			indegree[c]++; //자기보다 키가 작은 사람이 있는 경우 진입 차수 +1
		}
		Deque<Integer> dq = new ArrayDeque<>(); 
		for(int i=0;i<N;i++) 
			if(indegree[i]==0) dq.add(i);
		
		while(!dq.isEmpty()) {
			int cur = dq.poll();
			visited[cur] = true;
			System.out.print((cur+1)+" ");
			for(int i=0;i<graph[cur].size();i++) {
				int next = graph[cur].get(i);
				if(!visited[next]) { // 진입차수가 0이 아닐때는 진입차수 -1만하고 방문처리하지 않음
					indegree[next]-=1; 
					if(indegree[next]==0){//진입 차수가 0일 때만 방문 처리하도록 함
						dq.add(next);
						visited[next] = true; 
					}
				}
			}
		}
	}
}
