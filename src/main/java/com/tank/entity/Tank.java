package com.tank.entity;


import com.tank.util.ResourceManager;

import java.awt.*;
import java.util.Random;

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
    private TankFrame tf = null;

    //坦克阵营
    private Group group = Group.BAD;

    //获取坦克的宽度和高度
    public static int GOODWIDTH = ResourceManager.goodTankL.getWidth();
    public static int GOODHEIGHT = ResourceManager.goodTankL.getHeight();

    public static int BADWIDTH = ResourceManager.badTankL.getWidth();
    public static int BADHEIGHT = ResourceManager.badTankL.getHeight();


    private Random random = new Random();

    public Tank(int x, int y, boolean moving, Dir dir, TankFrame tf, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.tf = tf;
        this.group = group;

    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics g) {
        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankL : ResourceManager.badTankL, this.x, this.y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankR : ResourceManager.badTankR, this.x, this.y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankU : ResourceManager.badTankU, this.x, this.y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankD : ResourceManager.badTankD, this.x, this.y, null);
                break;
            default:
                break;
        }

        move();
    }

    public void move() {
        //如果没有移动,就会停止
        if (!moving) {
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
        //如果坦克是敌方的并且满足随机数条件，触发选择随机方向
        if (this.group == Group.BAD && random.nextInt(10) > 7) {
            randomDir();
        }

        //边界检测
        boundsCheck();
    }

    private void boundsCheck() {
        if (this.x < 0) this.x = 0;
        if (this.x > TankFrame.GAME_WIDTH - Tank.GOODWIDTH) this.x = TankFrame.GAME_WIDTH - Tank.GOODWIDTH;
        if (this.y < 30) this.y = 30;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.GOODHEIGHT) this.y = TankFrame.GAME_HEIGHT - Tank.GOODHEIGHT;
    }

    //从四个方向重选择一个方向
    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    //每次按下z键，就会创建一个子弹对象
    //有一个疑问如何将这个子弹类传给TankFrame,然后画出来呢？？？
    //方法是把TankFrame作为引用传到Tank类中
    public void fire() {
        tf.bulletList.add(new Bullet(this.x, this.y, this.dir, tf, Group.GOOD));
    }
}
