
import java.util.Arrays;

public class test4 {

    // 定义液体类
    static class Liquid {
        String name;
        double weight;
        double totalValue;
        double unitValue; // 单位价值

        public Liquid(String name, double weight, double totalValue) {
            this.name = name;
            this.weight = weight;
            this.totalValue = totalValue;
            this.unitValue = totalValue / weight;
        }
    }

    public static void main(String[] args) {
        // 1. 初始化数据
        Liquid[] liquids = {
                new Liquid("水", 4, 24),
                new Liquid("牛奶", 8, 160),
                new Liquid("五粮液", 2, 4000),
                new Liquid("可乐", 6, 108),
                new Liquid("茅台", 1, 4000)
        };

        double capacity = 10.0; // 背包总容量
        double totalProfit = 0.0; // 总价值

        // 2. 核心逻辑：按单位价值（性价比）从高到低排序
        Arrays.sort(liquids, (a, b) -> Double.compare(b.unitValue, a.unitValue));

        System.out.println("--- 贪心算法取货明细 ---");

        // 3. 贪心选择
        for (Liquid liquid : liquids) {
            if (capacity <= 0) break;

            if (liquid.weight <= capacity) {
                // 如果当前液体能全装下
                totalProfit += liquid.totalValue;
                capacity -= liquid.weight;
                System.out.printf("取走全部 %s: %.1f 升, 价值: %.1f\n",
                        liquid.name, liquid.weight, liquid.totalValue);
            } else {
                // 如果只能装下一部分
                double fractionValue = capacity * liquid.unitValue;
                System.out.printf("取走部分 %s: %.1f 升, 价值: %.1f (单位价值: %.1f)\n",
                        liquid.name, capacity, fractionValue, liquid.unitValue);
                totalProfit += fractionValue;
                capacity = 0; // 背包满了
            }
        }

        System.out.println("-----------------------");
        System.out.printf("10升液体的最高价值为: %.2f\n", totalProfit);

        double maxVal = 0;

        // 这里的循环穷举每种液体拿取的“整数升”
        // i:水, j:牛奶, k:五粮液, l:可乐, m:茅台
        for (int i = 0; i <= 4; i++) { // 水最多4升
            for (int j = 0; j <= 8; j++) { // 牛奶最多8升
                for (int k = 0; k <= 2; k++) { // 五粮液最多2升
                    for (int l = 0; l <= 6; l++) { // 可乐最多6升
                        for (int m = 0; m <= 1; m++) { // 茅台最多1升

                            double totalWeight = i + j + k + l + m;

                            // 约束条件：总重量不能超过10升
                            if (totalWeight <= 10) {
                                // 计算当前组合的总价值
                                double currentVal = i*(24.0/4) + j*(160.0/8) +
                                        k*(4000.0/2) + l*(108.0/6) + m*(4000.0/1);

                                if (currentVal > maxVal) {
                                    maxVal = currentVal;
                                }

                                System.out.println(currentVal);
                            }
                        }
                    }
                }
            }
        }
        System.out.println("通过步长穷举得到的最高价值: " + maxVal);

    }
}