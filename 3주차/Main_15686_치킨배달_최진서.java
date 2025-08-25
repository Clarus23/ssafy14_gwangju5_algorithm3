/**
 * 아이디어:
 * 	1. 치킨집 좌표, 집 좌표를 어딘가에 저장한다.
 * 		chickens, homes 배열
 * 	2. 치킨집 리스트에서 M 개를 구한다.
 * 		그리디: 치킨집의 수가 많을수록 도시의 치킨거리는 줄어든다. 따라서 치킨집의 개수를 0~M 개 중 M 개로 설정한다.
 * 		중복되지 않는 M개를 순서없이 고르는 것 : 조합
 * 	3. 구한 조합을 가지고 도시의 치킨거리를 구해보고, 최소값을 찾는다.
 * 
 * 메모리: 20056 kb
 * 시간: 240 ms
 * 난이도: ★★★☆☆
 */

import java.io.*;
import java.util.*;

public class Main_15686_치킨배달_최진서 {
	static int N, M, answer;
	static ArrayList<int[]> chickens = new ArrayList<>();
	static ArrayList<int[]> homes = new ArrayList<>();
	static ArrayList<Integer> selectedChicken = new ArrayList<>();
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 도시의 길이
		M = Integer.parseInt(st.nextToken()); // 남겨질 치킨집 수
		answer = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				int k = Integer.parseInt(st.nextToken()); // 0,1,2
				if (k == 1) homes.add(new int[] {i,j});
				else if (k == 2) chickens.add(new int[] {i,j});
			}
		}
		combination(0);
		System.out.println(answer);
		br.close();
	}
	static void combination(int start) {
		if (selectedChicken.size() == M) { // 다 선택 했으면
			calc(); // 도시의 치킨거리 계산
		}
		else for (int i = start; i < chickens.size(); i++) {
			selectedChicken.add(i); // i: 치킨집 번호
			combination(i+1);
			selectedChicken.remove(selectedChicken.size()-1); // 해당 치킨집 선택해제 (다음 경우를 찾기 위해 복구)
		}
	}
	static void calc() {
		int cityChickenLoad = 0;
		for (int[] home : homes) { // home: 집의 좌표 
			int hx = home[0]; // 집의 x좌표
			int hy = home[1]; // 집의 y좌표
			int chickenLoad = Integer.MAX_VALUE;
			for (int i : selectedChicken) { // i: 치킨집 번호
				int cx = chickens.get(i)[0]; // i번 치킨집 x좌표
				int cy = chickens.get(i)[1]; // i번 치킨집 y좌표
				// 그 집에서의 치킨거리
				chickenLoad = Math.min(chickenLoad, Math.abs(hx-cx) + Math.abs(hy-cy));
			}
			cityChickenLoad += chickenLoad; // 각 집에서의 '치킨거리'를 누적시켜 '도시의 치킨거리' 구하기
		}
		answer = Math.min(answer, cityChickenLoad); // 구한 도시의 치킨거리로 모든 조합에서의 최소값을 갱신
	}
}
