import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/* O(N)만에 연속 최대 합을 구할 수 있는 로직!*/
public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] a = new int[N+1];
		a[0]=-1000;
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=1;i<N+1;i++) {
			a[i] = Integer.parseInt(st.nextToken());
		}
		int ans=-1000;
		for(int i=1;i<N+1;i++) {
			a[i]=Math.max(a[i], a[i]+a[i-1]);
			ans=Math.max(ans, a[i]);
		}
		System.out.println(ans);
	}
}
