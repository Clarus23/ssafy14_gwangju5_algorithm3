import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
* 금지된 숫자가 포함되지 않은 N번째 수 찾기
*/

public class Main_1681_줄세우기_박민영 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        int N = Integer.parseInt(st.nextToken());
        String L = st.nextToken(); // 금지된 숫자를 문자열로 바로 받음

        int count = 0; // 조건을 만족하는 숫자의 개수
        int num = 0;   // 검사할 현재 숫자

        while (count < N) {
            num++; 
            String numStr = String.valueOf(num);// 숫자를 문자열로 변환

            // 문자열에 금지된 숫자(L)가 포함되어 있지 않다면
            if (!numStr.contains(L)) {
                count++; // 카운트 증가
            }
        }

        System.out.println(num);
    }
}
