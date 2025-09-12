package _0912;
/*
 * 본 코드는 map을 2차원 배열을 써 메모리 초과가 나고, 가중치를 int로 선언했기 때문에 오버플로우가 나는 코드입니다.
 * 그래도 다익스트라 알고리즘은 맞아요!
 * 각 매장 별로 시작점을 여러개로 다익스트라를 이용하여 거리를 구해도 가장 가까운 매장의 거리만 알기 때문에 상관 없음
 */
import java.io.*;
import java.util.*;
public class Main_13911_이주형 {
	static class edge implements Comparable<edge>{
		int to,w;
		public edge(int to, int w) {
			super();
			this.to = to;
			this.w = w;
		}
		@Override
			public int compareTo(edge o) {
				return Integer.compare(this.w, o.w);
			}
	}
	static int V,E;
	static int M, X;
	static List<Integer> mac;
	static int S, Y;
	static List<Integer> star;
	static int[][] map; // 가중치 저장 배열
	static List<Integer>[] graph; //인접 리스트
	static int[] distM, distS;
	static void dijkMac() {
		distM = new int[V];
		Arrays.fill(distM, Integer.MAX_VALUE);
		PriorityQueue<edge> pq = new PriorityQueue<>();
		for(int i=0;i<M;i++) {
			pq.add(new edge(mac.get(i),0));
			distM[mac.get(i)] = 0;
		}
		while(!pq.isEmpty()) {
			edge cur = pq.poll();
			if(cur.w > distM[cur.to] || cur.w > X) continue; // X도 프루닝
			for(int i=0;i<graph[cur.to].size();i++) {
				int to = graph[cur.to].get(i);
				int nw = map[cur.to][to] + cur.w;
				if(nw > distM[to] || nw > X) continue;
				distM[to] = nw;
				pq.add(new edge(to,nw));
			}
		}
	}
	static void dijkStar() {
		distS = new int[V];
		Arrays.fill(distS, Integer.MAX_VALUE);
		PriorityQueue<edge> pq = new PriorityQueue<>();
		for(int i=0;i<S;i++) {
			pq.add(new edge(star.get(i),0));
			distS[star.get(i)] = 0;
		}
		while(!pq.isEmpty()) {
			edge cur = pq.poll();
			if(cur.w > distS[cur.to] || cur.w > Y) continue;
			for(int i=0;i<graph[cur.to].size();i++) {
				int to = graph[cur.to].get(i);
				int nw = map[cur.to][to] + cur.w;
				if(nw > distS[to] || nw > X) continue;
				distS[to] = nw;
				pq.add(new edge(to,nw));
			}
		}
	}
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		graph = new ArrayList[V];
		for(int i=0;i<V;i++) graph[i] = new ArrayList<Integer>();
		map = new int[V][V];
		for(int i=0;i<E;i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken())-1;
			int v = Integer.parseInt(st.nextToken())-1;
			int w = Integer.parseInt(st.nextToken());
			map[u][v] = w; 
			map[v][u] = w;
			graph[u].add(v);
			graph[v].add(u);
		}
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		mac = new ArrayList<Integer>();
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<M;i++) 
			mac.add(Integer.parseInt(st.nextToken())-1);
		st = new StringTokenizer(br.readLine());
		S = Integer.parseInt(st.nextToken());
		Y = Integer.parseInt(st.nextToken());
		star = new ArrayList<Integer>();
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<S;i++) 
			star.add(Integer.parseInt(st.nextToken())-1);
		//각 매장별로 다익스트라 
		dijkMac();
		dijkStar();
		int ans = Integer.MAX_VALUE;
		for(int i=0;i<V;i++) {
			if(distM[i]!=0 && distM[i]!=Integer.MAX_VALUE && distS[i]!=0 && distS[i]!=Integer.MAX_VALUE )
				ans = Math.min(ans, distM[i]+distS[i]);
		}
		System.out.println(ans);
	}
}
