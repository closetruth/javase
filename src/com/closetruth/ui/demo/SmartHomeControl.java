package com.closetruth.ui.demo;

public class SmartHomeControl {
    public void control(JD jd) {
        System.out.println("开始操作");
        System.out.println("目前状态是：" + jd.getName() + " " + jd.getStatus());
        jd.press();
        System.out.println("状态已经是：" + jd.getName() + " " + jd.getStatus());
    }
}
