package com.github.monster.controller;

import cn.hutool.core.date.DateUtil;
import com.github.monster.core.pool.DynamicTaskPool;
import com.github.monster.core.task.Task;
import com.github.monster.core.util.SpringContextUtil;
import com.github.monster.service.AttackCityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Monster
 * @date 2023/9/5 15:00
 */
@RestController
@RequestMapping()
public class CollectController {

    @Resource
    private DynamicTaskPool dynamicTaskPool;

    @GetMapping("/test")
    public void startAttackCityStatistics() {

        Date startTime = DateUtil.offsetMillisecond(new Date(), 10).toCalendar().getTime();

        Task task = new Task("test", startTime, 0, 0, () -> {
            AttackCityService sthService = SpringContextUtil.getBean(AttackCityService.class);
            sthService.attackCityStatistics();
        });

        dynamicTaskPool.addOnce(task);

    }

}
