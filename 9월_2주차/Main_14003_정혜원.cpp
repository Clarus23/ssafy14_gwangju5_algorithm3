#include <iostream>
#include <vector>
using namespace std;

/*
 * # 풀이 아이디어: DP (동적 계획법) + 이진 탐색 (Binary Search) + 역추적
 * 	- 가장 긴 증가하는 부분 수열(LIS)을 O(N log N)으로 찾는 표준적인 풀이법입니다.
 * 	- lis[i]: 길이가 (i+1)인 증가 부분 수열의 마지막 원소 중 가장 작은 값.
 * 	- `lis` 배열 항상 오름차순으로 정렬된 상태 유지.
 * 	- 배열의 각 원소 `arr[i]`에 대해 이진 탐색을 사용하여 `lis` 배열에서 `arr[i]`가 들어갈 위치를 찾음.
 * 		- 만약 `arr[i]`가 `lis`의 모든 원소보다 크다면, 기존 LIS의 뒤에 붙여 LIS의 길이를 1 늘림.
 * 		- 그렇지 않다면, `arr[i]`보다 크거나 같은 첫 번째 원소를 `arr[i]`로 교체.(더 작은 값으로 끝나야 이후에 더 긴 수열을 만들 가능성이 높음)
 * 	- 실제 LIS 수열을 복원하기 위해, `index` 배열에 각 `arr[i]`가 `lis` 배열의 몇 번째 인덱스에 위치했는지를 기록.
 * 	- 모든 탐색이 끝난 후, `index` 배열을 뒤에서부터 역추적하여 LIS를 구성하는 원소들을 찾음.
 * 
 * # 시간복잡도: O(N log N)
 * 
 * # 메모리: 17,652 kb
 * # 실행시간: 200 ms
 * # 체감난이도: 중
 */

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	
	int n; cin >> n;
	
	vector<int> arr(n);
	vector<int> lis(n);
	vector<int> index(n);
	
	int size=0;
	for(int i=0; i<n; ++i) {
		cin >> arr[i];
		
		int s=0, e=size;
		while(s < e) {
			int m = (s+e)/2;
			
			if(lis[m] < arr[i]) s = m+1;
			else e = m;
		}
		
		lis[e] = arr[i];
		if(e == size) ++size;
		
		index[i] = e;
	}
	
	int targetIdx = size-1;
	vector<int> ans(size);
	
	for(int i=n-1; i>=0; --i) {
		if(index[i] != targetIdx) continue;
		
		ans[targetIdx--] = arr[i];
	}
	
	cout << size << "\n";
	for(int i=0; i<size; ++i) cout << ans[i] << " ";
	
	return 0;
}
