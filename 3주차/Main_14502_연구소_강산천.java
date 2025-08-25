import java.io.*;
import java.util.*;

/* 14502 연구소 골드4 중상
메모리 89208 kb
실행시간 348 ms
시간복잡도 O((N*M)C3 * bfs(N*M)) -> 바이러스 최소 2개 -> 최대 62C3(37820) * bfs 내부 최대 59번(바이러스 2개, 벽 3개) 수행 = 2,231,380
공간복잡도 O(N * M)
아이디어
	N*MC3 각각 조합마다 바이러스를 퍼뜨리는 bfs 수행
	세부 구현
		N*MC3 2차원 배열로 조합을 만드는 방법에서 막힘
		2차원 배열을 1차원 배열로 생각하고 열 길이인 M을 통해 x값과 y값을 나누기와 나머지 연산을 통해 설정
		dfs 수행
		3개의 벽을 설정 했다면 bfs 수행
*/
public class Main {
	static int N, M, map[][], virus_count, wall_count, safe_count, max_safe;
	static boolean[][] visited;
	static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static List<int[]> viruses;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		visited = new boolean[N][M];
		viruses = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 1) ++wall_count; // 벽 개수
	            if(map[i][j] == 2) {
	            	viruses.add(new int[] {i,j}); // 바이러스들 위치 저장
                }
			}
		}
		solution(0, 0); // dfs 수행
		System.out.println(max_safe);
	}
	
	private static void solution(int startIdx, int c) {
		if(c == 3) { // 벽 3개 설정 완료
			visited = new boolean[N][M]; //각각의 조합에 바이러스들 퍼지는거 처리하기 위해
			virus_count = viruses.size(); // 바이러스 초기 개수
			for(int[] virus : viruses) { // 바이러스 하나씩 출발 
                bfs(virus[0], virus[1]);
            }
			safe_count = N * M - (wall_count + c) - virus_count; // 0 개수
			max_safe = Math.max(max_safe, safe_count); // max 연산
			return; // 다른 조합으로
		}
		for (int i = startIdx; i < N * M; i++) { // 1차원 배열로 펴서 생각
			int x = i / M; // 몫은 x 값
			int y = i % M; // 나머지는 y 값
			if(map[x][y] == 1 || map[x][y] == 2) continue;
			map[x][y] = 1; // 벽으로 설정
			solution(i + 1, c + 1); // 설정 후 다음 진행
			map[x][y] = 0; // 벽 해제
			}
		}
	
	private static void bfs(int x, int y) { // 바이러스 퍼뜨리기
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[] {x,y});

        while (!q.isEmpty()) {
            int[] node = q.poll();
            for(int i = 0; i < dx.length; i++) {
                int nx = node[0] + dx[i];
                int ny = node[1] + dy[i];
                if(outOfIdx(nx, ny) || map[nx][ny] == 1  || map[nx][ny] == 2 || visited[nx][ny]) continue;
                visited[nx][ny] = true;
                ++virus_count; // 퍼진 바이러스 개수 카운팅
                q.offer(new int[] {nx,ny});
            }
        }
	}
	
    private static boolean outOfIdx(int nx, int ny) {
        return nx < 0 || ny < 0 || nx >= N || ny >= M;
    }
}
