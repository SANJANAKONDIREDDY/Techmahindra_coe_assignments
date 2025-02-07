package Week_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnagramFinder {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s.length() < p.length()) {
            return result; // No anagrams possible if s is shorter than p
        }

        // Create frequency count for p
        HashMap<Character, Integer> pCount = new HashMap<>();
        for (char c : p.toCharArray()) {
            pCount.put(c, pCount.getOrDefault(c, 0) + 1);
        }

        // Create a frequency count for the first window in s
        HashMap<Character, Integer> sCount = new HashMap<>();
        for (int i = 0; i < p.length(); i++) {
            sCount.put(s.charAt(i), sCount.getOrDefault(s.charAt(i), 0) + 1);
        }

        // Check if the first window is an anagram
        if (sCount.equals(pCount)) {
            result.add(0);
        }

        // Slide the window over s
        for (int i = p.length(); i < s.length(); i++) {
            // Add the new character to the window
            char newChar = s.charAt(i);
            sCount.put(newChar, sCount.getOrDefault(newChar, 0) + 1);

            // Remove the character that is sliding out of the window
            char oldChar = s.charAt(i - p.length());
            sCount.put(oldChar, sCount.get(oldChar) - 1);
            if (sCount.get(oldChar) == 0) {
                sCount.remove(oldChar);
            }

            // Compare the counts
            if (sCount.equals(pCount)) {
                result.add(i - p.length() + 1);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        AnagramFinder finder = new AnagramFinder();
        String s = "cbaebabacd";
        String p = "abc";
        List<Integer> indices = finder.findAnagrams(s, p);
        System.out.println(indices); 
    }
}