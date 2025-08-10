/*
아이디어: 완전탐색 (dfs)
    그래프에 사이클 여부를 확인하는 문제
    출발지점 부터 사이클인지 확인하는 방법이 아닌,
    'O' 모양이 아닌 'Q' 모양 처럼 중간 노드에서 사이클이 발생할 경우를 확인하는 방법으로 접근하였다.
    1. dfs 탐색은 되돌아 가지 않는 경로로 설정한다. lastX, lastY 변수로 구현
    2. 탐색 도중 이미 왔던 경로를 만났다면 사이클이 있는것으로 판단한다. check[][] 테이블로 구현
메모리: 14220 KB
시간: 92 ms
난이도: ★★☆☆☆
 */

import java.util.*;
import java.io.*;
class Main_16929_최진서 {
    static int N; // 게임판 세로길이
    static int M; // 게임판 가로길이
    static char alphabet; // dfs 실행할 알파벳 (매번 바뀜)
    static char[][] map; // 게임판
    static boolean[][] check; // visited 대용, 체킹 테이블
    static int[][] d = {{-1,0}, {1,0}, {0,-1}, {0,1}}; // 상하좌우 탐색에 사용
    static boolean answer; // false
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];
        check = new boolean[N][M]; // false 로 자동 초기화
        // 게임판 입력받기
        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j);
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!check[i][j]) { // 탐색하지 않은 곳이면
                    alphabet = map[i][j]; // dfs 수행을 위한 알파벳 갱신
                    dfs(i, j, 0, 0); // 현재 위치(x, y), 이전 위치(lastX, lastY)
                }
            }
        }
        if (answer) System.out.println("Yes");
        else System.out.println("No");
    }
    public static void dfs(int x, int y, int lastX, int lastY) {
        check[x][y] = true; // 방문 체킹
        for (int i = 0; i < 4; i++) { // 4방 탐색
            int dx = x + d[i][0]; // 이동할 x좌표
            int dy = y + d[i][1]; // 이동할 y좌표
            if (0 <= dx && dx < N && 0 <= dy && dy < M // 범위 체크
                    && !(dx == lastX && dy == lastY) // 바로 이전인 곳이 아니여야함
                    && map[dx][dy] == alphabet) { // 같은 알파벳이면
                // 이동가능
                if (check[dx][dy]) { // 왔던 곳이라면 cycle 이라는 뜻
                    answer = true;
                    return;
                }
                else dfs(dx, dy, x, y);
            }
        }
    }
}
