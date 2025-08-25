package _0825;
/*
 * 난이도 중
 * 90068KB 388ms
 * 풀이 아이디어: 3중 for문 + BFS
 * 벽의 개수가 3개로 정해져있으니 3중 for문으로 구현
 * 바이러스의 위치를 알고 있으니 바이러스 위치에서 bfs로 바이러스가 퍼지는 구간 연산
 * 남은 구역에서 공간을 카운트
 * 
 * 시간복잡도 : NM^4
 * 
 */
import java.io.*;
import java.util.*;

public class Main_14502_연구소_이주형 {
    static int N, M;
    static int[][] base;
    static List<int[]> empties = new ArrayList<>();
    static List<int[]> viruses = new ArrayList<>();
    static final int[] dr = {1, -1, 0, 0};
    static final int[] dc = {0, 0, 1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        base = new int[N][M];
        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < M; c++) {
                base[r][c] = Integer.parseInt(st.nextToken());
                if (base[r][c] == 0) empties.add(new int[]{r, c});
                else if (base[r][c] == 2) viruses.add(new int[]{r, c});
            }
        }

        int answer = 0;
        int E = empties.size();
        for (int i = 0; i < E; i++) {
            for (int j = i + 1; j < E; j++) {
                for (int k = j + 1; k < E; k++) {
                    int[][] map = new int[N][M];
                    for (int r = 0; r < N; r++) {
                        System.arraycopy(base[r], 0, map[r], 0, M);
                    }

                    int[] w1 = empties.get(i);
                    int[] w2 = empties.get(j);
                    int[] w3 = empties.get(k);
                    map[w1[0]][w1[1]] = 1;
                    map[w2[0]][w2[1]] = 1;
                    map[w3[0]][w3[1]] = 1;

                    spread(map);
                    answer = Math.max(answer, countSafe(map));
                }
            }
        }

        System.out.println(answer);
    }

    static void spread(int[][] map) {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        for (int[] v : viruses) q.offer(new int[]{v[0], v[1]});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d], nc = c + dc[d];
                if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
                if (map[nr][nc] == 0) {
                    map[nr][nc] = 2;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
    }

    static int countSafe(int[][] map) {
        int cnt = 0;
        for (int r = 0; r < N; r++)
            for (int c = 0; c < M; c++)
                if (map[r][c] == 0) cnt++;
        return cnt;
    }
}
