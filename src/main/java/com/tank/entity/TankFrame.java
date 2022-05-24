package com.tank.entity;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/5/25 1:07
 * FileName: TankFrame
 * Description: com.tank.entity
 */
public class TankFrame extends Frame {

    int x = 10;
    int y = 10;

    public TankFrame() {
        setVisible(true);
        //设置是否可以改变大小
        setResizable(false);
        setTitle("tank war");
        setSize(800, 600);

        //添加键盘的监听
        addKeyListener(new MyKeyListener());

        //添加一个窗口监听，点击X就会退出进程
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


    //重写paint方法
    //窗口需要重新绘制的时候要调用这个方法
    @Override
    public void paint(Graphics g) {
        System.out.println("paint");
        g.fillRect(x, y, 50, 50);
    }


    private class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("press");
            x += 10;
            //每调用一次repaint就会去调用paint方法，刷新窗口
        }

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("release");
        }
    }
}
