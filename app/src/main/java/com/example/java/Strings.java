package com.example.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mapara on 9/10/17.
 */

public class Strings {
    public static void main(String[] args) {
        //System.out.println(" longest non repeating substring size = " + lengthOfLongestNonRepeatingSubstring("ABBKEWK"));
        System.out.println("Minimum substring = " + minWindowHavingAllCharacter("ADOBECODEBANC", "CDE"));
        System.out.println("Minimum substring = " + minWindowHavingAllCharacter("ADOBECODEBANC", "ADE"));
        System.out.println("Minimum substring = " + minWindowHavingAllCharacter("ABCDCA", "AD"));
        System.out.println("Minimum substring = " + minWindowHavingAllCharacter("ADOBECODEBANC", "ABC"));
        System.out.println("Minimum substring = " + minWindowHavingAllCharacter("ADOBECODEBANC", "RAD"));
    }

    public static String minWindowHavingAllCharacter(String str, String substr) {
        int targetCount = substr.length();
        Map<Character, Integer> targetMap = new HashMap<>();
        for (char c : substr.toCharArray()) {
            if (targetMap.containsKey(c)) {
                targetMap.put(c, targetMap.get(c) + 1);
            } else {
                targetMap.put(c, 1);
            }
        }

        int minStart=0, minLen=Integer.MAX_VALUE;
        int head=0, tail=0, counter=0;
        Map<Character, Integer> smap = new HashMap<>();
        while(tail < str.length()) {
            if (targetMap.containsKey(str.charAt(tail))) {
                smap.put(str.charAt(tail), smap.get(str.charAt(tail)) == null ? 1 : smap.get(str.charAt(tail)) + 1);
                counter++;
            }
            if (counter == targetCount) {
                if (isValid(targetMap, smap)) { //find smaller window
                    if (minLen > (tail - head)) {
                        minStart = head;
                        minLen = tail - head;
                    }
                    while (!targetMap.containsKey(str.charAt(head))) {
                        if (minLen > (tail - head)) {
                            minStart = head;
                            minLen = tail - head;
                        }
                        head++;
                    }
                    if (minLen > (tail - head)) {
                        minStart = head;
                        minLen = tail - head;
                    }
                    smap.put(str.charAt(head), smap.get(str.charAt(head)) - 1);
                    counter--;
                    head++;
                } else {
                    while (!targetMap.containsKey(str.charAt(head))) {
                        head++;
                    }
                    smap.put(str.charAt(head), smap.get(str.charAt(head)) - 1);
                    counter--;
                    head++;
                }

            }
            tail++;
        }

        return minLen == Integer.MAX_VALUE ? "" : str.substring(minStart, minStart+minLen+1);
    }

    public static boolean isValid(Map<Character, Integer> targetMap, Map<Character, Integer> smap) {
        for (Character c : targetMap.keySet()) {
            if (smap.get(c) == null) return false;
            if (smap.get(c).intValue() != targetMap.get(c).intValue()) {
                return false;
            }
        }
        return true;
    }

    public static String minWindowHavingAllCharacterLeetCode(String str, String substr) {
        //String str = "ADOBECODEBANC";
        //String substr = "ABC";
        //O/p -> "BANC"

        int[] map = new int[128];
        for (char c : substr.toCharArray()) {
            map[c] = map[c] + 1;
        }
        int counter = substr.length(), begin = 0, end = 0, d = Integer.MAX_VALUE, head = 0;

        while(end < str.length()) {
            if (map[str.charAt(end)] > 0) {
                counter--; //in substr
            }
            map[str.charAt(end)]--;
            end++;
            while (counter == 0) { //valid
                if (end - begin < d) d = end - (begin);
                if (map[str.charAt(begin)] == 0) {
                    counter++; //make invalid
                }
                map[str.charAt(begin)]++;
                begin++;
            }
        }
        return d ==Integer.MAX_VALUE ? "" : str.substring(begin-1, end);
    }

/*
    public string minWindowLeetCode(string s, string t) {
        vector<int> map(128,0);
        for(auto c: t) map[c]++;
        int counter=t.size(), begin=0, end=0, d=INT_MAX, head=0;
        while(end<s.size()){
            if(map[s[end++]]-->0) counter--; //in t
            while(counter==0){ //valid
                if(end-begin<d)  d=end-(head=begin);
                if(map[s[begin++]]++==0) counter++;  //make it invalid
            }
        }
        return d==INT_MAX? "":s.substr(head, d);
    }
*/
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

    static List<Integer> findStartIndexOfAllAnagramsOfTargetString(String s, String p) {

        // Input: s: "cbaebabacd" p: "abc"

        // Output: [0, 6]
            List<Integer> res = new ArrayList<>();
            Map<Character, Integer> targetMap = new HashMap<>();

            //Init map with target character and count
            for (int i=0; i<p.length();i++) {
                targetMap.put(p.charAt(i), targetMap.getOrDefault(p.charAt(i), 0) + 1);
            }

            int counter = 0;
            int tLen = p.length();
            for (int i=0; i< s.length(); i++) {

                //when window moves, put back the characters going out of window and reduce the counter if the
                //character count is >=1
                if(i >= tLen) {
                    if (targetMap.containsKey(s.charAt(i-tLen))){
                        targetMap.put(s.charAt(i-tLen), targetMap.get(s.charAt(i-tLen))+ 1);
                        if (targetMap.get(s.charAt(i-tLen)) >= 1)
                            counter--;
                    }
                }
                //if the character is not in map, continue
                if (!targetMap.containsKey(s.charAt(i))) {
                    continue;
                }

                //character is found in map. reduce the character count and increase the counter
                //only if the character count is >= 0
                targetMap.put(s.charAt(i), targetMap.get(s.charAt(i)) - 1);
                if (targetMap.get(s.charAt(i)) >= 0)
                    counter++;

                //If the counter == target length size, add to the result set
                if (counter == tLen) {
                    res.add(i-tLen+1);
                }

                // SLIDE THE WINDOW....
            }

            return res;

    }
}
