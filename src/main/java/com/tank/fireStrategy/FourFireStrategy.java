package com.tank.fireStrategy;

import com.tank.entity.Bullet;
import com.tank.entity.Dir;
import com.tank.entity.Group;
import com.tank.entity.Tank;
import com.tank.util.Audio;

import static com.tank.entity.Tank.bulletsNums;

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/5/27 1:47
 * FileName: FourFireStrategy
 * Description: com.tank.fireStrategy
 */
public class FourFireStrategy implements FireStrategy {

    @Override
    public void fire(Tank tank) {
        //算出子弹打出的中点位置
        int bx;
        int by;
        if (tank.group == Group.GOOD) {
            if (bulletsNums <= 0) return;
            bx = tank.x + Tank.GOODWIDTH / 2 - Bullet.GOODWIDTH / 2;
            by = tank.y + Tank.GOODHEIGHT / 2 - Bullet.GOODHEIGHT / 2;
            for (Dir dir : Dir.values()) {
                tank.tf.goodBulletList.add(new Bullet(bx, by, dir, tank.tf, Group.GOOD));
            }
            bulletsNums--;
        } else {
            bx = tank.x + Tank.BADWIDTH / 2 - Bullet.BADWIDTH / 2;
            by = tank.y + Tank.BADHEIGHT / 2 - Bullet.BADHEIGHT / 2;
            tank.tf.badBulletList.add(new Bullet(bx, by, tank.dir, tank.tf, Group.BAD));
        }
        if (tank.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
