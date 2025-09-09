#include <iostream>
#include <vector>
#include <queue>
using namespace std;

typedef long long ll;

/*
 * # 풀이 아이디어: Multi-source Dijkstra
 * 	- 특정 종류의 가게가 여러 개 있을 때, 각 정점에서 가장 가까운 가게까지의 거리를 구해야 합니다.
 * 	- 이는 시작점이 여러 개인 다익스트라, 즉 '멀티소스 다익스트라'로 해결할 수 있습니다.
 * 	- 다익스트라 알고리즘을 시작할 때, 우선순위 큐에 한 개의 시작점이 아닌 모든 해당 종류의 가게 위치를 거리 0으로 넣고 시작합니다.
 * 	- 이 과정을 가게 종류 1과 가게 종류 2에 대해 각각 한 번씩, 총 두 번 실행합니다.
 * 	- 두 번의 다익스트라가 끝나면, 각 정점에서 가장 가까운 가게1까지의 거리와 가장 가까운 가게2까지의 거리를 모두 알 수 있습니다.
 * 	- 마지막으로 모든 정점을 순회하며 아래 조건을 만족하는 최적의 위치를 찾습니다.
 * 		1. 현재 정점이 가게가 아닐 것
 * 		2. 가게1까지의 거리가 제한 거리 이내일 것
 * 		3. 가게2까지의 거리가 제한 거리 이내일 것
 * 		4. 위 조건들을 만족하는 정점들 중, (가게1까지의 거리 + 가게2까지의 거리)가 최소가 되는 정점
 *
 * # 시간복잡도: O(E * log(V))
 * 	- 입력: O(E + V)
 *	- dijkstra: O(E log V)
 *
 * # 메모리: 11,632 kb
 * # 실행시간: 100 ms
 * # 체감난이도: 중상
 */


const ll inf = 1e18;	// 거리의 최대값이 int범위를 넘어갈 가능성이 존재하므로 long long 사용.

int v, e;				// 정점, 간선 개수

int storeNum[2];		// 가게의 개수
int limits[2];			// 가게 까지의 거리 제한

vector<vector<pair<int, int>>> graph;	// 인접리스트 {거리, 도착 node}
vector<vector<int>> stores(2);			// 가게의 번호
vector<vector<ll>> dists(2);			// 각 정점에서 가장 가까운 가게까지의 거리

vector<bool> isStore;					// 해당 정점이 가게인지 판별

void init() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	
	cin >> v >> e;
	
	// 양방향 그래프 인접리스트로 입력
	graph.assign(v+1, vector<pair<int, int>>());
	while(e--) {
		int v1, v2, weight;
		cin >> v1 >> v2 >> weight;
		
		graph[v1].push_back({weight, v2});
		graph[v2].push_back({weight, v1});
	}
	
	// 가게에 대한 정보 입력
	isStore.resize(v+1);
	for(int i=0; i<2; ++i) {
		cin >> storeNum[i] >> limits[i];
		
		stores[i].resize(storeNum[i]);
		dists[i].assign(v+1, inf);
		
		// multi-source dijkstra를 위해, 모든 가게의 시작 거리 0으로 설정
		for(int& it : stores[i]) {
			cin >> it;
			dists[i][it] = 0;
			isStore[it] = true;
		}
	}
}

void dijkstra(int type) {
	// 우선순위 큐. first: distance(음수; 내림차순을 오름차순으로 바꾸기 위해), second: node 번호
	priority_queue<pair<ll, int>> q;
	
	// 모든 가게를 q에 시작점으로 삽입
	for(int& startNode : stores[type]) q.push({0, startNode});
	
	while(!q.empty()) {
		ll dist = -1 * q.top().first; // distance를 다시 양수로 변환
		int cur = q.top().second;
		q.pop();
		
		// 더 짧은 경로가 있었다면, continue
		if(dist > dists[type][cur]) continue;
		
		// 현재 node와 연결된 다음 node들을 탐색
		for(pair<int, int>& edge : graph[cur]) {
			ll nextDist = dist + edge.first;
			
			if(nextDist >= dists[type][edge.second]) continue;
			
			// distance가 더 짧다면 갱신 및 큐에 추가
			dists[type][edge.second] = nextDist;
			q.push({-1 * nextDist, edge.second});
		}
	}
}
 
int main() {
	init();	// 입력
	// 각 가게에 대해 dijkstra 실행
	for(int i=0; i<2; ++i)
		dijkstra(i);
	
	// 모든 node를 순회하며 최적 장소 판별.
	ll minDist = inf;
	for(int i=1; i<=v; ++i) {
		// 조건1: 가게가 아니어야 함.
		if(isStore[i]) continue;
		// 조건2: 각 가게 까지의 거리가 limits보다 짧아야함.
		if(dists[0][i] > limits[0] || dists[1][i] > limits[1]) continue;
		
		// 거리 계산 후 최소값 갱신
		int dist = dists[0][i] + dists[1][i];
		minDist = (dist < minDist) ? dist : minDist;
	}
	
	// 조건을 만족하는 node가 없다면 -1 반환
	cout << (minDist == inf ? -1 : minDist);
	
	return 0;
}
