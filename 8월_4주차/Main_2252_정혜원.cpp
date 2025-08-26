#include <iostream>
#include <vector>
#include <queue>
using namespace std;

/*
* # 풀이 아이디어: 위상정렬(Topological Sort)
* ## 간선이 입력되면, 그래프를 저장하며, inDegree를 계산.
* ## 모든 입력이 끝난 후, inDegree가 0인 node들을 queue에 삽입.
* ## bfs 수행. (inDegree가 0이 아니라면 --inDegree, 0이면 q에 삽입)
*
* # 시간복잡도: O(V+E)
*
* # 메모리: 3,944 kb
* # 실행시간: 24 ms
* # 체감난이도: 중하
*/

int main() {
	cin.tie(nullptr);
	ios::sync_with_stdio(false);

	int n, m;
	cin >> n >> m;
	
	vector<int> inDegree(n + 1, 0);
	vector<vector<int>> graph(n + 1);
	while (m--) {
		int a, b;
		cin >> a >> b;

		++inDegree[b];
		graph[a].push_back(b);
	}

	queue<int> q;
	for (int i = 1; i <= n; ++i) if (!inDegree[i]) q.push(i);
	while (!q.empty()) {
		int cur = q.front(); q.pop();
		cout << cur << " ";

		for (int next : graph[cur]) {
			if (!inDegree[next]) continue;

			if (--inDegree[next]) continue;
			q.push(next);
		}
	}

	return 0;
}
