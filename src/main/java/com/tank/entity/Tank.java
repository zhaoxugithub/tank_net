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

    public Tank(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Dir getDir() {
        return dir;
    }

    public void paint(Graphics g) {
        g.fillRect(x, y, 50, 50);
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
