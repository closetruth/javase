package com.closetruth.ui.bonus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;

public class ThreadTest {
    public static void main(String[] args) {
        BonusPackets bonusPackets = new BonusPackets();
        List<Employee> employees = new ArrayList<>();
        //创建100个不同的员工对象 加上名字
        for (int i = 0; i < 100; i++) {
            //休眠100ms
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            employees.add(new Employee("员工" + (i + 1), bonusPackets));
            System.out.println("创建" + employees.get(i).getEmployeeName());
        }


        Collections.shuffle(employees);
        for (int i = 0; i < 100; i++) {
            employees.get(i).start();
        }

        //上面线程执行完再打印红包内容
        for (int i = 0; i < 100; i++) {
            try {
                employees.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 100; i++) {
            System.out.println(employees.get(i).getEmployeeName() + "抢到的红包：" + employees.get(i).getWallet());
        }


    }
}
