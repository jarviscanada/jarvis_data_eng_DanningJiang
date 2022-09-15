package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    //compare two maps
    public static <K,V> boolean compareMaps(Map<K,V> m1, Map<K,V> m2) {
//        if(m1.size() != m2.size()){
//            return false;
//        }
//
//        for (Object key1 : m1.keySet()){
//            if (m2.containsKey(key1)){
//                Object value1 = m1.get(key1);
//                Object value2 = m2.get(key1);
//                if(!value1.equals(value2)){
//                    return false;
//                }
//            }else {
//                return false;
//            }
//        }
//        return true;
        return m1.equals(m2);
    }
    public static int fibonacci(int n){
        Map<Integer,Integer> cache = new HashMap<>();
        if(n<2){
            return n;
        }
        if(cache.containsKey(n)){
            return cache.get(n);
        }
        int result = fibonacci(n-1) + fibonacci(n-2);
        cache.put(n,result);
        return result;
    }

    /**
     * Two Sum - HashMap method
     * Time Complexity:O(N)
     * Space Complexity:O(N)
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i],i);
        }
        for (int j = 0; j < nums.length; j++) {
            int diff = target - nums[j];
            if (map.containsKey(diff) &&  map.get(diff) != j) {
                result[0] = j;
                result[1] = map.get(diff);
                return result;
            }
        }
        return result;
    }

}
