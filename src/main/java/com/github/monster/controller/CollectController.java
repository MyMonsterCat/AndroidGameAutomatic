package com.github.monster.controller;

import cn.hutool.core.date.DateUtil;
import com.github.monster.core.pool.DynamicTaskPool;
import com.github.monster.core.task.Task;
import com.github.monster.core.util.SpringContextUtil;
import com.github.monster.service.AttackCityService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Monster
 * @date 2023/9/5 15:00
 */
public class CollectController {

    @Resource
    private DynamicTaskPool dynamicTaskPool;


    public void startAttackCityStatistics() {

        Date startTime = DateUtil.offsetMillisecond(new Date(), 10).toCalendar().getTime();

        Task task = new Task("test", startTime, 0, 0, () -> {
            AttackCityService sthService = SpringContextUtil.getBean(AttackCityService.class);
            sthService.aa(1, 2);
        });

        dynamicTaskPool.addOnce(task);

    }

}
