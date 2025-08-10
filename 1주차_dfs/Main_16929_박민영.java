import java.io.*;
import java.util.StringTokenizer;

public class Main_16929_박민영 {
	
	static boolean cycle = false;
	static int op_x[] = {1, -1, 0, 0};
	static int op_y[] = {0, 0, 1, -1};
	static int N, M;
	static int Visited[][];
	static char Map[][];
	public static void main(String[] args) throws IOException {
		// dfs
		String s;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		Visited = new int[N][M];
		Map = new char[N][M];
		
		for(int i = 0; i < N; ++i) {
			s = br.readLine();
			for(int j = 0; j < M; ++j)
				Map[i][j] = s.charAt(j);
		}
		
		for(int i = 0; i < N; ++i) {
			for(int j = 0; j < M; ++j) {
				if(Visited[i][j] == 0) {
					dfs(Map[i][j], i, j, -1, -1);
					if(cycle)
						break;
				}
			}
			if(cycle)
				break;
		}
		
		System.out.println(cycle? "Yes" : "No");
	}
	
	static void dfs(char alpha, int x, int y, int prev_x, int prev_y) {
		int temp_x, temp_y;
		
		Visited[x][y] = Map[x][y] - 'A' + 1;
		for(int i = 0; i < 4; ++i) {
			temp_x = op_x[i] + x;
			temp_y = op_y[i] + y;
			
			if(temp_x >= 0 && temp_x < N && temp_y >= 0 && temp_y < M) {
				if(temp_x != prev_x || temp_y != prev_y) {
					if(Map[temp_x][temp_y] == alpha) {
						if(Visited[temp_x][temp_y] == 0)
							dfs(alpha, temp_x, temp_y, x, y);
						else {
							cycle = true;
							break;
						}
					}
				}
			}
			if(cycle)
				break;
		}
	}

}
