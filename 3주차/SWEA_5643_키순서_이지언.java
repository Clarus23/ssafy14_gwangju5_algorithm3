
/*
 * 문제: SWEA_5643_키순서
 * 난이도 D4 체감난이도: 약간 어려움
 * [입력}
 * 빠른 처리를 위해  => BufferedReader + StringTokenizer
 * [출력]
 * system.out.println("#"+t+" "+ 결과값")
 * [로직]
 * 풀이 아이디어 
 * : 키순을 알기 위해서는 모든 간선과 직간접적으로 연결되어 있어햐함
 * : 모든 정점과 직간접적으로 연결된 정점의 갯수 = 키순을 알 수 있는 학생수
 * 
 * 시도
 * (1st) 인접행렬
 * 진입차수(i열) + 진출차수(j행) = 총 간선 수(E) : 모든 정점과 연결된 정점. 
 * fail : BFS로 해당 로직을 구현 x.
 * fail : 아무와도 비교하지 않은 학생를 누락. 잘못된 식. 
 * 
 * (2nd) 인접리스트
 * BFS로 노드 순회 중 간선으로 연결되지않은 노드는 순회를 못함. 
 * 고로, 방문관리 배열 내 값이 전부 참이면 헤당 노드는 모든 정점과 연결되어있으니 키순을 할 수있는 학생이다.
 * fail: 해당 로직 구현 x. 
 *
 *(3rd) 다른 사람 풀이
 *풀이 아이디어
 *나보다 큰 친구 + 나보다 작은 친구 = 총 학생 수 - 1
 *행은 자신보다 큰 친구(자신과 연결된)의 정보를 나타내고
 *열은 자신보다 작은 친구(자신과 연결된)의 정보를 나타낸다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
 
public class Solution_5643_키순서 {
 
    static int N, M, adj[][];
    static int gtCnt, ltCnt;
 
    public static void main(String[] args) throws IOException {
 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st; // 필요할 떄마다 객체를 생성을 하면 되니 선언만!
 
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine()); // 학생 수 : 정점 수
            M = Integer.parseInt(br.readLine()); // 관계 수 : 간선 수
           
            //그래프 구현 - 인접행렬
            adj = new int[N + 1][N + 1]; // 인접배열. index 0 사용 x => N+1
            
            int i, j;
            for (int m = 1; m <= M; m++) {
                st = new StringTokenizer(br.readLine());
                
                i = Integer.parseInt(st.nextToken()); // 작은 수
                j = Integer.parseInt(st.nextToken()); // 큰 수
                // 단방향 그래프
                adj[i][j] = 1;
            }
 
            //로직
            int res = 0; // 키순서를 아는 학생 수
            for (int k = 1; k <= N; k++) { //모든 학생 check
                gtCnt = 0; // 큰 친구 수
                ltCnt = 0; // 작은 친구 수
                // 자신보다 큰 친구를 찾으러
                gtBFS(k);
                // 자신보다 작은 친구를 찾으러
                ltBFS(k);
                // 자신보다 작은 친구와 큰 친구의 합이 N - 1 이라면 자신이 몇 번째인지 알 수 있음
                if(gtCnt + ltCnt == N - 1) res++;
            }
            
            System.out.println("#" + tc + " " + res);
        }
 
    }
 
    /**
     *  자신보다 키가 큰 학생을 찾아보자.
     * @param start 탐색의 출발 학생번호
     */
    private static void gtBFS(int start) {
 
        Queue<Integer> q = new LinkedList<>(); //순서관리 배열
        boolean[] visited = new boolean[N + 1];//방문 현황 파악, 인덱스 1부터 사용하니 +1
        q.add(start);//시작 정점 추가
        visited[start] = true;//bfs라 방문했다고 체크
        
        while(!q.isEmpty()) {//큐 안에 값이 있으면 없어질 때까지 반복
            int k = q.poll(); //방문
            for (int i = 1; i <= N; i++) {//인접쟁점 중보 없이 queue에  담기
                // 나보다 키가 크고 아직 확인하지 않은 친구 
                if (adj[k][i] == 1 && !visited[i]) {
                    q.add(i);
                    visited[i] = true;
                    gtCnt++;
                }
            }
        }
 
    }
 
    /**
     *  자신보다 키가 작은 학생을 찾아보자.
     * @param start 탐색의 출발 학생번호
     */
    private static void ltBFS(int start) { 
 
        Queue<Integer> q = new LinkedList<>(); // 큐 생성. 방문순서 관리
        boolean[] visited = new boolean[N + 1]; // 방문 관리. 
        q.add(start); // 큐 삽입
        visited[start] = true;// 방문 처리. 중복 방지.
        
        while(!q.isEmpty()) {//큐가 빌 떄까지 반복
            int k = q.poll();//맨 앞에 있는 요소 방문
            for (int i = 1; i <= N; i++) {//인접 요소 방문
                // 나보다 키가 작고 아지 확인하지 않은 친구 
                if (adj[i][k] == 1 && !visited[i]) {//열 = 진입차수
                    q.add(i);
                    visited[i] = true;
                    ltCnt++;
                }
            }
        }
 
    }
}
