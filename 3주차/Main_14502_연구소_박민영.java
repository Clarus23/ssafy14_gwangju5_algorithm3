import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
* BFS
* 큐에서 꺼낸 바이러스와 인접한 모든 빈칸을 감염시키고, 새로 감염된 칸들을 다시 큐에 넣는다.
*/

public class Main_14502_연구소_박민영 {
    
    static int N, M;
    static int[][] originalMap;
    static ArrayList<int[]> emptySpaces = new ArrayList<>();
    static ArrayList<int[]> viruses = new ArrayList<>();
    static int maxSafeZone = 0;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        originalMap = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                originalMap[i][j] = Integer.parseInt(st.nextToken());
                    emptySpaces.add(new int[]{i, j});
                } else if (originalMap[i][j] == 2) {
                    viruses.add(new int[]{i, j});
                }
            }
        }

        generateWallCombinations(0, 0);
        System.out.println(maxSafeZone);
    }

    public static void generateWallCombinations(int start, int depth) { //벽세우기
        if (depth == 3) {
            bfs(); //3개가 세워지면 bfs로 해당 상태에서 바이러스가 얼마나 퍼지는지 확인 
            return;
        }

        for (int i = start; i < emptySpaces.size(); i++) {
            int[] wallPos = emptySpaces.get(i);
            int r = wallPos[0];
            int c = wallPos[1];
            
            originalMap[r][c] = 1;
            generateWallCombinations(i + 1, depth + 1);
            originalMap[r][c] = 0;
        }
    }

    public static void bfs() {
        int[][] tempMap = new int[N][M];
        for (int i = 0; i < N; i++) {
            System.arraycopy(originalMap[i], 0, tempMap[i], 0, M);
        }

        Queue<int[]> q = new LinkedList<>();
        for(int[] virus : viruses) {
            q.add(virus);
        }

        while (!q.isEmpty()) {
            int[] current = q.poll();
            int r = current[0];
            int c = current[1];

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nr < N && nc >= 0 && nc < M) {//감염시키기
                    if (tempMap[nr][nc] == 0) {
                        tempMap[nr][nc] = 2;
                        q.add(new int[]{nr, nc});
                    }
                }
            }
        }
        
      int safeZone = 0;
      for (int i = 0; i < N; i++) {
          for (int j = 0; j < M; j++) {
              if (tempMap[i][j] == 0) { 
                  safeZone++;
              }
        }
    }
    maxSafeZone = Math.max(maxSafeZone, safeZone);
    }
    
  
}
