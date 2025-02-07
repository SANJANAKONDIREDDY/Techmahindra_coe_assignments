package TechM;
import java.util.Scanner;

public class TwoSum {
	public static void main(String[] args) {
		int[] givenarray= {7,3,5,8,6};
		Scanner s=new Scanner(System.in);
		int target=s.nextInt();
		s.close();
		for(int i=0;i<givenarray.length;i++) {
			for(int j=i+1;j<givenarray.length;j++) {
			if(target==givenarray[i]+givenarray[j]) {
				System.out.println("the two numbers are:"+givenarray[i]+" , "+givenarray[j]);
			}
			
		}
		
		
	}

	}
	}
