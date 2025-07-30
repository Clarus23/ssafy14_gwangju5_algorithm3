import java.io.*;
import java.util.StringTokenizer;

/*
 * 아이디어
 * dfs를 이용한 cycle check
 * 
 * cycle checking
 * 	dfs의 깊이가 4 이상이고(공이 4개 이상 존재하고)
 * 	시작칸과 맞닿아 있다면 => 	cycle이 존재한다.
 * 	그렇지 않다면 => 		cycle이 존재하지 않는다.
 * 
 * 배열의 모든 칸을 순회하며, dfs를 실행. cycle이 있다면 그 즉시 프로그램 종료.
 * 
 * 시간 복잡도
 * 배열 순회 => O(NM)
 * dfs logic => O(NM)
 * 
 * 총 시간 복잡도 => O((NM)^2)
 * N과 M은 2이상 50 이하이므로
 * 최악의 경우에도, 50^4 = 약 600만
 */

public class Main {
	static int n, m;							// 배열의 행과 열
	static String[] board;						// 게임 보드판(char형 2차원 배열 == String형 1차원 배열)
	static boolean[][] isVisited;				// 방문 한 노드를 체크하기 위한 boolean 2차원 배열
	static final int[] dRow = {0, 1, 0, -1};	// 방향설정을 위한 direction 배열
	static final int[] dCol = {1, 0, -1, 0};	// 방향설정을 위한 direction 배열
	static int startRow, startCol;				// 시작 행과 열 저장할 변수
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		board = new String[n];
		for(int i=0; i<n; ++i)
			board[i] = br.readLine();
		
		for(int row=0; row<n; ++row) {
			for(int col=0; col<m; ++col) {		// 배열을 차례대로 순회하며
				startRow = row;
				startCol = col;
				isVisited = new boolean[n][m];
				isVisited[row][col] = true;
				if(dfs(row, col, 1)) {			// cycle 체크
					bw.write("Yes");
					bw.flush();
					bw.close();
					
					return;
				}
			}
		}
		
		bw.write("No");
		bw.flush();
		bw.close();
	}
	
	static boolean dfs(int row, int col, int depth) {	// dfs 상세 로직
		for(int i=0; i<4; ++i) {
			int fRow = row + dRow[i];
			int fCol = col + dCol[i];
			
			// 보드를 넘어가거나, 현재 공과 색깔이 다르다면 continue
			if(fRow < 0 || fRow >= n || fCol < 0 || fCol >= m
					|| board[row].charAt(col) != board[fRow].charAt(fCol)) continue;
			
			// 공이 4개 이상 들어있고, 시작칸과 붙어 있다면 cycle이 존재함.
			if(depth >= 4 && startRow == fRow && startCol == fCol) return true;
			// 그렇지 않고, 아직 탐색을 하지 않았다면 => dfs 지속
			else if(!isVisited[fRow][fCol]) {
				isVisited[fRow][fCol] = true;
				if(dfs(fRow, fCol, depth+1)) return true;
			}
		}
		// 모든 탐색이 끝났을 시, cycle이 없었다면 false를 반환.
		return false;
	}
}
