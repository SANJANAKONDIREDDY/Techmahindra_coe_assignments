package TechM;
import java.util.Scanner;

public class ExceptionHandling {
	public static void main(String[] args) {
		System.out.println("enter the numbers:");
		Scanner s=new Scanner(System.in);
		int number1=s.nextInt();
		int number2=s.nextInt();
		s.close();
		int result=-1;
		try {
			 result=number1/number2;
			System.out.println(result);
		}
		catch(ArithmeticException e) {
			System.out.println("exception occured:"+e.getMessage());}
		finally {
				System.out.println(result);
			}
		}
	}
	
