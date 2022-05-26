package com.tank.entity;

import com.tank.util.ConfigUtil;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/5/25 1:07
 * FileName: TankFrame
 * Description: com.tank.entity
 */
public class TankFrame extends Frame {

    public static final int GAME_WIDTH = ConfigUtil.getInteger("gameWidth");
    public static final int GAME_HEIGHT = ConfigUtil.getInteger("gameHeight");

    Tank tank = new Tank(100, 100, false, Dir.DOWN, this, Group.GOOD);

    //子弹容器，主要是可以发出多个子弹
    public ArrayList<Bullet> goodBulletList = new ArrayList<>();
    public ArrayList<Bullet> badBulletList = new ArrayList<>();
    public ArrayList<Tank> tanksList = new ArrayList<>();
    public ArrayList<Explode> explodeList = new ArrayList<>();

    public TankFrame() {
        setVisible(true);
        //设置是否可以改变大小
        setResizable(false);
        setTitle("tank war");
        setSize(GAME_WIDTH, GAME_HEIGHT);
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


    public void information(Graphics g) {
        //添加title 子弹
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("剩余子弹数量:" + Tank.bulletsNums, 10, 60);
        g.drawString("剩余敌人数量:" + tanksList.size(), 200, 60);
        g.setColor(color);


        if (tanksList.size() <= 0 || !tank.lived) {
            g.setColor(Color.RED);
            g.drawString("game over", 1500, 1500);
        }
    }


    //重写paint方法
    //窗口需要重新绘制的时候要调用这个方法，每隔50ms调用一次
    @Override
    public void paint(Graphics g) {
        //记录一些子弹，坦克信息
        information(g);

        if (!tank.lived) {
            tank.x = 999999;
            tank.y = 999999;
        }
        //坦克自己去画自己
        tank.paint(g);

        //画敌方坦克
        for (int i = 0; i < tanksList.size(); i++) {
            tanksList.get(i).paint(g);
        }
        //画出子弹
        for (int i = 0; i < goodBulletList.size(); i++) {
            goodBulletList.get(i).paint(g);
        }
        for (int i = 0; i < badBulletList.size(); i++) {
            badBulletList.get(i).paint(g);
        }
        //画出爆炸
        for (int i = 0; i < explodeList.size(); i++) {
            explodeList.get(i).paint(g);
        }

        for (int i = 0; i < tanksList.size(); i++) {
            for (int j = 0; j < goodBulletList.size(); j++) {
                goodBulletList.get(j).collideWith(tanksList.get(i));
            }
        }

        for (int i = 0; i < badBulletList.size(); i++) {
            badBulletList.get(i).collideWith(tank);
        }
    }

    //下面这段代码是防止页面抖动
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics goffScreen = offScreenImage.getGraphics();
        Color color = goffScreen.getColor();
        goffScreen.setColor(Color.BLACK);
        goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        goffScreen.setColor(color);
        paint(goffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }


    private class MyKeyListener extends KeyAdapter {
        //这四个变量是用来记录键盘按下去的方向，可以方便按住两个方向键
        boolean bL = false;
        boolean bR = false;
        boolean bU = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }
            //设置主站坦克方向
            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                //键盘释放的时候，需要把方向设置回来，不然会一直往同一个方向执行
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_Z:
                    tank.fire();
                default:
                    break;
            }
            setMainTankDir();
        }

        //当某个按键被按下的时候，设置方向
        private void setMainTankDir() {
            if (!bL && !bD && !bU && !bR) {
                tank.setMoving(false);
            } else {
                //可以移动了
                tank.setMoving(true);
                if (bL) tank.setDir(Dir.LEFT);
                if (bR) tank.setDir(Dir.RIGHT);
                if (bU) tank.setDir(Dir.UP);
                if (bD) tank.setDir(Dir.DOWN);
            }
        }
    }
}
