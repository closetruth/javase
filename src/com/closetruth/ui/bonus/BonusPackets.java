package com.closetruth.ui.bonus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;

@Data
@AllArgsConstructor

public class BonusPackets {
    public static List<Double> list = new ArrayList<>();

    //200 个红包其中小红包在[1 - 30] 元之间，总占比为80%，大红包[31-100]元，总占比为20%。红包最多两位小数
    {
        for (int i = 0; i < 200; i++) {
            if (i < 160) {
                list.add(Math.round((Math.random() * 29 + 1) * 10000) / 100.0);
                System.out.println("生成红包：" + list.get(i) + "元");
            } else {
                list.add(Math.round((Math.random() * 69 + 31) * 10000) / 100.0);
                System.out.println("生成红包：" + list.get(i) + "元");
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("打乱红包");
        Collections.shuffle(list);

    }
}
