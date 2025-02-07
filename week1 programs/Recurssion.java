package TechM;
import java.util.Scanner;

public class Recurssion {
	
	public static void method1(int num ) {
		if(num<=100) {
		System.out.println(num);
		num++; 
		method1(num);	}
		else {
			return ;
		}
		
	}
	public static void main(String[] args) {
		System.out.println("enter the start range of recurrsion");
		Scanner s=new Scanner(System.in);
		int times=s.nextInt();
		method1(times);
			
		}
		
	}


