package com.tank.entity;


import com.tank.util.Audio;
import com.tank.util.ConfigUtil;
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
    public int x;
    public int y;
    private Dir dir;
    private static final int SPEED = ConfigUtil.getInteger("tankSpeed");
    //坦克子弹剩余数量
    public static int bulletsNums = ConfigUtil.getInteger("bulletNum");
    //坦克是否是停止状态
    private boolean moving = false;
    private TankFrame tf = null;
    public boolean lived = true;
    //坦克阵营
    public Group group = Group.BAD;
    //获取坦克的宽度和高度
    public static int GOODWIDTH = ResourceManager.goodTankL.getWidth();
    public static int GOODHEIGHT = ResourceManager.goodTankL.getHeight();
    public static int BADWIDTH = ResourceManager.badTankL.getWidth();
    public static int BADHEIGHT = ResourceManager.badTankL.getHeight();
    private Random random = new Random();
    //添加这个主要是用检测子弹和坦克爆炸用的
    public Rectangle rectangle = new Rectangle();

    public Tank(int x, int y, boolean moving, Dir dir, TankFrame tf, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.tf = tf;
        this.group = group;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = BADWIDTH;
        rectangle.height = BADHEIGHT;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics g) {
        if (!this.lived) {
            tf.tanksList.remove(this);
        }


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

        //设置敌方坦克随机开火方向
        if (this.group == Group.BAD && random.nextInt(10) > 7) {
            this.fire();
        }

        //如果坦克是敌方的并且满足随机数条件，触发选择随机方向
        if (this.group == Group.BAD && random.nextInt(10) > 7) {
            randomDir();
        }

        //边界检测
        boundsCheck();
        //更新位置
        rectangle.x = this.x;
        rectangle.y = this.y;
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
        //算出子弹打出的中点位置
        int bx;
        int by;
        if (this.group == Group.GOOD) {
            if (bulletsNums <= 0) return;
            bx = this.x + Tank.GOODWIDTH / 2 - Bullet.GOODWIDTH / 2;
            by = this.y + Tank.GOODHEIGHT / 2 - Bullet.GOODHEIGHT / 2;
            tf.goodBulletList.add(new Bullet(bx, by, this.dir, tf, Group.GOOD));
            bulletsNums--;
        } else {
            bx = this.x + Tank.BADWIDTH / 2 - Bullet.BADWIDTH / 2;
            by = this.y + Tank.BADHEIGHT / 2 - Bullet.BADHEIGHT / 2;
            tf.badBulletList.add(new Bullet(bx, by, this.dir, tf, Group.BAD));
        }
        if (this.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }

    public void die() {
        this.lived = false;
    }
}
