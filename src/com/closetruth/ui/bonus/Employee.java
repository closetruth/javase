package com.closetruth.ui.bonus;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor //作用：创建无参构造方法:  public Employee(){}
@AllArgsConstructor //作用：创建带参构造方法:  public Employee(String name){}
@Data

public class Employee extends Thread {

    private String employeeName;
    // 创建钱包容量可变的列表
    private List<Double> wallet = new ArrayList<>();
    private BonusPackets bonusPackets;

    @Override
    public void run() {
        grabBonusPacket(bonusPackets.list);
    }

    public Employee(String employeeName, BonusPackets bonusPackets) {
        this.employeeName = employeeName;
        this.bonusPackets = bonusPackets;
    }

    //抢1次红包线程 给出线程名
    private void grabBonusPacket(List<Double> list) {
        while (true) {

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (list) {
                if (list.size() > 0) {
                    double bonusPacket = list.remove(0);
                    this.wallet.add(bonusPacket);
                    System.out.println(getEmployeeName() + " " + "抢到红包：" + bonusPacket + "元");
                    //休眠

                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(getEmployeeName() + " " + "没有抢到红包");
                    break;
                }
            }
        }
    }

    public String getWallet() {
        return wallet.toString();
    }
}
