package com.github.monster.mga.core.pool;

import com.github.monster.mga.core.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
public class DynamicTaskPool {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 一次性定时任务
     *
     * @param task 任务
     */
    public void addOnce(Task task) {
        ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(task, task.getStartTime());
        TaskConfig.cache.put(task.getName(), scheduledFuture);
        log.info("任务 {} 放入队列，将在 {} 执行", task.getName(), formatter.format(task.getStartTime()));
    }

    /**
     * 周期性执行任务，每次执行有固定间隔
     *
     * @param task 任务
     */
    public void addAtFixedRate(Task task) {
        ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.scheduleAtFixedRate(task, task.getDuration());
        TaskConfig.cache.put(task.getName(), scheduledFuture);
    }

    /**
     * 在某个时间后开始周期性执行任务，每次执行有固定间隔
     */
    public void addAtFixedRateDelay(Task task) {
        ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.scheduleAtFixedRate(task, task.getStartTime(), task.getDuration());
        TaskConfig.cache.put(task.getName(), scheduledFuture);
    }

    /**
     * 周期性执行延时任务
     *
     * @param task 任务
     */
    public void addWithFixedDelay(Task task) {
        ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.scheduleAtFixedRate(task, task.getDelay());
        TaskConfig.cache.put(task.getName(), scheduledFuture);
    }


    public void stop(String taskName) {
        if (TaskConfig.cache.isEmpty()) return;
        if (TaskConfig.cache.get(taskName) == null) return;

        ScheduledFuture<?> scheduledFuture = TaskConfig.cache.get(taskName);

        if (scheduledFuture != null) {
            // 停止当前的线程
            scheduledFuture.cancel(true);
            TaskConfig.cache.remove(taskName);
        }
    }

    public void stopAll() {
        if (TaskConfig.cache.isEmpty()) {
            return;
        }
        TaskConfig.cache.values().forEach(scheduledFuture -> scheduledFuture.cancel(true));
    }


    /**
     * 正在进行的任务数
     */
    public int isActive() {
        return threadPoolTaskScheduler.getActiveCount();
    }
}
