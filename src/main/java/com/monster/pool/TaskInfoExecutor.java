package com.monster.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池
 *
 * @author Monster
 */
@Slf4j
public class TaskInfoExecutor extends ThreadPoolTaskExecutor {

    public String info() {
        ThreadPoolExecutor executor = getThreadPoolExecutor();

        String info = "线程池" + this.getThreadNamePrefix() +
                "中，总任务数为 " + executor.getTaskCount() +
                " ，已处理完的任务数为 " + executor.getCompletedTaskCount() +
                " ，目前正在处理的任务数为 " + executor.getActiveCount() +
                " ，缓冲队列中任务数为 " + executor.getQueue().size();

        logger.info(info);

        return info;
    }

    @Override
    public void execute(Runnable task) {
        super.execute(task);
        info();
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        super.execute(task, startTimeout);
        info();
    }

    @Override
    public Future<?> submit(Runnable task) {
        Future<?> submit = super.submit(task);
        info();
        return submit;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        Future<T> submit = super.submit(task);
        info();
        return submit;
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        ListenableFuture<?> listenable = super.submitListenable(task);
        info();
        return listenable;
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        ListenableFuture<T> listenable = super.submitListenable(task);
        info();
        return listenable;
    }
}
