package Week_1;

public class StringProcessor {

    // Method to reverse a given string
    public String reverseString(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    // Method to count occurrences of a substring in a given text
    public int countOccurrences(String text, String sub) {
        if (sub.isEmpty()) return 0; // Avoid counting empty substring
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(sub, index)) != -1) {
            count++;
            index += sub.length(); // Move index forward to avoid counting the same occurrence
        }
        return count;
    }

    // Method to split a string by spaces and capitalize each word
    public String splitAndCapitalize(String str) {
        String[] words = str.split(" ");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                           .append(word.substring(1)).append(" ");
            }
        }
        return capitalized.toString().trim(); // Remove trailing space
    }

    public static void main(String[] args) {
        StringProcessor processor = new StringProcessor();

        // Test reverseString
        String reversed = processor.reverseString("hello");
        System.out.println("Reversed: " + reversed); // Output: "olleh"

        // Test countOccurrences
        int occurrences = processor.countOccurrences("hello world, hello universe", "hello");
        System.out.println("Occurrences: " + occurrences); // Output: 2

        // Test splitAndCapitalize
        String capitalized = processor.splitAndCapitalize("hello world in java");
        System.out.println("Capitalized: " + capitalized); // Output: "Hello World From Java"
    }
}