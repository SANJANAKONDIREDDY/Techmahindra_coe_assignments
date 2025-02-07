package TechM;
import java.util.Scanner;

public class Trailing_zeros {
	public static void factorial() {
		double n=25;
		double sum=1;
		for(double i=1;i<=n;i++) {
			sum*=i;
		}
		System.out.println(sum);
	}
	public static void trailing() {
		//printing to enter the user input
		System.out.print("enter a number:");
		//getting the input from the user
		Scanner s=new Scanner(System.in);
		//storing the input given by the user
		int num=s.nextInt();
		// if the given number is less then five then the message given in the print statement will be printed
		if (num<5) {
			System.out.println("there are no zeros");
		}
		// if the given number is greater than five then the else part will be executed
		else {
		//initializing the variable named quotient for storing the quotient
		int quotient=0;
		//using for loop for incrementing the values and finding the no of zeroes
		for(int i=5;i<=num;i*=5) {
			quotient+=num/i;
		}
		//printing the no of zeros
		System.out.println("the number of zeros are :"+ quotient);	
	}
	}

	public static void main(String[] args) {
		trailing();
		
	}
		
		

}
