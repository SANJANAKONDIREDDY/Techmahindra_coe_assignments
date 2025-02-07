package Week_1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class task_1 {
	//declaring a static method named processInput
    public static void processInput() {
    	//printing a message for the user to enter a number
        System.out.println("enter the input");
        //taking input from the user 
        Scanner s = new Scanner(System.in);
        //executing the code repeatedly using the while loop
        while (true) {
        	//
            try {
            	//reads the input from the user
                int userInput = s.nextInt();
                //checks the input from the user is zero or not 
                if (userInput == 0) {
                	//if the entered input is 0 then it will show the ArithmeticException 
                    throw new ArithmeticException("can't divide with zero");
                }
                //calculating the reciprocal of the user input
                double reciprocal = 1.0 / userInput;
                System.out.println(reciprocal);//printing the reciprocal
            } catch (ArithmeticException e) {//handles the division by zero
                System.out.println("ZeroDivisionError");
                System.out.println("enter a number");
            } catch (InputMismatchException e) {//handles the non numeric input
                System.out.println("ValueError");
                System.out.println("enter a valid number");
                s.next();
            } catch (Exception e) {//handles any other exception
                System.out.println("some other exception occurred");
            }
        }
    }

    public static void main(String[] args) {
      
        	processInput(); //calling the method
        
    }
}
