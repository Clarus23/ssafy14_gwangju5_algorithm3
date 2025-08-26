import java.io.*;
import java.util.*;
/* 1868 파핑파핑 지뢰찾기 D4 중상
메모리 42,132 kb
실행시간 192 ms
시간복잡도 O(N * N)
공간복잡도 O(N * N)
아이디어
	countMap 선언하여 각 자리에 주변 지뢰 수 업데이트
	주변 지뢰 0개인 친구들 먼저 (우선순위)
	0개는 어떤걸 먼저 누르는지 신경 X 연결되어있다면 어떤걸 먼저 누르든 1번
	0개를 다 눌러봤는데 남아있는게 있다면 이건 주변에 지뢰가 존재하는 숫자임
	각각 개별 count 해줘야 함
	이를 visited 방문 배열로 처리
	지뢰가 있는 곳 입력값 받을 때 방문 처리
	0인 곳 bfs 돌면서 주변 방문 처리
	다 돌았다면 아직 방문이 안된 부분 각각 count
*/
public class Solution {
	static int N, M, count, countMap[][];
	static boolean[][] visited;
	static int[] dx = {0, 1, 0, -1, 1, 1, -1, -1};
	static int[] dy = {1, 0, -1, 0, 1, -1, 1, -1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
	    for (int t = 1; t <= T; t++) { 
		    sb.append("#").append(t).append(" ");
		    N = Integer.parseInt(br.readLine());
		    countMap = new int[N][N];
		    visited = new boolean[N][N];
		    for (int i = 0; i < N; i++) {
				String line = br.readLine();
				for (int j = 0; j < N; j++) {
					// 지뢰 -1로 설정(숫자로 표현)
					if(line.charAt(j) == '*') {
						visited[i][j] = true;
						countMap[i][j] = -1;
					}
				}
			}

		    for (int i = 0; i < N; i++) { // 나 기준 주변 지뢰 개수 업데이트
				for (int j = 0; j < N; j++) {
					if(countMap[i][j] == -1) continue;
					for (int k = 0; k < dx.length; k++) {
						int nx = i + dx[k];
						int ny = j + dy[k];
						if(outOfIdx(nx, ny)) continue;
						// 바로 map에 갱신
						if(countMap[nx][ny] == -1) ++countMap[i][j];
					}
				}
			}
		    count = 0;
		    // 0 먼저 수행 나머지 남은 것들은 아래에서 각각 count
		    for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// 이미 처리했거나, 0이 아닌 친구들 넘어가기
					if(visited[i][j] || countMap[i][j] != 0) continue;
					// 0하나 눌렀을 때 처리
					solution(i, j);
					++count; // 한번 누름
				}
			}
		    
		    // 여기서 남은 숫자 각각 count
		    for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if(!visited[i][j]) ++count;
				}
			}		    
		    sb.append(count).append("\n");
	    }
	    System.out.println(sb);
	}
	
	private static void solution(int sx, int sy) {
		Queue<int[]> q = new ArrayDeque<>();
		visited[sx][sy] = true;
		q.offer(new int[] {sx, sy});
		while (!q.isEmpty()) { // 큐가 빈다면 더이상 처리해야할 부분 없음(0 끊김)
			int[] node = q.poll();
			int x = node[0];
			int y = node[1];
			for (int i = 0; i < dx.length; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				if(outOfIdx(nx, ny) || visited[nx][ny]) continue;
				if(countMap[nx][ny] != 0) { // 0 주변 방문 처리 
					visited[nx][ny] = true;
					continue;
				}
				// 0 주변에 0이 있으면 이 친구도 주변 처리해야하니 큐에 넣기
				visited[nx][ny] = true;
				q.offer(new int[] {nx, ny});
				}
			}
		}
	
	private static boolean outOfIdx(int nx, int ny) {
		return nx < 0 || ny < 0 || nx >= N || ny >= N;
	}
}
