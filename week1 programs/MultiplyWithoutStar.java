package TechM;

import java.util.Scanner;

public class MultiplyWithoutStar {
    public static void main(String[] args) {
        // Prompt the user to enter two numbers for multiplication
        System.out.println("Enter the numbers:");
        
        // Create a Scanner object to read input from the user
        Scanner s = new Scanner(System.in);
        
        // Read the first number (multiplicand) from user input
        int number1 = s.nextInt();
        
        // Read the second number (multiplier) from user input
        int number2 = s.nextInt();
        
        // Initialize a variable to store the result of the multiplication
        int result = 0;
        
        // Use a loop to add the first number to itself 'number2' times
        for (int i = 0; i < number2; i++) {
            result += number1; // Accumulate the result
        }
        
        // Output the final result of the multiplication
        System.out.println(result);
        
        // Close the scanner to prevent resource leaks
        s.close();
    }
}
