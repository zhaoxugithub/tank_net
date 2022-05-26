package com.tank;

import com.tank.entity.Dir;
import com.tank.entity.Group;
import com.tank.entity.Tank;
import com.tank.entity.TankFrame;
import com.tank.util.ConfigUtil;

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

        int tankNum = ConfigUtil.getInteger("tankNum");
        //添加敌方坦克
        for (int i = 0; i < tankNum; i++) {
            tankFrame.tanksList.add(new Tank(100 + i * 80, 400, true, Dir.DOWN, tankFrame, Group.BAD));
        }
        //自动刷新窗口
        while (true) {
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
