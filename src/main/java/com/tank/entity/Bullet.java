package com.tank.entity;

import com.tank.util.Audio;
import com.tank.util.ResourceManager;

import java.awt.*;

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/5/24 23:53
 * FileName: Bullet
 * Description: com.tank.entity 子弹类
 */

public class Bullet {

    private static final int SPEED = 30;
    public int x, y;
    private Dir dir;
    private Group group;
    public static int GOODWIDTH = ResourceManager.goodBulletD.getWidth();
    public static int GOODHEIGHT = ResourceManager.goodBulletD.getHeight();

    public static int BADWIDTH = ResourceManager.badBulletD.getWidth();
    public static int BADHEIGHT = ResourceManager.badBulletD.getHeight();
    private boolean lived = true;

    public Rectangle rectangle = new Rectangle();

    TankFrame tf = null;

    public Bullet(int x, int y, Dir dir, TankFrame tf, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = BADWIDTH;
        rectangle.height = BADHEIGHT;
    }


    //给TankFrame去调用的，实际上也是每50ms调用一次
    public void paint(Graphics g) {
        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodBulletL : ResourceManager.badBulletL, this.x, this.y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodBulletR : ResourceManager.badBulletR, this.x, this.y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodBulletU : ResourceManager.badBulletU, this.x, this.y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodBulletD : ResourceManager.badBulletD, this.x, this.y, null);
                break;
            default:
                break;
        }
        move();
    }

    //子弹是不会停止的
    private void move() {

        if (this.x < 0 || this.y < 0 || this.x > TankFrame.GAME_WIDTH || this.y > TankFrame.GAME_HEIGHT) {
            lived = false;
            if(this.group == Group.GOOD){
                tf.goodBulletList.remove(this);
            }else {
                tf.badBulletList.remove(this);
            }
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
        rectangle.x = this.x;
        rectangle.y = this.y;
    }


    public void collideWith(Tank tank) {
        //如果坦克的阵营和子弹的阵营一致
        if (this.group == tank.group) {
            return;
        }

        //如果子弹和坦克相遇
        if (rectangle.intersects(tank.rectangle)) {
            System.out.println("gggggg");
            new Thread(() -> new Audio("audio/explode.wav").play()).start();
            tank.die();
            this.die();
            int ex = tank.x + Tank.BADWIDTH / 2 - Explode.WIDTH / 2;
            int ey = tank.y + Tank.BADHEIGHT / 2 - Explode.HEIGHT / 2;
//            BaseExplode explode = tf.gameFactory.createExplode(ex, ey, tf);
            tf.explodeList.add(new Explode(ex, ey, tf));
        }
    }

    private void die() {
        this.lived = false;
    }
}
