package com.tank.fireStrategy;

import com.tank.entity.Tank;

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/5/27 1:37
 * FileName: FireStrategy
 * Description: com.tank.fireStrategy
 * <p>
 * 策略模式
 */
@FunctionalInterface
public interface FireStrategy {
    void fire(Tank tank);
}
