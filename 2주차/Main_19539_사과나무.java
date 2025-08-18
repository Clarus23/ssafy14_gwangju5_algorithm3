
package asd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_19539_사과나무 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        
        int N= Integer.parseInt(br.readLine());
        
        StringTokenizer st= new StringTokenizer(br.readLine());
        int sum=0;
        int count=0;
        for(int i=0;i<N;i++) {
            int a=Integer.parseInt(st.nextToken());
            if(a%2==1) {
                count++;
            }
            sum+=a;
        }

        
        if(sum%3==0) {
            if(count <= sum/3) {// 총 sum/3번 물을 뿌림
            System.out.println("YES");}
            else {
                System.out.println("NO");
            }
        }else {
            System.out.println("NO");
        }
    }

}
