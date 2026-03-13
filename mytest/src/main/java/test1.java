import java.util.Arrays;

import static java.lang.Thread.sleep;

public class test1 {
    public static void main(String[] args) throws InterruptedException {

        //中奖号码数组，0-6为红球，7为蓝球，填入数据，不用循环
        int[] winningNumbers = {10, 12, 30, 16, 7, 17, 12};
        //为前6个数组排序
        Arrays.sort(winningNumbers, 0, 6);
        System.out.println("中奖号码为：" + Arrays.toString(winningNumbers));

        //猜测号码
        int[] guessNumbers = new int[7];

        for (int i = 0; i < 6; i++) {
            double rand = Math.random();
            int redBall = (int)(rand * 34 + 1);
            while (Arrays.binarySearch(guessNumbers, 0, i + 1, redBall) >= 0) {
                redBall = (int)(Math.random() * 34 + 1);
            }

            guessNumbers[i] = redBall;
            Arrays.sort(guessNumbers, 0, i + 1);
        }

        double rand = Math.random();
        int blueBall = (int)(rand * 14 + 1);
        guessNumbers[6] = blueBall;

        System.out.println("猜测号码为：" + Arrays.toString(guessNumbers));


    }
}
