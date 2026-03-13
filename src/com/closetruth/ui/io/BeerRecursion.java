package com.closetruth.ui.io;

import java.util.HashMap;
import java.util.Map;

public class BeerRecursion {
    public static void main(String[] args) {
        // 定义Map 存储 金钱-瓶数-盖数-空瓶数-剩余盖数 字符串到数量
        Map<String, Integer> map = new HashMap<>();
        map.put("money", 10);
        map.put("allBottle", 0);
        map.put("cover", 0);
        map.put("remainBottle", 0);
        map.put("remainCover", 0);

        System.out.println(map);
        buyBeer(map);
        System.out.println(map);
        map = changeBeer(map);
        System.out.println(map);
    }

    public static void buyBeer(Map<String, Integer> map) {
        int money = map.get("money");
        if (money >= 2) {
            int bottleNum = money / 2;
            map.put("remainBottle", bottleNum);
            map.put("remainCover", bottleNum);
            map.put("allBottle", bottleNum);
            map.put("money", money - bottleNum * 2);
        }
    }

    // 元   瓶   盖
    // 2    1    1
    // 4    2    2
    // 6    3    3
    // 8    4    4
    //换啤酒
    //                新瓶   新盖   新空瓶   剩余盖
    //      4

    public static Map<String, Integer> changeBeer(Map<String, Integer> map) {
        int allBottle = map.get("allBottle");
        int remainBottle = map.get("remainBottle");
        int remainCover = map.get("remainCover");


        int newBottlesFromBottles = remainBottle / 2;
        int newBottlesFromCovers = remainCover / 4;

        int newBottles = newBottlesFromBottles + newBottlesFromCovers;

        int newRemainBottle = remainBottle + newBottles  - newBottlesFromBottles * 2;
        int newRemainCover = remainCover + newBottles - newBottlesFromCovers * 4;
        allBottle = allBottle + newBottles;


        map.put("allBottle", allBottle);
        map.put("cover", newBottles);
        map.put("remainBottle", newRemainBottle);
        map.put("remainCover", newRemainCover);

        if (newBottles == 0) {
            return map;
        }

       return changeBeer(map);
    }
}
