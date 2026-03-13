package com.closetruth.ui.io;

public class test {
    public static void main(String[] args) {
        System.out.println(eat(1));
    }

    public static int eat(int date){
        if (date == 10) {
            return 1;
        }
        int result = (eat(date + 1) + 1) * 2;
        return result;

        // eat(1) = (eat(2) + 1) * 2
        // eat(2) = (eat(3) + 1) * 2

        // 1: 10
        // 2: 4
        // 3: 1
    }
}
