
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class test2 {
    public static void main(String[] args) {
        int[] arr = new int[100];


        for (int i = 0; i < 100; i++) {
            int rand = (int) (Math.random() * 200 + 1);
            while (contains(arr, rand)) {
                rand = (int) (Math.random() * 200 + 1);
            }

            arr[i] = rand;
        }

        System.out.println(Arrays.toString(arr));
        try {
            eliminate(arr);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean contains(int[] arr, int rand) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == rand) {
                return true;
            }
        }
        return false;
    }

    private static void eliminate(int[] arr) throws InterruptedException {
        while (remainingCount(arr) != 1) {
            int count = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != 0) {
                    count++;
                    if (count % 2  == 1) {
                        arr[i] = 0;
                    }
                }
            }
            sleep(1000);
            System.out.println(Arrays.toString(arr));
        }
    }

    private static int remainingCount(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                count++;
            }
        }
        return count;
    }
}
