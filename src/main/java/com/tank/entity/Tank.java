package com.tank.entity;

import java.awt.*;

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/5/24 23:53
 * FileName: Tank
 * Description: com.tank.entity
 */
public class Tank {

    private int x;
    private int y;
    private Dir dir;
    private static final int SPEED = 10;
    //坦克是否是停止状态
    private boolean moving = false;

    public Tank(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics g) {
        g.fillRect(x, y, 50, 50);
        move();
    }

    public void move() {
        //如果没有移动,就会停止
        if (!moving) {
            System.out.println("sdss");
            return;
        }
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }
    }

    //每次按下z键，就会创建一个子弹对象
    //有一个疑问如何将这个子弹类传给TankFrame,然后画出来呢？？？
    public void fire() {
        new Bullet(this.x, this.y, this.dir);
    }
}
