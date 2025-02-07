package TechM;
import java.util.Scanner;

public class Stocksvalues {
    public static void main(String[] args) {
        int[] StockValues = {7, 1, 6, 5, 3, 2, 4};
        int MaxProfit = 0;

        for (int i = 0; i < StockValues.length; i++) {
            for (int j = i + 1; j < StockValues.length; j++) {
                int profit = StockValues[j] - StockValues[i];
                if (profit > MaxProfit) {
                    MaxProfit = profit;
                }
            }
        }
        
       
        System.out.println("the max profit is :"+ MaxProfit);
    }
}

