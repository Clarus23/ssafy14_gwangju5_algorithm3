package test;

import java.io.*;
import java.util.*;

/*	2178 미로 탐색 실버 1 하
 * 	메모리 14456 kb
 *	실행시간 112 ms
 *	시간복잡도 O(N*M) N,M = 100
 *	공간복잡도 O(N*M)
 *	아이디어 
 *		
*/
public class Main {
    static int N;
    static int M;
    static int[] dx = {0,1,0,-1}; // 상 하 좌 우 탐색용
    static int[] dy = {1,0,-1,0};
    static int[][] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            String lines = br.readLine(); // 붙어있는 문자열
            for (int j = 0; j < M; j++) {
                map[i][j] = lines.charAt(j)-'0'; // 문자열 하나씩 분리하여 배열에 저장
            }
        }
        bfs(0,0); // bfs로 최적의 이동횟수 연산
        System.out.println(map[N-1][M-1]);
    }

    private static void bfs(int i, int j) { // 파라미터 = 시작 좌표
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[] {i,j});
        while (!q.isEmpty()){
            int[] now = q.poll(); // 현재 위치
            for (int k = 0; k < 4; k++) { // 사방 탐색
                int nx = now[0] + dx[k];
                int ny = now[1] + dy[k];
                
                // 미로의 범위를 벗어나거나 0(벽)으로 막힌 곳 or 이미 방문한 곳(즉 갱신된 곳)
                if(nx < 0 || ny < 0 || nx >= N || ny >= M || map[nx][ny] != 1) continue;
                map[nx][ny] = map[now[0]][now[1]] + 1; // map에 직접 이동 count (이전 count 누적)
                q.offer(new int[] {nx,ny}); // 다음 이동 위치 큐에 대입
            }
        }
    }

}
