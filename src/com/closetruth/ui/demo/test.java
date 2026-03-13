package com.closetruth.ui.demo;

public class test {
    public static void main(String[] args) {
        // TODO: 实现面向对象的设备控制逻辑
        /*
         * // 目标：面向对象程序设计智能生成控制系统。
         * // 职责：设备（电视、电冰箱、洗衣机、微波炉……）
        */

        JD[] jds = new JD[4];
        jds[0] = new TV("小米电视", true);
        jds[1] = new WashMachine("美的洗衣机", true);
        jds[2] = new Air("美的空调", true);
        jds[3] = new Lamp("LED灯", true);

        SmartHomeControl smartHomeControl = new SmartHomeControl();
        //smartHomeControl.control(jds[0]);
    }
}
