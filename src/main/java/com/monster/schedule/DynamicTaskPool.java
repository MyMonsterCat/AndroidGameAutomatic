package com.monster.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
public class DynamicTaskPool {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 一次性定时任务
     *
     * @param task 任务
     */
    public void add(CustomizeTask task) {
        ScheduledFuture scheduledFuture = threadPoolTaskScheduler.schedule(task, task.getStartTime());
        ScheduleConfig.cache.put(task.getName(), scheduledFuture);
        log.info("任务{}放入队列，将在{}执行", task.getName(), task.getStartTime());
    }

    /**
     * 周期性执行任务，每次执行有固定间隔
     *
     * @param task 任务
     */
    public void addAtFixedRate(CustomizeTask task) {
        ScheduledFuture scheduledFuture = threadPoolTaskScheduler.scheduleAtFixedRate(task, task.getDuration());
        ScheduleConfig.cache.put(task.getName(), scheduledFuture);
    }

    /**
     * 在某个时间后开始周期性执行任务，每次执行有固定间隔
     *
     * @param task
     */
    public void addAtFixedRateDelay(CustomizeTask task) {
        ScheduledFuture scheduledFuture = threadPoolTaskScheduler.scheduleAtFixedRate(task, task.getStartTime(), task.getDuration());
        ScheduleConfig.cache.put(task.getName(), scheduledFuture);
    }

    /**
     * 周期性执行延时任务
     *
     * @param task 任务
     */
    public void addWithFixedDelay(CustomizeTask task) {
        ScheduledFuture scheduledFuture = threadPoolTaskScheduler.scheduleAtFixedRate(task, task.getDelay());
        ScheduleConfig.cache.put(task.getName(), scheduledFuture);
    }


    public void stop(String taskName) {
        if (ScheduleConfig.cache.isEmpty()) return;
        if (ScheduleConfig.cache.get(taskName) == null) return;

        ScheduledFuture scheduledFuture = ScheduleConfig.cache.get(taskName);

        if (scheduledFuture != null) {
            // 停止当前的线程
            scheduledFuture.cancel(true);
            ScheduleConfig.cache.remove(taskName);
        }
    }

    public void stopAll() {
        if (ScheduleConfig.cache.isEmpty()) return;
        ScheduleConfig.cache.values().forEach(scheduledFuture -> scheduledFuture.cancel(true));
    }


    /**
     * 是否有任务正在进行
     *
     * @return
     */
    public boolean isActive() {
        return threadPoolTaskScheduler.getActiveCount() > 0;
    }
}
