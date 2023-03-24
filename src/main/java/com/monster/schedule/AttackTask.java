package com.monster.schedule;

import com.monster.service.ISthService;
import com.monster.service.SthServiceImpl;
import com.monster.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Monster
 * @date 2023/3/16 15:55
 */
@Slf4j
public class AttackTask extends CustomizeTask {

    public AttackTask(int x, int y, Date date) {
        this.x = x;
        this.y = y;
        this.startTime = date;
        this.name = "attack";
    }

    public void setName(String name) {
        this.name = "AttackCity-Task";
    }

    private int x;
    private int y;


    @Override
    public void taskRun() {
        SthServiceImpl sthService = SpringContextUtil.getBean(ISthService.class);
        sthService.attackCity(x, y);
        log.info("任务执行了" + this.getName());
    }
}
