package com.springboot.coordinates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yangguotao
 * @Date: 2019/12/11
 * @Version 1.0
 */
public class test01 {

    public static void main(String[] args) {
//        int[] nums = new int[]{2, 1, 2, 5, 4, 5, 4, 8, 1};
//        Long xx = System.nanoTime();
//        int c = singleNumber2(nums);
//        Long cc = System.nanoTime();
//        System.out.println(c);
//        System.out.println(cc - xx);
//        Map<Integer,Integer> countMap = new HashMap<>();
//        for (Integer i : countMap.keySet()
//             ) {
//            System.out.println(i);
//        }
        File file = new File("H:/1.txt");
        ReaderTxt(file);
    }

    public static int singleNumber(int[] nums) {

        boolean falg = false;
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            for (Integer j : countMap.keySet()) {
                if (j == num) {
                    countMap.put(j, countMap.get(j) + 1);
                    falg = !falg;
                }
            }
            if (!falg) {
                countMap.put(num, 1);
            }
            falg = false;
        }
        for (Integer i : countMap.keySet()) {
            if (countMap.get(i) == 1) {
                return i;
            }
        }
        return 0;

    }

    public static int singleNumber2(int[] nums) {
        int len = nums.length;
        int result = 0;
        for (int i = 0; i < len; i++) {
            result ^= nums[i];
        }
        return result;
    }

    public static void ReaderTxt(File file){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            int len = 0;
            String str = null;
            while ((str=bufferedReader.readLine())!=null){
                String[] strarr = str.split(",");
                System.out.println(String.format("[%s,%s]",strarr[0],strarr[1]));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
