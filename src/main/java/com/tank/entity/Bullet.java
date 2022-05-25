package com.tank.entity;

import java.awt.*;

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/5/24 23:53
 * FileName: Bullet
 * Description: com.tank.entity 子弹类
 */

public class Bullet {

    private static final int SPEED = 10;
    private int x, y;
    private Dir dir;
    private static final int WIDTH = 5, HEIGHT = 5;
    private boolean lived = true;

    TankFrame tf = null;

    public Bullet(int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }


    //给TankFrame去调用的，实际上也是每50ms调用一次
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.red);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
        move();
    }

    //子弹是不会停止的
    private void move() {

        if (this.x < 0 || this.y < 0 || this.x > TankFrame.GAME_WIDTH || this.y > TankFrame.GAME_HEIGHT) {
            lived = false;
            tf.bulletList.remove(this);
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
}
