/**
 * 아이디어:
 *  조합 + 시뮬레이션 문제
 *
 * 메모리: 51304 kb
 * 시간: 280 ms
 * 난이도: ★★★☆☆
 */

import java.io.*;
import java.util.*;

public class Main_14502_최진서 {
    static int N, M;
    static int[][] map;
    static int[][] temp;
    static int[][] d = {{-1,0},{1,0},{0,-1},{0,1}}; // 상하좌우
    static int max;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        max = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 0) {
                    makeWall(i,j,1);
                }
            }
        }
        System.out.println(max);
    }
    static void makeWall(int x, int y, int count) {
        map[x][y] = 1;
        if (count == 3) {
            simulation();
        }
        else {
            int nx = x;
            int ny = y;
            if (!(x == N-1 && y == M-1)) {
                if (y == M-1) {
                    nx++;
                    ny = 0;
                } else ny++;

                for (int i = nx; i < N; i++) {
                    for (int j = ny; j < M; j++) {
                        if (map[i][j] == 0) makeWall(i,j,count+1);
                    }
                    ny = 0;
                }
            }
        }
        map[x][y] = 0; // 복구
    }
    static void simulation() {
        temp = new int[N][M];
        for (int i = 0; i < N; i++) temp[i] = map[i].clone();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 2) dfs(i,j);
            }
        }
        int cnt = 0; // 0 의 개수
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (temp[i][j] == 0) cnt++;
            }
        }
        max = Math.max(max, cnt);
    }
    static void dfs(int x, int y) {
        for (int i = 0; i < 4; i++) {
            int dx = x + d[i][0];
            int dy = y + d[i][1];
            if (0<=dx && dx<N && 0<=dy && dy<M
                && temp[dx][dy] == 0) {
                temp[dx][dy] = 2;
                dfs(dx,dy);
            }
        }
    }
}
