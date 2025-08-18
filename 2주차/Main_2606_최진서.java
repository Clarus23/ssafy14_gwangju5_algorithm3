/**
 * 참고: Algo과평 Q3.'연결요소의 개수'와 유사한 문제
 *
 * 아이디어:
 *      1번 컴퓨터를 루트로 dfs 또는 bfs 를 돌며 탐색되는 노드들의 개수를 구하는 문제
 *      문제처럼 사이클이 있는 그래프는 탐색에 visited 를 꼭 사용해야한다. (무한 루프 방지)
 *
 * 메모리: 14080 kb
 * 시간: 108 ms
 * 난이도: 실버 3
 */

import java.io.*;
import java.util.*;

public class Main_2606_최진서 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 컴퓨터 수
        int M = Integer.parseInt(br.readLine()); // 연결 수

        // 컴퓨터 간의 연결 정보를 저장할 리스트 생성
        ArrayList<Integer>[] computer = new ArrayList[N+1];
        for (int i = 1; i < N+1; i++) computer[i] = new ArrayList<>();

        // 연결 정보 입력
        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            computer[a].add(b);
            computer[b].add(a);
        }

        // bfs 로 구현
        boolean[] visited = new boolean[N+1];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1); // 루트는 1번 컴퓨터
        visited[1] = true; // 1번 컴퓨터 방문 체킹

        while(!queue.isEmpty()) { // 더이상 탐색할 노드가 없으면 종료
            int node = queue.poll(); // 현재 탐색할 노드 꺼내기
            for (int child : computer[node]) { // 그 노드와 연결된 노드
                if (!visited[child]) { // 중 방문하지 않은 노드만
                    queue.add(child); // 탐색할 것이므로 큐에 넣는다.
                    visited[child] = true; // 각 노드를 단 한번만 방문하기 위해 방문 체킹하기
                }
            }
        }

        // 정답 노드 개수 구하기
        int cnt = 0;
        for (int i = 2; i < N+1; i++) { // 1번 컴퓨터는 정답에서 제외
            if (visited[i]) cnt++;
        }
        System.out.println(cnt);

        br.close();
    }
}

// ---------< dfs version >---------

//public class Main {
//    static HashSet<Integer> visited = new HashSet<>();
//    static ArrayList<Integer>[] computer;
//
//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//        int N = Integer.parseInt(br.readLine()); // 컴퓨터 수
//        int M = Integer.parseInt(br.readLine()); // 연결 수
//        computer = new ArrayList[N+1];
//        for (int i = 1; i < N+1; i++) computer[i] = new ArrayList<>();
//
//        for (int i = 0; i < M; i++) {
//            StringTokenizer st = new StringTokenizer(br.readLine());
//            int a = Integer.parseInt(st.nextToken());
//            int b = Integer.parseInt(st.nextToken());
//
//            computer[a].add(b);
//            computer[b].add(a);
//        }
//        dfs(1); // 루트는 1번 컴퓨터
//        System.out.println(visited.size() - 1); // 1번 제외
//
//        br.close();
//    }
//    static void dfs(int node) {
//        visited.add(node); // 해당 노드 방문 체킹
//        for (int child : computer[node]) {
//            // 방문하지 않은 노드라면 호출
//            if (!visited.contains(child)) dfs(child);
//        }
//    }
//}
