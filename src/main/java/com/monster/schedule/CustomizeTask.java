package com.monster.schedule;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Getter
@Setter
public abstract class CustomizeTask implements Runnable {
    /**
     * 任务名字
     */
    protected String name;
    /**
     * 触发条件
     */
    protected Date startTime;
    /**
     * 两次任务之间的执行间隔时间
     */
    protected long duration;
    /**
     * 延迟时间
     */
    protected long delay;

    public abstract void taskRun();

    @Override
    public void run() {
        log.info("当前任务名称：{}", name);
        taskRun();
    }
}
