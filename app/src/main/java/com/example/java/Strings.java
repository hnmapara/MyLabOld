package com.example.java;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mapara on 9/10/17.
 */

public class Strings {
    public static void main(String[] args) {
        System.out.println(" longest non repeating substring size = " + lengthOfLongestNonRepeatingSubstring("ABBKEWK"));
    }

    static int lengthOfLongestNonRepeatingSubstring(String s) {
        //System.out.println(" longest non repeating substring size = " + lengthOfLongestNonRepeatingSubstring("ABBKEWK"));

        int[] map = new int[128];
        int counter=0, begin=0, end=0, maxLength = 0;
        while(end < s.length()){
            if(map[s.charAt(end)] > 0) {
                counter++;
            }
            map[s.charAt(end)]++;
            end++;
            while(counter>0) {
                if(map[s.charAt(begin)] > 1) {
                    counter--;
                }
                map[s.charAt(begin)]--;
                begin++;
            }
            maxLength = Math.max(maxLength, end-begin); //while valid, update maxLength
        }
        return maxLength;
    }

    static int lengthOfLongestSubstring(String s) {
        //easier to understand
        //System.out.println(" longest non repeating substring size = " + lengthOfLongestNonRepeatingSubstring("ABBKEWK"));

        int i = 0, j = 0, max = 0;
        Set<Character> set = new HashSet<>();

        while (j < s.length()) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                max = Math.max(max, set.size());
            } else {
                set.remove(s.charAt(i++));
            }
        }

        return max;
    }
}
