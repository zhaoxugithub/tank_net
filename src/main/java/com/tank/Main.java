package com.tank;

import com.tank.entity.TankFrame;

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/5/24 23:32
 * FileName: Main
 * Description: com.tank
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();


        //自动刷新窗口
        while (true) {
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
